package com.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtility {
    private static final String URL = "jdbc:mysql://localhost:3306/mini_ecommerce";
    private static final String USER = "root";
 // Before: private static final String PASSWORD = "your_real_password";
 // After: This safely fetches the password from your OS environment variables instead of hardcoding it!
 private static final String PASSWORD = System.getenv("DB_PASSWORD");


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
