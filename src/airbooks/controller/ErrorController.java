package airbooks.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorController {
    @FXML
    private Label headerLabel;
    @FXML
    private Label infoLabel;

    @FXML
    private void closeAction(ActionEvent e) {
        headerLabel.getScene().getWindow().hide();
    }

    @FXML
    private void aboutAction(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/airbooks/fxml/about.fxml"));
        Stage window = new Stage();
        window.setScene(new Scene(root));
        window.setTitle("About");
        window.initModality(Modality.WINDOW_MODAL);
        window.initOwner(headerLabel.getScene().getWindow());
        window.showAndWait();
    }

    public void init(String headerText, String info) {
        headerLabel.setText(headerText);
        infoLabel.setText(info);
    }
}
