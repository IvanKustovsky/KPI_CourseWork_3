package com.example.coursework.dao.contract;

import com.example.coursework.database.Const;
import com.example.coursework.database.DatabaseConnection;
import com.example.coursework.dto.Contract;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContractDaoImpl implements ContractDao {

    @Override
    public Contract findById(int id) {
        Connection connection = DatabaseConnection.getConnection();
        Contract contract = null;
        String sql = "SELECT * FROM " + Const.CONTRACT_TABLE + " WHERE " + Const.CONTRACT_ID + "=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                contract = extractContractFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return contract;
    }

    @Override
    public List<Contract> findAll() {
        Connection connection = DatabaseConnection.getConnection();
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM " + Const.CONTRACT_TABLE;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                Contract contract = extractContractFromResultSet(resultSet);
                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return contracts;
    }

    @Override
    public void save(Contract entity) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "INSERT INTO " + Const.CONTRACT_TABLE +
                "(" + Const.AGENT_ID + ", " + Const.COMMERCIAL_ID + ", " + Const.CONTRACT_AMOUNT + ") " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, entity.getAgent_id());
            preparedStatement.setInt(2, entity.getCommercial_id());
            preparedStatement.setDouble(3, entity.getContract_amount());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    entity.setContract_id(generatedId);
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
    public void update(Contract entity) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "UPDATE " + Const.CONTRACT_TABLE + " SET " +
                Const.CONTRACT_AMOUNT + " = ? " +
                "WHERE " + Const.CONTRACT_ID + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, entity.getContract_amount());
            preparedStatement.setInt(2, entity.getContract_id());

            preparedStatement.executeUpdate(); // Execute the SQL UPDATE statement
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    @Override
    public void delete(Contract entity) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "DELETE FROM " + Const.CONTRACT_TABLE + " WHERE " +
                Const.CONTRACT_ID + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getContract_id());
            preparedStatement.executeUpdate(); // Execute the SQL DELETE statement
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    @Override
    public double calculateContractAmount(int adDuration, int programId) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT calculate_contract_amount(?, ?) AS contract_amount";
        double contractAmount = 0.0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Встановлення параметрів
            statement.setInt(1, adDuration);
            statement.setInt(2, programId);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Отримання результату
                if (resultSet.next()) {
                    contractAmount = resultSet.getDouble(Const.CONTRACT_AMOUNT);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contractAmount;
    }
    @Override
    public List<Contract> findAllWithDetails() {
        Connection connection = DatabaseConnection.getConnection();
        List<Contract> contracts = new ArrayList<>();
        String sql = "CALL GetAllContractsDetails()";

        try (
                CallableStatement statement = connection.prepareCall(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Contract contract = extractContractWithDetailsFromResultSet(resultSet);
                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return contracts;
    }

    private Contract extractContractWithDetailsFromResultSet(ResultSet resultSet) throws SQLException {
        int contractId = resultSet.getInt(Const.CONTRACT_ID);
        String agentFullName = resultSet.getString(Const.CONTRACT_AGENT_FULL_NAME);
        double agentRate = resultSet.getDouble(Const.CONTRACT_AGENT_RATE);
        String companyDetails = resultSet.getString(Const.CONTRACT_COMPANY_DETAILS);
        double contractAmount = resultSet.getDouble(Const.CONTRACT_AMOUNT);
        double agentCommission = resultSet.getDouble(Const.CONTRACT_AGENT_COMMISSION);

        return new Contract(contractId, agentFullName, companyDetails, agentRate,
                contractAmount, agentCommission);
    }


    private Contract extractContractFromResultSet(ResultSet resultSet) throws SQLException {
        int contractId = resultSet.getInt(Const.CONTRACT_ID);
        int agentId = resultSet.getInt(Const.AGENT_ID);
        int commercialId = resultSet.getInt(Const.COMMERCIAL_ID);
        double amount = resultSet.getDouble(Const.CONTRACT_AMOUNT);
        return new Contract(contractId, agentId, commercialId, amount);
    }
}

