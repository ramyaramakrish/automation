-- =============================================================================
-- Transfer Service - H2 Database Schema
-- =============================================================================
-- This schema is for H2 in-memory database (development/testing).
-- Note: JPA/Hibernate auto-creates tables, so this is for reference only.
-- =============================================================================

-- -----------------------------------------------------------------------------
-- Accounts Table
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS accounts (
    account_id          VARCHAR(12) PRIMARY KEY,
    account_holder_name VARCHAR(100) NOT NULL,
    email               VARCHAR(100) UNIQUE,
    phone               VARCHAR(15) UNIQUE,
    balance             DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    account_type        VARCHAR(20) NOT NULL DEFAULT 'SAVINGS',
    status              VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version             BIGINT DEFAULT 0
);

-- -----------------------------------------------------------------------------
-- Transactions Table
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
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
    
    CONSTRAINT fk_from_account FOREIGN KEY (from_account_id) REFERENCES accounts(account_id),
    CONSTRAINT fk_to_account FOREIGN KEY (to_account_id) REFERENCES accounts(account_id)
);

-- -----------------------------------------------------------------------------
-- Indexes
-- -----------------------------------------------------------------------------
CREATE INDEX IF NOT EXISTS idx_accounts_status ON accounts(status);
CREATE INDEX IF NOT EXISTS idx_transaction_from_account ON transactions(from_account_id);
CREATE INDEX IF NOT EXISTS idx_transaction_to_account ON transactions(to_account_id);
CREATE INDEX IF NOT EXISTS idx_transaction_reference ON transactions(reference_number);
CREATE INDEX IF NOT EXISTS idx_transaction_date ON transactions(transaction_date);

-- =============================================================================
-- Sample Data (Loaded by DataLoader.java component)
-- =============================================================================
-- Note: Sample data is loaded programmatically via DataLoader.java
-- This ensures proper entity relationships and timestamps.
-- 
-- Sample Accounts:
-- A001 - John Doe      - 15,000.00 - SAVINGS - ACTIVE
-- A002 - Jane Smith    - 25,000.50 - CURRENT - ACTIVE
-- A003 - Alice Johnson - 30,000.75 - SAVINGS - ACTIVE
-- A004 - Bob Wilson    -  5,000.00 - SALARY  - ACTIVE
-- A005 - Carol Davis   - 75,000.00 - CURRENT - ACTIVE
-- A006 - Inactive User -  1,000.00 - SAVINGS - INACTIVE
-- A007 - Suspended User-    500.00 - SAVINGS - SUSPENDED
-- =============================================================================