package com.example.coursework.controllers;

import com.example.coursework.controllers.abstracts.BaseController;
import com.example.coursework.dao.contract.ContractDao;
import com.example.coursework.dao.contract.ContractDaoImpl;
import com.example.coursework.database.Const;
import com.example.coursework.dto.Contract;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ContractsController extends BaseController implements Initializable {

    @FXML
    private TableColumn<Contract, String> agentCol;
    @FXML
    private TableColumn<Contract, Double> agentCommissionCol;
    @FXML
    private TableColumn<Contract, Double> agentRateCol;
    @FXML
    private TableColumn<Contract, String> companyBankDetailsCol;
    @FXML
    private TableColumn<Contract, Double> contractAmountCol;
    @FXML
    private TableColumn<Contract, Integer> contractIdCol;
    @FXML
    private TableView<Contract> contractsTable;
    private final ContractDao contractDao = new ContractDaoImpl();

    private void loadData(){
        contractsTable.setItems(populateTable());

        // Set the cell value factories for each column
        contractIdCol.setCellValueFactory(new PropertyValueFactory<>(Const.CONTRACT_ID));
        agentCol.setCellValueFactory(new PropertyValueFactory<>(Const.CONTRACT_AGENT_FULL_NAME));
        agentRateCol.setCellValueFactory(new PropertyValueFactory<>(Const.CONTRACT_AGENT_RATE));
        companyBankDetailsCol.setCellValueFactory(new PropertyValueFactory<>(Const.CONTRACT_COMPANY_DETAILS));
        contractAmountCol.setCellValueFactory(new PropertyValueFactory<>(Const.CONTRACT_AMOUNT));
        agentCommissionCol.setCellValueFactory(new PropertyValueFactory<>(Const.CONTRACT_AGENT_COMMISSION));
    }

    private ObservableList<Contract> populateTable() {
        return FXCollections.observableArrayList(contractDao.findAllWithDetails());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        displayFunctional();
    }

}
