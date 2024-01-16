package com.example.coursework.dao.agent;

import com.example.coursework.database.Const;
import com.example.coursework.database.DatabaseConnection;
import com.example.coursework.dto.Agent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class AgentDaoImpl implements AgentDao{
        @Override
        public Agent findById(int id) {
            Connection connection = DatabaseConnection.getConnection();
            Agent agent = null;
            String sql = "SELECT * FROM " + Const.AGENT_TABLE + " WHERE " + Const.AGENT_ID + "=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    agent = extractAgentFromResultSet(resultSet);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(connection);
            }

            return agent;
        }


        @Override
        public List<Agent> findAll() {
            Connection connection = DatabaseConnection.getConnection();
            List<Agent> agents = new ArrayList<>();
            String sql = "SELECT * FROM " + Const.AGENT_TABLE;

            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                while (resultSet.next()) {
                    Agent agent = extractAgentFromResultSet(resultSet);
                    agents.add(agent);
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle or log the exception as needed
            } finally {
                DatabaseConnection.closeConnection(connection);
            }

            return agents;
        }

        @Override
        public void save(Agent entity) {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "INSERT INTO " + Const.AGENT_TABLE +
                    "(" + Const.AGENT_NAME + ", " + Const.AGENT_SURNAME + ", " + Const.AGENT_COMMISSION_RATE + ") " +
                    "VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, entity.getAgent_name());
                preparedStatement.setString(2, entity.getAgent_surname());
                preparedStatement.setDouble(3, entity.getCommission_rate());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        entity.setAgent_id(generatedId);
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
        public void update(Agent entity) {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "UPDATE " + Const.AGENT_TABLE + " SET " +
                    Const.AGENT_NAME + " = ?, " +
                    Const.AGENT_SURNAME + " = ?, " +
                    Const.AGENT_COMMISSION_RATE + " = ? " +
                    "WHERE " + Const.AGENT_ID + " = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, entity.getAgent_name());
                preparedStatement.setString(2, entity.getAgent_surname());
                preparedStatement.setDouble(3, entity.getCommission_rate());
                preparedStatement.setInt(4, entity.getAgent_id());

                preparedStatement.executeUpdate(); // Execute the SQL UPDATE statement
            } catch (SQLException e) {
                e.printStackTrace(); // Handle or log the exception as needed
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
            updateAgentRevenue();
        }

        @Override
        public void delete(Agent entity) {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "DELETE FROM " + Const.AGENT_TABLE + " WHERE " +
                    Const.AGENT_NAME + " = ? AND " +
                    Const.AGENT_SURNAME + " = ? AND " +
                    Const.AGENT_COMMISSION_RATE + " = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, entity.getAgent_name());
                preparedStatement.setString(2, entity.getAgent_surname());
                preparedStatement.setDouble(3, entity.getCommission_rate());

                preparedStatement.executeUpdate(); // Виконати SQL-запит DELETE
            } catch (SQLException e) {
                e.printStackTrace(); // Обробити або зареєструвати виняток, якщо потрібно
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }

        @Override
        public List<String> findAllAgentsDetails() {
            Connection connection = DatabaseConnection.getConnection();
            List<String> agentDetailsList = new ArrayList<>();
            String sql = "{CALL GetAllAgentDetails()}";

            try (PreparedStatement preparedStatement = connection.prepareCall(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String agentDetails = resultSet.getString(Const.AGENT_DETAILS);
                    agentDetailsList.add(agentDetails);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(connection);
            }

            return agentDetailsList;
        }

        public void updateAgentRevenue() {
            Connection connection = DatabaseConnection.getConnection();
            try {
                // Assuming you have a stored procedure named CalculateAgentRevenue
                String storedProcedure = "{CALL CalculateAgentRevenue()}";

                try (PreparedStatement statement = connection.prepareStatement(storedProcedure)) {
                    statement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();  // Handle the exception according to your application's needs
            }
        }

        private Agent extractAgentFromResultSet(ResultSet resultSet) throws SQLException {
            int agentId = resultSet.getInt(Const.AGENT_ID);
            String agentName = resultSet.getString(Const.AGENT_NAME);
            String agentSurname = resultSet.getString(Const.AGENT_SURNAME);
            double commissionRate = resultSet.getDouble(Const.AGENT_COMMISSION_RATE);
            double totalRevenue = resultSet.getDouble(Const.AGENT_TOTAL_REVENUE);
            return new Agent(agentId, agentName, agentSurname, commissionRate, totalRevenue);
        }
}
