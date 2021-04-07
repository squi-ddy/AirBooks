package airbooks.controller;

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

public class ConfirmRentController implements Initializable {
    @FXML
    private TextField lockerNoLabel;
    @FXML
    private TextField passwordLabel;
    @FXML
    private TextField postalCodeLabel;

    @FXML
    private void aboutAction(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/airbooks/fxml/about.fxml"));
        Stage window = new Stage();
        window.setScene(new Scene(root));
        window.setTitle("About");
        window.initModality(Modality.WINDOW_MODAL);
        window.initOwner(lockerNoLabel.getScene().getWindow());
        window.showAndWait();
    }

    public void init(int lockerNo, String password, String postal) {
        lockerNoLabel.setText(String.valueOf(lockerNo));
        passwordLabel.setText(password);
        postalCodeLabel.setText(postal);
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater( () -> lockerNoLabel.getParent().requestFocus() );
    }
}
