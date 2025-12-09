-- =============================================================================
-- Transfer Service - PostgreSQL Database Schema
-- =============================================================================
-- This script creates the database schema for the Transfer Service application.
-- Run this script before starting the application with postgresql profile.
--
-- Usage:
--   psql -U postgres -d transferdb -f schema-postgresql.sql
--
-- Or in psql:
--   \i schema-postgresql.sql
-- =============================================================================

-- -----------------------------------------------------------------------------
-- Database Creation (Run as superuser if needed)
-- -----------------------------------------------------------------------------
-- CREATE DATABASE transferdb;
-- \c transferdb;

-- -----------------------------------------------------------------------------
-- Drop existing tables (for fresh setup)
-- -----------------------------------------------------------------------------
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;

-- -----------------------------------------------------------------------------
-- Create ENUM types (PostgreSQL specific)
-- -----------------------------------------------------------------------------
DROP TYPE IF EXISTS account_type CASCADE;
CREATE TYPE account_type AS ENUM (
    'SAVINGS',
    'CURRENT',
    'SALARY',
    'FIXED_DEPOSIT',
    'RECURRING_DEPOSIT'
);

DROP TYPE IF EXISTS account_status CASCADE;
CREATE TYPE account_status AS ENUM (
    'ACTIVE',
    'INACTIVE',
    'SUSPENDED',
    'CLOSED',
    'BLOCKED'
);

DROP TYPE IF EXISTS transaction_type CASCADE;
CREATE TYPE transaction_type AS ENUM (
    'FUND_TRANSFER',
    'DEPOSIT',
    'WITHDRAWAL',
    'BILL_PAYMENT',
    'REFUND'
);

DROP TYPE IF EXISTS transaction_status CASCADE;
CREATE TYPE transaction_status AS ENUM (
    'PENDING',
    'COMPLETED',
    'FAILED',
    'REVERSED',
    'CANCELLED'
);

-- -----------------------------------------------------------------------------
-- Accounts Table
-- -----------------------------------------------------------------------------
CREATE TABLE accounts (
    account_id          VARCHAR(12) PRIMARY KEY,
    account_holder_name VARCHAR(100) NOT NULL,
    email               VARCHAR(100) UNIQUE,
    phone               VARCHAR(15) UNIQUE,
    balance             DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    account_type        VARCHAR(20) NOT NULL DEFAULT 'SAVINGS',
    status              VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version             BIGINT DEFAULT 0,
    
    -- Constraints
    CONSTRAINT chk_balance_non_negative CHECK (balance >= 0),
    CONSTRAINT chk_account_type CHECK (account_type IN ('SAVINGS', 'CURRENT', 'SALARY', 'FIXED_DEPOSIT', 'RECURRING_DEPOSIT')),
    CONSTRAINT chk_account_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED', 'CLOSED', 'BLOCKED'))
);

-- Indexes for accounts table
CREATE INDEX idx_accounts_status ON accounts(status);
CREATE INDEX idx_accounts_type ON accounts(account_type);
CREATE INDEX idx_accounts_email ON accounts(email);
CREATE INDEX idx_accounts_phone ON accounts(phone);
CREATE INDEX idx_accounts_holder_name ON accounts(account_holder_name);

-- -----------------------------------------------------------------------------
-- Transactions Table
-- -----------------------------------------------------------------------------
CREATE TABLE transactions (
    transaction_id      BIGSERIAL PRIMARY KEY,
    reference_number    VARCHAR(20) NOT NULL UNIQUE,
    from_account_id     VARCHAR(12) NOT NULL,
    to_account_id       VARCHAR(12) NOT NULL,
    amount              DECIMAL(15, 2) NOT NULL,
    transaction_type    VARCHAR(20) NOT NULL DEFAULT 'FUND_TRANSFER',
    status              VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    description         VARCHAR(255),
    transaction_date    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    from_balance_before DECIMAL(15, 2),
    from_balance_after  DECIMAL(15, 2),
    to_balance_before   DECIMAL(15, 2),
    to_balance_after    DECIMAL(15, 2),
    failure_reason      VARCHAR(255),
    
    -- Foreign Keys
    CONSTRAINT fk_from_account FOREIGN KEY (from_account_id) REFERENCES accounts(account_id),
    CONSTRAINT fk_to_account FOREIGN KEY (to_account_id) REFERENCES accounts(account_id),
    
    -- Constraints
    CONSTRAINT chk_amount_positive CHECK (amount > 0),
    CONSTRAINT chk_different_accounts CHECK (from_account_id <> to_account_id),
    CONSTRAINT chk_transaction_type CHECK (transaction_type IN ('FUND_TRANSFER', 'DEPOSIT', 'WITHDRAWAL', 'BILL_PAYMENT', 'REFUND')),
    CONSTRAINT chk_transaction_status CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED', 'REVERSED', 'CANCELLED'))
);

