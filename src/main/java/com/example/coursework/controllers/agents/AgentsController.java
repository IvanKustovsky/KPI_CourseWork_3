package com.example.coursework.controllers.agents;

import com.example.coursework.controllers.abstracts.BaseController;
import com.example.coursework.controllers.HomePageController;
import com.example.coursework.dao.agent.AgentDaoImpl;
import com.example.coursework.database.Const;
import com.example.coursework.dto.Agent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AgentsController extends BaseController implements Initializable {

    @FXML
    private ImageView addDataImage;

    @FXML
    private TableColumn<Agent, Double> agentCommissionRateCol;

    @FXML
    private TableColumn<Agent, Integer> agentIdCol;

    @FXML
    private TableColumn<Agent, String> agentNameCol;

    @FXML
    private TableColumn<Agent, String> agentSurnameCol;
    @FXML
    private TableColumn<Agent, Double> agentRevenueCol;
    @FXML
    private TableView<Agent> agentsTable;
    @FXML
    private ImageView deleteDataImage;

    @FXML
    private ImageView editDataImage;

    @FXML
    private ImageView refreshTableImage;

    private final AgentDaoImpl agentDao = new AgentDaoImpl();

    @FXML
    void deleteData() {
        selectedAgent = agentsTable.getSelectionModel().getSelectedItem();
        if (selectedAgent != null) {
            agentDao.delete(selectedAgent);
            refreshTable();
        } else {
            alert.setHeaderText(null);
            alert.setContentText("Щоб видалити запис спочатку оберіть його!");
            alert.showAndWait();
        }
    }

    @FXML
    void editData() {
        selectedAgent = agentsTable.getSelectionModel().getSelectedItem();
        if (selectedAgent != null) {
            openAddOrEditWindow(false);
        } else {
            alert.setHeaderText(null);
            alert.setContentText("Щоб оновити запис спочатку оберіть його!");
            alert.showAndWait();
        }
    }

    @FXML
    void getAddView() {
        openAddOrEditWindow(true);
    }

    @FXML
    void refreshTable() {
        ObservableList<Agent> observableAgentList = FXCollections.observableArrayList(agentDao.findAll());
        agentsTable.setItems(observableAgentList);
    }

    @FXML
    void showAddTip() {
        if (addDataImage.isVisible()) {
            Tooltip tooltip = new Tooltip("Додати дані");
            Tooltip.install(addDataImage, tooltip);
        }
    }

    @FXML
    void showDeleteTip() {
        if (deleteDataImage.isVisible()) {
            Tooltip tooltip = new Tooltip("Видалити дані");
            Tooltip.install(deleteDataImage, tooltip);
        }
    }

    @FXML
    void showEditTip() {
        if (editDataImage.isVisible()) {
            Tooltip tooltip = new Tooltip("Редагувати дані");
            Tooltip.install(editDataImage, tooltip);
        }
    }

    @FXML
    void showRefreshTip() {
        if (refreshTableImage.isVisible()) {
            Tooltip tooltip = new Tooltip("Оновити таблицю");
            Tooltip.install(refreshTableImage, tooltip);
        }
    }

    private void openAddOrEditWindow(boolean isAdding) {
        if (isAdding) {
            openNewScene("Додавання агента", "/com/example/coursework/addAgent.fxml");
        } else {
            openNewScene("Редагування агента", "/com/example/coursework/editAgent.fxml");
        }
    }

    private void loadData() {
        List<Agent> agentList = agentDao.findAll();
        // Convert the list to an ObservableList (required for TableView)
        ObservableList<Agent> observableAgentList = FXCollections.observableArrayList(agentList);

        // Set the cell value factories for each column
        agentIdCol.setCellValueFactory(new PropertyValueFactory<>(Const.AGENT_ID));
        agentNameCol.setCellValueFactory(new PropertyValueFactory<>(Const.AGENT_NAME));
        agentSurnameCol.setCellValueFactory(new PropertyValueFactory<>(Const.AGENT_SURNAME));
        agentCommissionRateCol.setCellValueFactory(new PropertyValueFactory<>(Const.AGENT_COMMISSION_RATE));
        agentRevenueCol.setCellValueFactory(new PropertyValueFactory<>(Const.AGENT_TOTAL_REVENUE));

        // Populate the table with the data
        agentsTable.setItems(observableAgentList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        displayFunctional();
        if(HomePageController.authenticatedCustomer.isAdmin()){
            addDataImage.setVisible(true);
            editDataImage.setVisible(true);
            refreshTableImage.setVisible(true);
            deleteDataImage.setVisible(true);
        }
    }
}
