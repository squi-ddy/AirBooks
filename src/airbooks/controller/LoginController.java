package airbooks.controller;

import airbooks.model.Interface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private PasswordField passwordTF;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/error.fxml"));
            Parent root = loader.load();
            loader.<ErrorController>getController().init("Invalid input!", "Username or password is incorrect.");
            Stage err = new Stage();
            err.setScene(new Scene(root));
            err.setTitle("Error");
            err.initModality(Modality.WINDOW_MODAL);
            err.initOwner(loginStage);
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

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater( () -> usernameTF.getParent().requestFocus() );
    }
}