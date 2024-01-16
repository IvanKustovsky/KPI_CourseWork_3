package com.example.coursework.dao.customer;


import com.example.coursework.database.Const;
import com.example.coursework.database.DatabaseConnection;
import com.example.coursework.dto.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao{
    @Override
    public Customer findById(int id) {
        Connection connection = DatabaseConnection.getConnection();
        Customer customer = null;
        String sql = "SELECT * FROM " + Const.CUSTOMER_TABLE + " WHERE " + Const.CUSTOMER_ID + "=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                customer = extractCustomerFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return customer;
    }


    @Override
    public List<Customer> findAll() {
        Connection connection = DatabaseConnection.getConnection();
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM " + Const.CUSTOMER_TABLE;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                Customer customer = extractCustomerFromResultSet(resultSet);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return customers;
    }

    @Override
    public void save(Customer entity) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO " + Const.CUSTOMER_TABLE +
                "(" + Const.CUSTOMER_BANK_DETAILS + ", " + Const.CUSTOMER_PHONE + ", " +
                Const.CUSTOMER_EMAIL + ", " + Const.CUSTOMER_PASSWORD + ", " +
                Const.CUSTOMER_CONTACT_PERSON_ID + ", " + Const.CUSTOMER_IS_ADMIN + ") " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getBankDetails());
            preparedStatement.setString(2, entity.getPhone());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getPassword());
            preparedStatement.setInt(5, entity.getContactPersonId());
            preparedStatement.setBoolean(6, entity.isAdmin());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    entity.setCustomerId(generatedId); // Assuming you have a setter for customerId
                } else {
                    throw new SQLException("Failed to retrieve generated ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }

    @Override
    public void update(Customer entity) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "UPDATE " + Const.CUSTOMER_TABLE + " SET " +
                Const.CUSTOMER_BANK_DETAILS + " = ?, " +
                Const.CUSTOMER_PHONE + " = ?, " +
                Const.CUSTOMER_EMAIL + " = ?, " +
                Const.CUSTOMER_PASSWORD + " = ?, " +
                Const.CUSTOMER_CONTACT_PERSON_ID + " = ?, " +
                Const.CUSTOMER_IS_ADMIN + " = ? " +
                "WHERE " + Const.CUSTOMER_ID + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getBankDetails());
            preparedStatement.setString(2, entity.getPhone());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getPassword());
            preparedStatement.setInt(5, entity.getContactPersonId());
            preparedStatement.setBoolean(6, entity.isAdmin());
            preparedStatement.setInt(7, entity.getCustomerId());

            preparedStatement.executeUpdate(); // Execute the SQL UPDATE statement
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    @Override
    public void delete(Customer entity) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "DELETE FROM " + Const.CUSTOMER_TABLE + " WHERE " +
                Const.CUSTOMER_BANK_DETAILS + " = ? AND " +
                Const.CUSTOMER_PHONE + " = ? AND " +
                Const.CUSTOMER_EMAIL + " = ? AND " +
                Const.CUSTOMER_PASSWORD + " = ? AND " +
                Const.CUSTOMER_CONTACT_PERSON_ID + " = ? AND " +
                Const.CUSTOMER_IS_ADMIN + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getBankDetails());
            preparedStatement.setString(2, entity.getPhone());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getPassword());
            preparedStatement.setInt(5, entity.getContactPersonId());
            preparedStatement.setBoolean(6, entity.isAdmin());

            preparedStatement.executeUpdate(); // Execute the SQL DELETE statement
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    private Customer extractCustomerFromResultSet(ResultSet resultSet) throws SQLException {
        int customerId = resultSet.getInt(Const.CUSTOMER_ID);
        int contact_person_id = resultSet.getInt(Const.CUSTOMER_CONTACT_PERSON_ID);
        String bankDetails = resultSet.getString(Const.CUSTOMER_BANK_DETAILS);
        String phone = resultSet.getString(Const.CUSTOMER_PHONE);
        String email = resultSet.getString(Const.CUSTOMER_EMAIL);
        String password = resultSet.getString(Const.CUSTOMER_PASSWORD);
        boolean isAdmin = resultSet.getBoolean(Const.CUSTOMER_IS_ADMIN);
        return new Customer(customerId,contact_person_id,bankDetails,phone,email,password,isAdmin);
    }
}
