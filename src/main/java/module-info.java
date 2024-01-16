module com.example.coursework {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.coursework to javafx.fxml;
    opens com.example.coursework.controllers;
    opens com.example.coursework.dto to javafx.base;
    exports com.example.coursework;
    opens com.example.coursework.controllers.agents;
    opens com.example.coursework.controllers.programs;
    opens com.example.coursework.controllers.orders;
    opens com.example.coursework.controllers.abstracts;
}