-- Indexes for transactions table
CREATE INDEX idx_transaction_from_account ON transactions(from_account_id);
CREATE INDEX idx_transaction_to_account ON transactions(to_account_id);
CREATE INDEX idx_transaction_reference ON transactions(reference_number);
CREATE INDEX idx_transaction_date ON transactions(transaction_date);
CREATE INDEX idx_transaction_status ON transactions(status);

-- Composite index for account transaction history queries
CREATE INDEX idx_transaction_accounts ON transactions(from_account_id, to_account_id, transaction_date DESC);

-- -----------------------------------------------------------------------------
-- Function: Update timestamp on row update
-- -----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger for accounts table
DROP TRIGGER IF EXISTS trigger_accounts_updated_at ON accounts;
CREATE TRIGGER trigger_accounts_updated_at
    BEFORE UPDATE ON accounts
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- -----------------------------------------------------------------------------
-- Function: Generate transaction reference number
-- -----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION generate_reference_number()
RETURNS VARCHAR(20) AS $$
BEGIN
    RETURN 'TXN' || EXTRACT(EPOCH FROM CURRENT_TIMESTAMP)::BIGINT || (RANDOM() * 1000)::INT;
END;
$$ LANGUAGE plpgsql;

-- =============================================================================
-- Sample Data
-- =============================================================================

-- -----------------------------------------------------------------------------
-- Insert Sample Accounts
-- -----------------------------------------------------------------------------
INSERT INTO accounts (account_id, account_holder_name, email, phone, balance, account_type, status) VALUES
('A001', 'John Doe', 'john.doe@example.com', '9876543210', 15000.00, 'SAVINGS', 'ACTIVE'),
('A002', 'Jane Smith', 'jane.smith@example.com', '9876543211', 25000.50, 'CURRENT', 'ACTIVE'),
('A003', 'Alice Johnson', 'alice.j@example.com', '9876543212', 30000.75, 'SAVINGS', 'ACTIVE'),
('A004', 'Bob Wilson', 'bob.wilson@example.com', '9876543213', 5000.00, 'SALARY', 'ACTIVE'),
('A005', 'Carol Davis', 'carol.davis@example.com', '9876543214', 75000.00, 'CURRENT', 'ACTIVE'),
('A006', 'Inactive User', 'inactive@example.com', '9876543215', 1000.00, 'SAVINGS', 'INACTIVE'),
('A007', 'Suspended User', 'suspended@example.com', '9876543216', 500.00, 'SAVINGS', 'SUSPENDED');

-- -----------------------------------------------------------------------------
-- Insert Sample Transactions
-- -----------------------------------------------------------------------------
INSERT INTO transactions (reference_number, from_account_id, to_account_id, amount, transaction_type, status, description, from_balance_before, from_balance_after, to_balance_before, to_balance_after) VALUES
('TXN1001', 'A001', 'A002', 500.00, 'FUND_TRANSFER', 'COMPLETED', 'Monthly rent payment', 15500.00, 15000.00, 24500.50, 25000.50),
('TXN1002', 'A002', 'A003', 1000.00, 'FUND_TRANSFER', 'COMPLETED', 'Invoice payment', 26000.50, 25000.50, 29000.75, 30000.75),
('TXN1003', 'A003', 'A001', 250.50, 'FUND_TRANSFER', 'COMPLETED', 'Reimbursement', 30251.25, 30000.75, 14749.50, 15000.00),
('TXN1004', 'A004', 'A005', 100.00, 'FUND_TRANSFER', 'COMPLETED', 'Subscription fee', 5100.00, 5000.00, 74900.00, 75000.00),
('TXN1005', 'A005', 'A004', 2000.00, 'FUND_TRANSFER', 'COMPLETED', 'Salary advance', 77000.00, 75000.00, 3000.00, 5000.00),
('TXN1006', 'A001', 'A003', 750.00, 'FUND_TRANSFER', 'COMPLETED', 'Gift', 15750.00, 15000.00, 29250.75, 30000.75),
('TXN1007', 'A004', 'A002', 10000.00, 'FUND_TRANSFER', 'FAILED', 'Large transfer attempt', 5000.00, 5000.00, 25000.50, 25000.50);

