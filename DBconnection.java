package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

    // Oracle DB URL, username, and password
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE"; // replace XE with your SID
    private static final String USER = "system"; // replace with your Oracle DB username
    private static final String PASSWORD = "password"; // replace with your Oracle DB password

    private static Connection connection = null;

    // Static method to get database connection
    public static Connection getConnection() {
        if (connection != null)
            return connection;

        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Oracle Database successfully.");

        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to Oracle Database.");
            e.printStackTrace();
        }

        return connection;
    }

    // Optional: Close the connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Oracle Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
