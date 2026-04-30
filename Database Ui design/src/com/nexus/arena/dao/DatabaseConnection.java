package com.nexus.arena.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;

/**
 * Singleton database connection manager for NexusArena.
 * Provides a persistent connection with automatic reconnection.
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/NexusArena";
    private static final String USER = "root";
    private static final String PASSWORD = "xffxn_909";

    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || !connection.isValid(2)) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found.", e);
            }
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("[NexusArena] Database connection established.");
            seedDatabaseIfEmpty();
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("[NexusArena] Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }

    private static void seedDatabaseIfEmpty() {
        try {
            boolean isEmpty = true;
            try (java.sql.Statement st = connection.createStatement();
                 java.sql.ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM PLAYER")) {
                 if (rs.next() && rs.getInt(1) > 0) {
                     isEmpty = false;
                 }
            } catch (Exception ignored) {}
            
            if (!isEmpty) return; // Database already populated

            System.out.println("[NexusArena] Database is empty. Loading DML data from seed script...");
            File seedFile = new File("resources/seed_data.sql");
            if (!seedFile.exists()) {
                seedFile = new File("c:/Users/HF/Documents/DATABASE PROJECT/Database Ui design/resources/seed_data.sql");
            }
            if (seedFile.exists()) {
                String sqlContent = new String(Files.readAllBytes(Paths.get(seedFile.getAbsolutePath())));
                String[] statements = sqlContent.split(";");
                try (java.sql.Statement st = connection.createStatement()) {
                    for (String stmt : statements) {
                        stmt = stmt.trim();
                        if (!stmt.isEmpty()) {
                            st.execute(stmt);
                        }
                    }
                }
                System.out.println("[NexusArena] Seed script applied successfully. Database is now loaded.");
            } else {
                System.out.println("[NexusArena] Seed file not found.");
            }
        } catch (Exception e) {
            System.err.println("[NexusArena] Error running seed script: " + e.getMessage());
        }
    }

    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && conn.isValid(2);
        } catch (SQLException e) {
            System.out.println("[NexusArena] Connection test failed: " + e.getMessage());
            return false;
        }
    }
}
