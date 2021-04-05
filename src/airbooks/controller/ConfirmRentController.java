package airbooks.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfirmRentController {
    @FXML
    private Label lockerNoLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label postalCodeLabel;

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
        lockerNoLabel.setText(lockerNo + "");
        passwordLabel.setText(password);
        postalCodeLabel.setText(postal);
    }
}
