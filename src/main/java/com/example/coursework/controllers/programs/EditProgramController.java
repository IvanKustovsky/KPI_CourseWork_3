package com.example.coursework.controllers.programs;

import com.example.coursework.controllers.abstracts.BaseController;
import com.example.coursework.dao.program.ProgramDao;
import com.example.coursework.dao.program.ProgramDaoImpl;
import com.example.coursework.dto.Program;
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

public class EditProgramController extends BaseController implements Initializable {
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
    private final Program currentSelectedProgram = BaseController.selectedProgram;
    private final ProgramDao programDao = new ProgramDaoImpl();

    @FXML
    void save() {
        int programId = currentSelectedProgram.getProgram_id();
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
            programDao.update(new Program(programId,programName,
                    programRating,programRate,(int)programLimitPerDay));
            Stage stage = (Stage) successLabel.getScene().getWindow();
            successLabel.setVisible(true);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> stage.close()));
            timeline.setCycleCount(1);
            timeline.play();

        }
    }
    private void setTextField(Program program) {
        programNameId.setText(program.getProgram_name());
        programRatingId.setText(String.valueOf(program.getRating()));
        programRateId.setText(String.valueOf(program.getRate()));
        programLimitId.setText(String.valueOf(program.getAd_limit_per_day()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTextField(currentSelectedProgram);
    }

}
