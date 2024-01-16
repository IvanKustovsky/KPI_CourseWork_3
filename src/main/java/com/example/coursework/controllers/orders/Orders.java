package com.example.coursework.controllers.orders;

import com.example.coursework.controllers.abstracts.BaseController;
import com.example.coursework.dao.customerOrder.CustomerOrderDao;
import com.example.coursework.dao.customerOrder.CustomerOrderImpl;
import com.example.coursework.database.Const;
import com.example.coursework.dto.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class Orders extends BaseController implements Initializable {
    @FXML
    private TableColumn<Order, Integer> adDurationCol;
    @FXML
    private TableColumn<Order, String> agentInfoCol;
    @FXML
    private TableColumn<Order, String> companyDetailsCol;
    @FXML
    private TableColumn<Order, Double> contractAmountCol;
    @FXML
    private TableColumn<Order, Date> dateCol;
    @FXML
    private TableColumn<Order, Integer> orderIdCol;
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> programNameCol;
    @FXML
    private TableColumn<Order, Double> programRateCol;
    @FXML
    private TableColumn<Order, Double> programRatingCol;

    private final CustomerOrderDao customerOrderDao = new CustomerOrderImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        displayFunctional();
    }

    private void loadData(){
        ordersTable.setItems(populateTable());

        orderIdCol.setCellValueFactory(new PropertyValueFactory<>(Const.CUSTOMER_ORDER_CUSTOMER_ID));
        companyDetailsCol.setCellValueFactory(new PropertyValueFactory<>(Const.CUSTOMER_ORDER_CUSTOMER_BANK_DETAILS));
        programNameCol.setCellValueFactory(new PropertyValueFactory<>(Const.CUSTOMER_ORDER_PROGRAM_NAME));
        programRatingCol.setCellValueFactory(new PropertyValueFactory<>(Const.CUSTOMER_ORDER_PROGRAM_RATING));
        programRateCol.setCellValueFactory(new PropertyValueFactory<>(Const.CUSTOMER_ORDER_PROGRAM_RATE));
        dateCol.setCellValueFactory(new PropertyValueFactory<>(Const.CUSTOMER_ORDER_AIR_DATE));
        adDurationCol.setCellValueFactory(new PropertyValueFactory<>(Const.CUSTOMER_ORDER_DURATION));
        contractAmountCol.setCellValueFactory(new PropertyValueFactory<>(Const.CUSTOMER_ORDER_CONTRACT_AMOUNT));
        agentInfoCol.setCellValueFactory(new PropertyValueFactory<>(Const.CUSTOMER_ORDER_AGENT_DETAILS));
    }

    private ObservableList<Order> populateTable() {
        return FXCollections.observableArrayList(customerOrderDao.getAllOrderDetails());
    }
}
