module AirBooks {
    opens airbooks;
    opens airbooks.controller;
    opens airbooks.model;
    opens airbooks.fxml;
    requires javafx.controls;
    requires javafx.fxml;
}