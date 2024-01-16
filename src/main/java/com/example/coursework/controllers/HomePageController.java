package com.example.coursework.controllers;

import com.example.coursework.controllers.abstracts.SceneOpenerController;
import com.example.coursework.dao.customer.CustomerDao;
import com.example.coursework.dao.customer.CustomerDaoImpl;
import com.example.coursework.dto.Customer;
import com.example.coursework.services.Encryption;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.List;

public class HomePageController extends SceneOpenerController {
    @FXML
    private Label errorLabel;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    public static Customer authenticatedCustomer = null;
    private final CustomerDao customerDao = new CustomerDaoImpl();
    @FXML
    void openPersonalCabinetWindow() {
        String loginInput = loginField.getText();
        String passwordInput = passwordField.getText();
        // Перевірка чи є введений login та пароль в базі даних
        authenticatedCustomer = authenticateCustomer(loginInput, passwordInput);

        if (authenticatedCustomer != null) {
            showSuccessfulMessage();
        } else {
            errorLabel.setVisible(true);
            errorLabel.setText("Користувача з таким логіном та паролем не існує.");
        }
    }
    @FXML
    void openRegistrationWindow() {
        openNewScene("Вікно реєстрації",
                "/com/example/coursework/registration.fxml");
    }

    private void showSuccessfulMessage(){
        errorLabel.setText("Автентифікація успішна.");
        errorLabel.setStyle("-fx-text-fill: green;");
        errorLabel.setVisible(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                e -> openNewSceneAndCloseCurrent("Особистий кабінет",
                        "/com/example/coursework/personalCabinet.fxml")));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private Customer authenticateCustomer(String loginInput, String passwordInput) {
        List<Customer> customers = customerDao.findAll();
        // Loop through the list of customers to find a match for the loginInput
        for (Customer customer : customers) {
            if (customer.getEmail().equals(loginInput)
                    || customer.getBankDetails().equals(loginInput)) {
                // Check if the password matches
                if (Encryption.verify(passwordInput,customer.getPassword())) {
                    return customer; // Authentication successful
                }
            }
        }
        return null; // Authentication failed
    }

}
