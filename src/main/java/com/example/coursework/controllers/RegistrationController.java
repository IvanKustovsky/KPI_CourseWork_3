package com.example.coursework.controllers;

import com.example.coursework.controllers.abstracts.InputValidationController;
import com.example.coursework.dao.contactPerson.ContactPersonDao;
import com.example.coursework.dao.contactPerson.ContactPersonDaoImpl;
import com.example.coursework.dao.customer.CustomerDao;
import com.example.coursework.dao.customer.CustomerDaoImpl;
import com.example.coursework.dto.ContactPerson;
import com.example.coursework.dto.Customer;
import com.example.coursework.services.Encryption;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField bankDetailsField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label nameErrorLabel;
    @FXML
    private Label surnameErrorLabel;
    @FXML
    private Label bankDetailsErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label phoneErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label successLabel;
    private final CustomerDao customerDao = new CustomerDaoImpl();
    private final ContactPersonDao contactPersonDao = new ContactPersonDaoImpl();

    @FXML
    void signUpNewCustomer() {
        if(isDataValid()){
            signUpNewUser();
            showSuccessfulMessageAndCloseStage();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputValidationController.phoneNumberValidation(phoneNumberField);
        InputValidationController.companyDetailsValidation(bankDetailsField);
    }

    private void signUpNewUser() {
        ContactPerson contactPerson = new ContactPerson(nameField.getText(), surnameField.getText());
        contactPersonDao.save(contactPerson);
        Customer customer = new Customer(contactPerson.getId(),bankDetailsField.getText(), phoneNumberField.getText(),
                emailField.getText(), Encryption.encrypt(passwordField.getText()), false);
        customerDao.save(customer);
    }

    private void showSuccessfulMessageAndCloseStage() {
        successLabel.setVisible(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2),
                e -> ((Stage) successLabel.getScene().getWindow()).close()));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private boolean isDataValid() {
        // Add your validation conditions here
        if(!isUniqueCustomerData()){
            return false;
        }
        resetErrors();
        boolean isNameValid = !nameField.getText().isEmpty();
        boolean isSurnameValid = !surnameField.getText().isEmpty();
        boolean isCompanyDetailsValid = bankDetailsField.getText().length() == 8;
        boolean isEmailValid = InputValidationController.isValidEmail(emailField.getText());
        boolean isPhoneNumberValid = phoneNumberField.getText().length() == 13;
        boolean isPasswordValid = !passwordField.getText().isEmpty();

        // Display error messages if validation fails
        nameErrorLabel.setVisible(!isNameValid);
        surnameErrorLabel.setVisible(!isSurnameValid);
        bankDetailsErrorLabel.setVisible(!isCompanyDetailsValid);
        emailErrorLabel.setVisible(!isEmailValid);
        phoneErrorLabel.setVisible(!isPhoneNumberValid);
        passwordErrorLabel.setVisible(!isPasswordValid);

        return (isNameValid && isSurnameValid && isCompanyDetailsValid
                && isEmailValid && isPhoneNumberValid && isPasswordValid);
    }

    private boolean isUniqueCustomerData() {
        List<Customer> customers = customerDao.findAll();
        String newBankDetails = bankDetailsField.getText();
        String newEmail = emailField.getText();
        String newPassword = Encryption.encrypt(passwordField.getText());

        boolean isPasswordUnique = customers.stream()
                .noneMatch(customer -> customer.getPassword().equals(newPassword));
        boolean isBankDetailsUnique = customers.stream()
                .noneMatch(customer -> customer.getBankDetails().equals(newBankDetails));
        boolean isEmailUnique = customers.stream()
                .noneMatch(customer -> customer.getEmail().equals(newEmail));

        bankDetailsErrorLabel.setText("ЄДРПОУ вже використовується.");
        emailErrorLabel.setText("Електронна адреса вже використовується.");
        passwordErrorLabel.setText("Пароль вже використовується.");

        passwordErrorLabel.setVisible(!isPasswordUnique);
        bankDetailsErrorLabel.setVisible(!isBankDetailsUnique);
        emailErrorLabel.setVisible(!isEmailUnique);

        return (isPasswordUnique && isBankDetailsUnique && isEmailUnique);
    }

    private void resetErrors() {
        bankDetailsErrorLabel.setText("Код ЄДРПОУ має містити 8 цифр.");
        emailErrorLabel.setText("Електронна адреса не є валідною.");
        passwordErrorLabel.setText("Пароль не може бути порожнім.");
        nameErrorLabel.setVisible(false);
        surnameErrorLabel.setVisible(false);
        bankDetailsErrorLabel.setVisible(false);
        emailErrorLabel.setVisible(false);
        phoneErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);
    }

}
