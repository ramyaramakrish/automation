package com.npci.db;

import org.postgresql.Driver;

import java.sql.*;

public class Example1 {
    public static void main(String[] args) {

        // Read all accounts from the database
        // Using JDBC api
        Connection connection = null;
        try {
            // step-1 : register the driver
            DriverManager.registerDriver(new Driver());
            // `step-2 : create the connection
            String url = "jdbc:postgresql://localhost:55000/postgres";
            String user = "postgres";
            String password = "postgrespw";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established successfully: " + connection);

            // step-3 : sql queries execution
            String sql = "SELECT * FROM accounts";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("SQL query executed successfully.");

            // step-4 : process the results
            while (rs.next()) {
                String accountNumber = rs.getString("acc_number");
                double balance = rs.getDouble("balance");
                System.out.println("Account Number: " + accountNumber);
                System.out.println("Balance: " + balance);
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    System.out.println("Connection closed successfully.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
