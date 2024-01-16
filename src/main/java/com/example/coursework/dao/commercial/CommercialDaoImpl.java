package com.example.coursework.dao.commercial;

import com.example.coursework.database.Const;
import com.example.coursework.database.DatabaseConnection;
import com.example.coursework.dto.Commercial;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommercialDaoImpl implements CommercialDao {

    @Override
    public Commercial findById(int id) {
        Connection connection = DatabaseConnection.getConnection();
        Commercial commercial = null;
        String sql = "SELECT * FROM " + Const.COMMERCIAL_TABLE + " WHERE " + Const.COMMERCIAL_ID + "=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                commercial = extractCommercialFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return commercial;
    }

    @Override
    public List<Commercial> findAll() {
        Connection connection = DatabaseConnection.getConnection();
        List<Commercial> commercials = new ArrayList<>();
        String sql = "SELECT * FROM " + Const.COMMERCIAL_TABLE;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                Commercial commercial = extractCommercialFromResultSet(resultSet);
                commercials.add(commercial);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return commercials;
    }

    @Override
    public void save(Commercial entity) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "INSERT INTO " + Const.COMMERCIAL_TABLE +
                "(" + Const.COMMERCIAL_CUSTOMER_ID + ", " + Const.COMMERCIAL_PROGRAM_ID + ", " +
                Const.COMMERCIAL_AIR_DATE + ", " + Const.COMMERCIAL_DURATION + ") " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, entity.getCustomer_id());
            preparedStatement.setInt(2, entity.getProgram_id());
            preparedStatement.setDate(3, Date.valueOf(entity.getAir_date()));
            preparedStatement.setInt(4, entity.getDuration());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    entity.setCommercial_id(generatedId);
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
    public void update(Commercial entity) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "UPDATE " + Const.COMMERCIAL_TABLE + " SET " +
                Const.COMMERCIAL_CUSTOMER_ID + " = ?, " +
                Const.COMMERCIAL_PROGRAM_ID + " = ?, " +
                Const.COMMERCIAL_AIR_DATE + " = ?, " +
                Const.COMMERCIAL_DURATION + " = ? " +
                "WHERE " + Const.COMMERCIAL_ID + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getCustomer_id());
            preparedStatement.setInt(2, entity.getProgram_id());
            preparedStatement.setDate(3, Date.valueOf(entity.getAir_date()));
            preparedStatement.setInt(4, entity.getDuration());
            preparedStatement.setInt(5, entity.getCommercial_id());

            preparedStatement.executeUpdate(); // Execute the SQL UPDATE statement
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    @Override
    public void delete(Commercial entity) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "DELETE FROM " + Const.COMMERCIAL_TABLE + " WHERE " +
                Const.COMMERCIAL_CUSTOMER_ID + " = ? AND " +
                Const.COMMERCIAL_PROGRAM_ID + " = ? AND " +
                Const.COMMERCIAL_AIR_DATE + " = ? AND " +
                Const.COMMERCIAL_DURATION + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getCustomer_id());
            preparedStatement.setInt(2, entity.getProgram_id());
            preparedStatement.setDate(3, Date.valueOf(entity.getAir_date()));
            preparedStatement.setInt(4, entity.getDuration());

            preparedStatement.executeUpdate(); // Execute the SQL DELETE statement
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    @Override
    public int getAdCountPerDay(int program_id, LocalDate date) {
        Connection connection = DatabaseConnection.getConnection();
        int adCount = 0;

        String sql = "SELECT get_ad_count(?, ?) AS ad_count";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, program_id);
            preparedStatement.setDate(2, java.sql.Date.valueOf(date));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                adCount = resultSet.getInt("ad_count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return adCount;
    }

    private Commercial extractCommercialFromResultSet(ResultSet resultSet) throws SQLException {
        int commercialId = resultSet.getInt(Const.COMMERCIAL_ID);
        int customerId = resultSet.getInt(Const.COMMERCIAL_CUSTOMER_ID);
        int programId = resultSet.getInt(Const.COMMERCIAL_PROGRAM_ID);
        LocalDate airDate = resultSet.getDate(Const.COMMERCIAL_AIR_DATE).toLocalDate();
        int duration = resultSet.getInt(Const.COMMERCIAL_DURATION);
        return new Commercial(commercialId, customerId, programId, airDate, duration);
    }

}

