package com.example.coursework.database;

import java.sql.*;
import java.util.logging.*;

public class DatabaseConnection extends Configs {
    public static Connection getConnection(){
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void closeStatement(Statement statement) {

        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public static void closePreparedStatement(PreparedStatement preparedstatement) {

        try {
            if (preparedstatement != null) {
                preparedstatement.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public static void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }
}
