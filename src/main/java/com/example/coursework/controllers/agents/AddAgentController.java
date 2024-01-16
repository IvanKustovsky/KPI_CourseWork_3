package com.example.coursework.controllers.agents;

import com.example.coursework.controllers.abstracts.BaseController;
import com.example.coursework.dao.agent.AgentDao;
import com.example.coursework.dao.agent.AgentDaoImpl;
import com.example.coursework.dto.Agent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAgentController extends BaseController implements Initializable {
    @FXML
    private TextField agentCommissionRateId;
    @FXML
    private TextField agentNameId;
    @FXML
    private TextField agentSurnameId;
    @FXML
    private Label successLabel;
    private final AgentDao agentDao = new AgentDaoImpl();

    @FXML
    void save() {
        String agentName = agentNameId.getText();
        String agentSurname = agentSurnameId.getText();
        String agentCommissionRateStr = agentCommissionRateId.getText();
        double agentCommissionRate = getDoubleValue(agentCommissionRateStr, "Комісійна ставка");
        if(agentCommissionRate == -1)
        {return;} // -1 означає, що введено невірний формат для чисел

        if (agentName.isEmpty() || agentSurname.isEmpty() || agentCommissionRateStr.isEmpty()
                || agentCommissionRate < 0 || agentCommissionRate > 25) {
            alert.setContentText("Введіть ім'я, прізвище та комісійну ставку агента (0-25).");
            alert.showAndWait();
        } else {
            agentDao.save(new Agent(agentName, agentSurname, agentCommissionRate));
            successLabel.setVisible(true);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e ->{
                clean();
                successLabel.setVisible(false);
            }));
            timeline.setCycleCount(1);
            timeline.play();
        }
    }

    private void clean() {
        agentSurnameId.setText(null);
        agentNameId.setText(null);
        agentCommissionRateId.setText(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
