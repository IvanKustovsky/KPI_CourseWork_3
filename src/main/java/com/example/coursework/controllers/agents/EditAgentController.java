package com.example.coursework.controllers.agents;

import com.example.coursework.controllers.abstracts.BaseController;
import com.example.coursework.dao.agent.AgentDaoImpl;
import com.example.coursework.dto.Agent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class EditAgentController extends BaseController implements Initializable {
    @FXML
    private TextField agentCommissionRateId;
    @FXML
    private TextField agentNameId;
    @FXML
    private TextField agentSurnameId;
    @FXML
    private Label successLabel;
    private final Agent currentSelectedAgent = BaseController.selectedAgent;
    private final AgentDaoImpl agentDao = new AgentDaoImpl();
    @FXML
    void save() {
        int agentId = currentSelectedAgent.getAgent_id();
        String agentName = agentNameId.getText();
        String agentSurname = agentSurnameId.getText();
        String agentCommissionRateStr = agentCommissionRateId.getText();
        double agentCommissionRate = getDoubleValue(agentCommissionRateStr, "Комісійна ставка");
        if(agentCommissionRate == -1)
        {return;} // -1 означає, що введено невірний формат для чисел
        if (agentName.isEmpty() || agentSurname.isEmpty() || agentCommissionRateStr.isEmpty()
                || agentCommissionRate < 0 || agentCommissionRate > 100) {
            alert.setContentText("Введіть ім'я, прізвище та комісійну ставку агента(0-99).");
            alert.showAndWait();
        } else {
            agentDao.update(new Agent(agentId,agentName,agentSurname,agentCommissionRate));
            Stage stage = (Stage) successLabel.getScene().getWindow();
            successLabel.setVisible(true);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> stage.close()));
            timeline.setCycleCount(1);
            timeline.play();

        }
    }
    private void setTextField(Agent agent) {
        agentNameId.setText(agent.getAgent_name());
        agentSurnameId.setText(agent.getAgent_surname());
        agentCommissionRateId.setText(String.valueOf(agent.getCommission_rate()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTextField(currentSelectedAgent);
    }


}
