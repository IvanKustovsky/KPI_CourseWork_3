package com.example.coursework.controllers.abstracts;


import com.example.coursework.controllers.HomePageController;
import com.example.coursework.dto.Agent;
import com.example.coursework.dto.Program;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;



public abstract class BaseController extends SceneOpenerController {
    @FXML
    protected Button agentsBtn;
    @FXML
    protected Button contractsBtn;
    @FXML
    protected Button customerOrdersBtn;
    @FXML
    protected Button ordersBtn;
    @FXML
    protected Button personalCabBtn;
    @FXML
    protected Button programsBtn;
    protected static boolean isPersonalCabinetSceneOpen = true;
    protected static boolean isProgramsSceneOpen = false;
    protected static boolean isCustomerOrdersSceneOpen = false;
    protected static boolean isAgentsSceneOpen = false;
    protected static boolean isContractsSceneOpen = false;
    protected static boolean isOrdersSceneOpen = false;
    protected static Agent selectedAgent = null;
    protected static Program selectedProgram = null;
    protected final Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML
    protected void switchToAgents() {
        if (!isAgentsSceneOpen) {
            openNewSceneAndCloseCurrent("Агенти","/com/example/coursework/agents.fxml");
            resetScenesToFalse();
            isAgentsSceneOpen = true;
        }
    }

    @FXML
    protected void switchToContracts() {
        if (!isContractsSceneOpen) {
            openNewSceneAndCloseCurrent("Контракти","/com/example/coursework/contracts.fxml");
            resetScenesToFalse();
            isContractsSceneOpen = true;
        }
    }

    @FXML
    protected void switchToCustomerOrders() {
        if (!isCustomerOrdersSceneOpen) {
            openNewSceneAndCloseCurrent("Ваші замовлення","/com/example/coursework/customerOrders.fxml");
            resetScenesToFalse();
            isCustomerOrdersSceneOpen = true;
        }
    }

    @FXML
    protected void switchToOrders() {
        if (!isOrdersSceneOpen) {
            openNewSceneAndCloseCurrent("Замовлення","/com/example/coursework/orders.fxml");
            resetScenesToFalse();
            isOrdersSceneOpen = true;
        }
    }

    @FXML
    protected void switchToPersonalCabinet() {
        if (!isPersonalCabinetSceneOpen) {
            openNewSceneAndCloseCurrent("Особистий кабінет","/com/example/coursework/personalCabinet.fxml");
            resetScenesToFalse();
            isPersonalCabinetSceneOpen = true;
        }
    }

    @FXML
    protected void switchToPrograms() {
        if (!isProgramsSceneOpen) {
            openNewSceneAndCloseCurrent("Телепередачі","/com/example/coursework/tv-programs.fxml");
            resetScenesToFalse();
            isProgramsSceneOpen = true;
        }
    }

    protected void displayFunctional(){
        if (HomePageController.authenticatedCustomer.isAdmin()) {
            customerOrdersBtn.setVisible(false);
        } else {
            contractsBtn.setVisible(false);
            ordersBtn.setVisible(false);
        }
    }

    protected double getDoubleValue(String value, String fieldName) {
        if (value.isEmpty()){
            return -1;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            showAlert(fieldName + " не є числом!");
            return -1; // або інше значення за замовчуванням
        }
    }
    private void showAlert(String content) {
        alert.setHeaderText("Помилка");
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void resetScenesToFalse() {
        isAgentsSceneOpen = false;
        isCustomerOrdersSceneOpen = false;
        isContractsSceneOpen = false;
        isOrdersSceneOpen = false;
        isPersonalCabinetSceneOpen = false;
        isProgramsSceneOpen = false;
    }
}
