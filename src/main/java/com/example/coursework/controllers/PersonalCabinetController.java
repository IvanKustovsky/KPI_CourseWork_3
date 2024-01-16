package com.example.coursework.controllers;

import com.example.coursework.controllers.abstracts.BaseController;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class PersonalCabinetController extends BaseController implements Initializable{

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
    @FXML
    private Button orderAdBtn;
    @FXML
    private Label orderAdLabel;
    private final ContactPersonDao contactPersonDao = new ContactPersonDaoImpl();
    private final CustomerDao customerDao = new CustomerDaoImpl();

    @FXML
    void editCustomerData() {
        if(isDataValid()){
            editUserData();
            showSuccessfulMessage();
        }
    }

    @FXML
    void openHomePageWindow() {
        openNewSceneAndCloseCurrent("Вікно автентифікації","/com/example/coursework/home-page.fxml");
    }

    @FXML
    void openOrderAdWindowBtn() {
        openNewScene("Замовлення реклами","/com/example/coursework/orderAdvertisement.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTextField(HomePageController.authenticatedCustomer); // Заповнюємо поля користувача
        displayFunctional(); //Приховує або показує відповідні кнопки в залежності від статусу користувача (адмін,замовник)
        if (HomePageController.authenticatedCustomer.isAdmin()) {
            orderAdBtn.setVisible(false);
            orderAdLabel.setVisible(false);
        }
        InputValidationController.phoneNumberValidation(phoneNumberField);
        InputValidationController.companyDetailsValidation(bankDetailsField);
    }

    private void editUserData() {
        ContactPerson contactPerson = contactPersonDao.findById(HomePageController.
                authenticatedCustomer.getContactPersonId());
        contactPerson.setName(nameField.getText());
        contactPerson.setSurname(surnameField.getText());
        contactPersonDao.update(contactPerson);
        Customer customer = HomePageController.authenticatedCustomer;
        customer.setBankDetails(bankDetailsField.getText());
        customer.setPhone(phoneNumberField.getText());
        customer.setPassword(!Objects.equals(customer.getPassword(), passwordField.getText()) ?
                Encryption.encrypt(passwordField.getText()) : passwordField.getText());
        customer.setEmail(emailField.getText());
        customerDao.update(customer);
        setTextField(customer);
    }

    private void showSuccessfulMessage() {
        successLabel.setVisible(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> successLabel.setVisible(false)));
        timeline.setCycleCount(1);
        timeline.play();
    }
    public boolean isDataValid() {
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

    public boolean isUniqueCustomerData() {
        List<Customer> customers = customerDao.findAll();
        Customer currentCustomer = customerDao.findById(HomePageController.authenticatedCustomer.getCustomerId());
        String newBankDetails = bankDetailsField.getText();
        String newEmail = emailField.getText();
        String newPassword = Encryption.encrypt(passwordField.getText());

        boolean isPasswordUnique = customers.stream()
                .filter(customer -> !customer.equals(currentCustomer)) // Exclude the currentCustomer
                .noneMatch(customer -> customer.getPassword().equals(newPassword));

        boolean isBankDetailsUnique = customers.stream()
                .filter(customer -> !customer.equals(currentCustomer)) // Exclude the currentCustomer
                .noneMatch(customer -> customer.getBankDetails().equals(newBankDetails));

        boolean isEmailUnique = customers.stream()
                .filter(customer -> !customer.equals(currentCustomer)) // Exclude the currentCustomer
                .noneMatch(customer -> customer.getEmail().equals(newEmail));

        bankDetailsErrorLabel.setText("ЄДРПОУ вже використовується.");
        phoneErrorLabel.setText("Номер вже використовується.");
        emailErrorLabel.setText("Електронна адреса вже використовується.");
        passwordErrorLabel.setText("Пароль вже використовується.");

        passwordErrorLabel.setVisible(!isPasswordUnique);
        bankDetailsErrorLabel.setVisible(!isBankDetailsUnique);
        emailErrorLabel.setVisible(!isEmailUnique);

        return (isPasswordUnique && isBankDetailsUnique && isEmailUnique);
    }

    private void resetErrors() {
        bankDetailsErrorLabel.setText("Код ЄДРПОУ має містити 8 цифр.");
        phoneErrorLabel.setText("Введіть коректний номер телефону.");
        emailErrorLabel.setText("Електронна адреса не є валідною.");
        passwordErrorLabel.setText("Пароль не може бути порожнім.");
        nameErrorLabel.setVisible(false);
        surnameErrorLabel.setVisible(false);
        bankDetailsErrorLabel.setVisible(false);
        emailErrorLabel.setVisible(false);
        phoneErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);
    }

    private void setTextField(Customer customer) {
        bankDetailsField.setText(customer.getBankDetails());
        emailField.setText(customer.getEmail());
        nameField.setText(contactPersonDao.findById(customer.getCustomerId()).getName());
        surnameField.setText(contactPersonDao.findById(customer.getCustomerId()).getSurname());
        phoneNumberField.setText(customer.getPhone());
        passwordField.setText(customer.getPassword());
    }

}
