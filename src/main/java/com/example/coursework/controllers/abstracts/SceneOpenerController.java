package com.example.coursework.controllers.abstracts;

import com.example.coursework.AppRunner;
import com.example.coursework.interfaces.SceneOpener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class SceneOpenerController implements SceneOpener {
    public static Stage previousStage = AppRunner.innitialStage;
    @Override
    public void openNewScene(String title, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openNewSceneAndCloseCurrent(String title, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));

            if(previousStage != null){
                previousStage.close();
            }
            previousStage = stage;
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
