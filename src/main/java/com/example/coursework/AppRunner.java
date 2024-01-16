package com.example.coursework;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class AppRunner extends Application {
    public static Stage innitialStage;
    @Override
    public void start(Stage stage) throws IOException {
        innitialStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(AppRunner.class.getResource("home-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Вікно автентифікації");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
