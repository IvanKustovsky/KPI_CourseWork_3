package com.example.coursework.controllers.abstracts;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.util.regex.Pattern;

public abstract class InputValidationController {

        public static void phoneNumberValidation(TextField phoneNumberField) {
            phoneNumberField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
                if (phoneNumberField.getCaretPosition() < 4) {
                    event.consume(); // Відмовити у введенні символу перед початковим текстом
                } else if (!event.getCharacter().matches("[0-9]")) {
                    event.consume(); // Відмовити у введенні символу, якщо це не цифра
                } else if (phoneNumberField.getText().length() >= 13) {
                    event.consume(); // Відмовити у введенні більше ніж 9 цифр
                }
            });

            // Додати обробник подій для керування при натисканні миші
            phoneNumberField.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                // Перевірити, чи курсор знаходиться перед початковим текстом
                if (phoneNumberField.getCaretPosition() < 4) {
                    phoneNumberField.positionCaret(phoneNumberField.getText().length()); // Перемістити курсор в кінець
                    event.consume(); // Відмовити у події, щоб користувач не міг редагувати в середині тексту
                }
            });

            phoneNumberField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
                    // Відмовити у видаленні, якщо курсор знаходиться перед початковим текстом
                    if (phoneNumberField.getCaretPosition() <= 4) {
                        event.consume();
                    }
                }
            });
        }

        public static void companyDetailsValidation(TextField companyDetailsField) {
            companyDetailsField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
                if (!event.getCharacter().matches("[0-9]")) {
                    event.consume(); // Відмовити у введенні символу, якщо це не цифра
                }
            });

            // Додати обробник подій для перевірки кількості символів
            companyDetailsField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() > 8) {
                    companyDetailsField.setText(oldValue); // Встановити попереднє значення, якщо перевищено 8 символів
                }
            });
        }

        public static boolean isValidEmail(String email) {
            // Регулярний вираз для перевірки коректності email адреси
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);

            // Перевірити, чи введений текст відповідає регулярному виразу
            return pattern.matcher(email).matches();
        }
    }

