package com.example.coursework.controllers.orders;

import com.example.coursework.controllers.abstracts.BaseController;
import com.example.coursework.controllers.HomePageController;
import com.example.coursework.dao.agent.AgentDao;
import com.example.coursework.dao.agent.AgentDaoImpl;
import com.example.coursework.dao.commercial.CommercialDao;
import com.example.coursework.dao.commercial.CommercialDaoImpl;
import com.example.coursework.dao.contract.ContractDaoImpl;
import com.example.coursework.dao.program.ProgramDao;
import com.example.coursework.dao.program.ProgramDaoImpl;
import com.example.coursework.dto.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OrderAdvertisement extends BaseController implements Initializable {

    @FXML
    private ChoiceBox<Integer> adDurationCB;
    @FXML
    private ChoiceBox<String> agentAndRateCB;
    @FXML
    private TextField contractAmountId;
    @FXML
    private DatePicker dataPickerId;
    @FXML
    private Label agentError;
    @FXML
    private Label dateError;
    @FXML
    private Label durationError;
    @FXML
    private Label programNameError;
    @FXML
    private Button orderAdvertisementBtn;
    @FXML
    private ChoiceBox<String> programsCB;

    private final ProgramDao programDao = new ProgramDaoImpl();
    private final AgentDao agentDao = new AgentDaoImpl();
    private final ContractDaoImpl contractDao = new ContractDaoImpl();
    private final CommercialDao commercialDao = new CommercialDaoImpl();
    @FXML
    void orderNewAdvertisement() {
        resetErrors();
        if (isDataCorrect()) {
            if(isReachedAdLimitPerDay()) {
                showLimitExceededError();
            } else {
                createNewCommercial();
                agentError.setText("Операція пройшла успішно.");
                agentError.setStyle("-fx-text-fill: green;");
                agentError.setVisible(true);
                Stage stage = ((Stage) orderAdvertisementBtn.getScene().getWindow());
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> stage.close()));
                timeline.setCycleCount(1);
                timeline.play();
            }

        }
    }

    private boolean isReachedAdLimitPerDay() {
        String programName = programsCB.getValue();
        LocalDate selectedDate = dataPickerId.getValue();

        Program program = programDao.findByName(programName);
        int programId = program.getProgram_id();
        int adCountPerDay = commercialDao.getAdCountPerDay(programId, selectedDate);
        int adLimitPerDay = program.getAd_limit_per_day();

        return adCountPerDay >= adLimitPerDay;
    }

    private void showLimitExceededError() {
        programNameError.setText("Ліміт реклами в даній телепередачі в обрану дату вичерпано.");
        dateError.setText("Оберіть іншу дату або іншу телепередачу.");
        programNameError.setVisible(true);
        dateError.setVisible(true);
    }

    private void createNewCommercial() {
        int customerId = HomePageController.authenticatedCustomer.getCustomerId();
        int programId = programDao.findByName(programsCB.getValue()).getProgram_id();
        Commercial commercial = new Commercial(customerId,programId,dataPickerId.getValue(), adDurationCB.getValue());
        commercialDao.save(commercial);
        int agentId = extractIdFromComboBox(agentAndRateCB.getValue());
        double contractAmount = getDoubleValue(contractAmountId.getText(),"Значення контракту");
        Contract contract = new Contract(agentId,commercial.getCommercial_id(),contractAmount);
        contractDao.save(contract);
        agentDao.updateAgentRevenue();
    }

    private void resetErrors(){
        programNameError.setText("Оберіть назву телепередачі.");
        dateError.setText("Визначте дату майбутнього показу реклами.");
        programNameError.setVisible(false);
        dateError.setVisible(false);
        durationError.setVisible(false);
        agentError.setVisible(false);
    }

    private boolean isDataCorrect() {
        if (programsCB.getValue() == null) {
            programNameError.setVisible(true);
        }
        if (dataPickerId.getValue() == null || dataPickerId.getValue().isBefore(LocalDate.now())) {
            dateError.setVisible(true);
        }
        if (adDurationCB.getValue() == null) {
            durationError.setVisible(true);
        }
        if (agentAndRateCB.getValue() == null) {
            agentError.setVisible(true);
        }
        return !programNameError.isVisible() && !dateError.isVisible()
                && !durationError.isVisible() && !agentError.isVisible();
    }

    private int extractIdFromComboBox(String comboBoxValue) {
        // Розділити рядок за символом "|"
        String[] parts = comboBoxValue.split("\\|");
        // Отримати перший елемент та видалити пробіли
        String idPart = parts[0].trim();
        // Розділити рядок за символом ":"
        String[] idSplit = idPart.split(":");
        // Отримати другий елемент та видалити пробіли
        String idString = idSplit[1].trim();
        // Перевірити, чи значення є числовим
        try {
            return Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            // Обробити випадок, коли значення не є числовим
            System.err.println("Invalid numeric value: " + idString);
            return -1; // Повернути значення за замовчуванням або обробити помилку за потребою
        }
    }

    private void calculateContractAmount() {
        Integer selectedDuration = adDurationCB.getValue();
        String selectedProgram = programsCB.getValue();

        if (selectedDuration != null && selectedProgram != null) {
            int programId = programDao.findByName(selectedProgram).getProgram_id();
            contractAmountId.setText(String.valueOf(contractDao.calculateContractAmount(selectedDuration, programId)));
        }
    }

    private void loadData(){
        ObservableList<Integer> duration = FXCollections.observableArrayList();
        for (int i = 5; i <= 60; i++) {
            duration.add(i);
        }
        agentAndRateCB.setItems(FXCollections.observableArrayList(agentDao.findAllAgentsDetails()));
        adDurationCB.setItems(duration);
        ObservableList<String> programs = FXCollections.observableArrayList();
        programDao.findAll().stream()
                .map(Program::getProgram_name)
                .forEach(programs::add);
        programsCB.setItems(programs);

        adDurationCB.setOnAction(event -> calculateContractAmount());
        programsCB.setOnAction(event -> calculateContractAmount());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }
}
