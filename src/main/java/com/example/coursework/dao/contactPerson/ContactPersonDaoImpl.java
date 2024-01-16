package com.example.coursework.dao.contactPerson;

import com.example.coursework.database.Const;
import com.example.coursework.database.DatabaseConnection;
import com.example.coursework.dto.ContactPerson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactPersonDaoImpl implements ContactPersonDao {

    @Override
    public ContactPerson findById(int id) {
        Connection connection = DatabaseConnection.getConnection();
        ContactPerson contactPerson = null;
        String sql = "SELECT * FROM " + Const.CONTACT_PERSON_TABLE + " WHERE " + Const.CONTACT_PERSON_ID + "=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                contactPerson = extractContactPersonFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return contactPerson;
    }

    @Override
    public List<ContactPerson> findAll() {
        Connection connection = DatabaseConnection.getConnection();
        List<ContactPerson> contactPersons = new ArrayList<>();
        String sql = "SELECT * FROM " + Const.CONTACT_PERSON_TABLE;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                ContactPerson contactPerson = extractContactPersonFromResultSet(resultSet);
                contactPersons.add(contactPerson);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return contactPersons;
    }

    @Override
    public void save(ContactPerson entity) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO " + Const.CONTACT_PERSON_TABLE +
                "(" + Const.CONTACT_PERSON_NAME + ", " + Const.CONTACT_PERSON_SURNAME + ") " +
                "VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    entity.setId(generatedId);
                } else {
                    throw new SQLException("Failed to retrieve generated ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    @Override
    public void update(ContactPerson entity) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "UPDATE " + Const.CONTACT_PERSON_TABLE + " SET " +
                Const.CONTACT_PERSON_NAME + " = ?, " +
                Const.CONTACT_PERSON_SURNAME + " = ? " +
                "WHERE " + Const.CONTACT_PERSON_ID + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setInt(3, entity.getId());

            preparedStatement.executeUpdate(); // Execute the SQL UPDATE statement
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    @Override
    public void delete(ContactPerson entity) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "DELETE FROM " + Const.CONTACT_PERSON_TABLE + " WHERE " +
                Const.CONTACT_PERSON_NAME + " = ? AND " +
                Const.CONTACT_PERSON_SURNAME + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());

            preparedStatement.executeUpdate(); // Execute the SQL DELETE statement
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    private ContactPerson extractContactPersonFromResultSet(ResultSet resultSet) throws SQLException {
        int contactPersonId = resultSet.getInt(Const.CONTACT_PERSON_ID);
        String name = resultSet.getString(Const.CONTACT_PERSON_NAME);
        String surname = resultSet.getString(Const.CONTACT_PERSON_SURNAME);
        return new ContactPerson(contactPersonId, name, surname);
    }
}

