package com.example.coursework.dao.customerOrder;

import com.example.coursework.database.Const;
import com.example.coursework.database.DatabaseConnection;
import com.example.coursework.dto.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerOrderImpl implements CustomerOrderDao{
    @Override
    public List<Order> getOrderDetailsByCustomerId(int customerId) {
        Connection connection = DatabaseConnection.getConnection();
        List<Order> orderDetailsList = new ArrayList<>();
        String sql = "CALL GetOrderDetailsByCustomerId(?)";

        try (
                CallableStatement statement = connection.prepareCall(sql)
        ) {
            statement.setInt(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order customerOrder = extractOrderDetailsFromResultSet(resultSet,false);
                    orderDetailsList.add(customerOrder);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return orderDetailsList;
    }

    public List<Order> getAllOrderDetails() {
        Connection connection = DatabaseConnection.getConnection();
        List<Order> orderDetailsList = new ArrayList<>();
        String sql = "{CALL GetAllOrdersDetails}";

        try (CallableStatement statement = connection.prepareCall(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = extractOrderDetailsFromResultSet(resultSet,true);
                    orderDetailsList.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return orderDetailsList;
    }

    private Order extractOrderDetailsFromResultSet(ResultSet resultSet, boolean includeBankDetails) throws SQLException {
        int commercialId = resultSet.getInt(Const.CUSTOMER_ORDER_CUSTOMER_ID);
        String programName = resultSet.getString(Const.CUSTOMER_ORDER_PROGRAM_NAME);
        double programRating = resultSet.getDouble(Const.CUSTOMER_ORDER_PROGRAM_RATING);
        double rate = resultSet.getDouble(Const.CUSTOMER_ORDER_PROGRAM_RATE);
        Date airDate = resultSet.getDate(Const.CUSTOMER_ORDER_AIR_DATE);
        int duration = resultSet.getInt(Const.CUSTOMER_ORDER_DURATION);
        double contractAmount = resultSet.getDouble(Const.CUSTOMER_ORDER_CONTRACT_AMOUNT);
        String agentDetails = resultSet.getString(Const.CUSTOMER_ORDER_AGENT_DETAILS);

        if (includeBankDetails) {
            String customerBankDetails = resultSet.getString(Const.CUSTOMER_ORDER_CUSTOMER_BANK_DETAILS);
            return new Order(commercialId, customerBankDetails, programName, programRating,
                    rate, airDate, duration, contractAmount, agentDetails);
        } else {
            return new Order(commercialId, programName, programRating,
                    rate, airDate, duration, contractAmount, agentDetails);
        }
    }

}
