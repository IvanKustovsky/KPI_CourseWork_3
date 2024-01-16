package com.example.coursework.dao.program;

import com.example.coursework.database.Const;
import com.example.coursework.database.DatabaseConnection;
import com.example.coursework.dto.Program;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramDaoImpl implements ProgramDao {

    @Override
    public Program findById(int id) {
        Connection connection = DatabaseConnection.getConnection();
        Program program = null;
        String sql = "SELECT * FROM " + Const.PROGRAM_TABLE + " WHERE " + Const.PROGRAM_ID + "=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                program = extractProgramFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return program;
    }

    @Override
    public List<Program> findAll() {
        Connection connection = DatabaseConnection.getConnection();
        List<Program> programs = new ArrayList<>();
        String sql = "SELECT * FROM " + Const.PROGRAM_TABLE;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                Program program = extractProgramFromResultSet(resultSet);
                programs.add(program);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return programs;
    }

    @Override
    public void save(Program entity) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "INSERT INTO " + Const.PROGRAM_TABLE +
                "(" + Const.PROGRAM_NAME + ", " + Const.PROGRAM_RATING + ", "
                + Const.PROGRAM_AD_RATE + ", " + Const.PROGRAM_AD_LIMIT_PER_DAY + ") " + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getProgram_name());
            preparedStatement.setDouble(2, entity.getRating());
            preparedStatement.setDouble(3, entity.getRate());
            preparedStatement.setInt(4, entity.getAd_limit_per_day());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                entity.setProgram_id(generatedId);
            } else {
                throw new SQLException("Failed to retrieve generated ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    @Override
    public void update(Program entity) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "UPDATE " + Const.PROGRAM_TABLE + " SET " +
                Const.PROGRAM_NAME + " = ?, " +
                Const.PROGRAM_RATING + " = ?, " +
                Const.PROGRAM_AD_RATE + " = ?, " +
                Const.PROGRAM_AD_LIMIT_PER_DAY + " = ? " +
                "WHERE " + Const.PROGRAM_ID + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getProgram_name());
            preparedStatement.setDouble(2, entity.getRating());
            preparedStatement.setDouble(3, entity.getRate());
            preparedStatement.setInt(4, entity.getAd_limit_per_day());
            preparedStatement.setInt(5, entity.getProgram_id());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    @Override
    public void delete(Program entity) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "DELETE FROM " + Const.PROGRAM_TABLE + " WHERE " +
                Const.PROGRAM_ID + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getProgram_id());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    @Override
    public Program findByName(String name) {
        Connection connection = DatabaseConnection.getConnection();
        Program program = null;
        String sql = "SELECT * FROM " + Const.PROGRAM_TABLE + " WHERE " + Const.PROGRAM_NAME + "=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                program = extractProgramFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return program;
    }


    private Program extractProgramFromResultSet(ResultSet resultSet) throws SQLException {
        int programId = resultSet.getInt(Const.PROGRAM_ID);
        String name = resultSet.getString(Const.PROGRAM_NAME);
        double rating = resultSet.getDouble(Const.PROGRAM_RATING);
        double adRate = resultSet.getDouble(Const.PROGRAM_AD_RATE);
        int adLimitPerDay = resultSet.getInt(Const.PROGRAM_AD_LIMIT_PER_DAY);

        return new Program(programId, name, rating, adRate, adLimitPerDay);
    }
}