-- Update the failed transaction with failure reason
UPDATE transactions SET failure_reason = 'Insufficient funds' WHERE reference_number = 'TXN1007';

-- =============================================================================
-- Useful Views
-- =============================================================================

-- -----------------------------------------------------------------------------
-- View: Account Summary
-- -----------------------------------------------------------------------------
CREATE OR REPLACE VIEW v_account_summary AS
SELECT 
    a.account_id,
    a.account_holder_name,
    a.balance,
    a.account_type,
    a.status,
    COUNT(DISTINCT t1.transaction_id) AS total_sent,
    COUNT(DISTINCT t2.transaction_id) AS total_received,
    COALESCE(SUM(t1.amount), 0) AS total_amount_sent,
    COALESCE(SUM(t2.amount), 0) AS total_amount_received
FROM accounts a
LEFT JOIN transactions t1 ON a.account_id = t1.from_account_id AND t1.status = 'COMPLETED'
LEFT JOIN transactions t2 ON a.account_id = t2.to_account_id AND t2.status = 'COMPLETED'
GROUP BY a.account_id, a.account_holder_name, a.balance, a.account_type, a.status;

-- -----------------------------------------------------------------------------
-- View: Recent Transactions
-- -----------------------------------------------------------------------------
CREATE OR REPLACE VIEW v_recent_transactions AS
SELECT 
    t.transaction_id,
    t.reference_number,
    t.from_account_id,
    fa.account_holder_name AS from_account_name,
    t.to_account_id,
    ta.account_holder_name AS to_account_name,
    t.amount,
    t.transaction_type,
    t.status,
    t.description,
    t.transaction_date
FROM transactions t
JOIN accounts fa ON t.from_account_id = fa.account_id
JOIN accounts ta ON t.to_account_id = ta.account_id
ORDER BY t.transaction_date DESC
LIMIT 100;

-- -----------------------------------------------------------------------------
-- View: Transaction Statistics
-- -----------------------------------------------------------------------------
CREATE OR REPLACE VIEW v_transaction_stats AS
SELECT 
    DATE(transaction_date) AS transaction_date,
    COUNT(*) AS total_transactions,
    COUNT(*) FILTER (WHERE status = 'COMPLETED') AS completed,
    COUNT(*) FILTER (WHERE status = 'FAILED') AS failed,
    SUM(amount) FILTER (WHERE status = 'COMPLETED') AS total_amount
FROM transactions
GROUP BY DATE(transaction_date)
ORDER BY transaction_date DESC;

-- =============================================================================
-- Useful Queries for Testing
-- =============================================================================

-- Get all active accounts
-- SELECT * FROM accounts WHERE status = 'ACTIVE';

-- Get transaction history for an account
-- SELECT * FROM transactions 
-- WHERE from_account_id = 'A001' OR to_account_id = 'A001'
-- ORDER BY transaction_date DESC;

-- Get account summary
-- SELECT * FROM v_account_summary;

-- Get recent transactions
-- SELECT * FROM v_recent_transactions;

-- Get transaction statistics
-- SELECT * FROM v_transaction_stats;

-- =============================================================================
-- Grant Permissions (if using separate application user)
-- =============================================================================
-- GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO app_user;
-- GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO app_user;

-- =============================================================================
-- Verification Queries
-- =============================================================================
SELECT 'Accounts created: ' || COUNT(*) FROM accounts;
SELECT 'Transactions created: ' || COUNT(*) FROM transactions;

\echo '============================================='
\echo 'PostgreSQL Schema created successfully!'
\echo '============================================='
\echo 'Tables: accounts, transactions'
\echo 'Views: v_account_summary, v_recent_transactions, v_transaction_stats'
\echo '============================================='
