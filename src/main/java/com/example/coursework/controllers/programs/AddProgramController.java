package com.example.coursework.controllers.programs;

import com.example.coursework.controllers.abstracts.BaseController;
import com.example.coursework.dao.program.ProgramDao;
import com.example.coursework.dao.program.ProgramDaoImpl;
import com.example.coursework.dto.Program;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class AddProgramController extends BaseController  {

    @FXML
    private TextField programLimitId;

    @FXML
    private TextField programNameId;

    @FXML
    private TextField programRateId;

    @FXML
    private TextField programRatingId;
    @FXML
    private Label successLabel;
    private final ProgramDao programDao = new ProgramDaoImpl();

    @FXML
    void save() {
        String programName = programNameId.getText();
        String programRatingStr = programRatingId.getText();
        String programRateStr = programRateId.getText();
        String programLimitPerDayStr = programLimitId.getText();
        double programRating = getDoubleValue(programRatingStr,"Рейтинг");
        double programRate = getDoubleValue(programRateStr,"Вартість");
        double programLimitPerDay = getDoubleValue(programLimitPerDayStr,"Обмеження");
        if(programRating == -1 || programRate == -1 || programLimitPerDay == -1)
        {return;} // -1 означає, що введено невірний формат для чисел
        if (programName.isEmpty() || programRateStr.isEmpty() || programRatingStr.isEmpty()
                || programLimitPerDayStr.isEmpty() || programRating > 100 || programRating < 0 ||
        programRate < 200 || programRate > 10000 || programLimitPerDay > 10) {
            alert.setContentText("""
                    Введіть всі дані. Рейтинг має бути в межах від 0 до 100.
                    Вартість від 200 до 10000.
                    Обмеження від 0 до 10.\s""");
            alert.showAndWait();
        } else {
            programDao.save(new Program(programName, programRating, programRate, (int)programLimitPerDay));
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
        programNameId.setText(null);
        programRatingId.setText(null);
        programRateId.setText(null);
        programLimitId.setText(null);
    }

}
