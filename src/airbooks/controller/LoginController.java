package airbooks.controller;

import airbooks.model.Interface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField passwordTF;
    @FXML
    private TextField usernameTF;

    private Stage loginStage;

    @FXML
    private void aboutAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/airbooks/fxml/about.fxml"));
        Stage window = new Stage();
        window.setScene(new Scene(root));
        window.setTitle("About");
        window.initModality(Modality.WINDOW_MODAL);
        window.initOwner(passwordTF.getScene().getWindow());
        window.showAndWait();
    }

    @FXML
    private void loginAction(ActionEvent e) throws IOException {
        int status = Interface.login(usernameTF.getText().strip(), passwordTF.getText().strip());
        if (status == -1) {
            var err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("Error!");
            err.setContentText("Invalid username/password!");
            err.showAndWait();
        }
        else if (status == 0) {
            Parent root = FXMLLoader.load(getClass().getResource("/airbooks/fxml/student.fxml"));
            Stage main = new Stage();
            main.setScene(new Scene(root));
            main.setTitle("Student View");
            loginStage.hide();
            main.showAndWait();
            Interface.logout();
            loginStage.show();
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("/airbooks/fxml/admin.fxml"));
            Stage main = new Stage();
            main.setScene(new Scene(root));
            main.setTitle("Admin View");
            loginStage.hide();
            main.showAndWait();
            loginStage.show();
        }
    }

    public void init(Stage stage) {
        loginStage = stage;
    }
}