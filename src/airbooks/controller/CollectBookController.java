package airbooks.controller;

import airbooks.model.Interface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CollectBookController {
    @FXML
    private TextField postalTF;
    @FXML
    private TextField lockerNoTF;
    @FXML
    private PasswordField passwordTF;

    @FXML
    private void collectAction() throws IOException {
        int status = Interface.collectFromLocker(postalTF.getText(), lockerNoTF.getText(), passwordTF.getText());
        if (status == 0) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/airbooks/fxml/confirm-locker.fxml")));
            Stage window = new Stage();
            window.setScene(new Scene(root));
            window.setTitle("Confirmation");
            window.initModality(Modality.WINDOW_MODAL);
            window.initOwner(postalTF.getScene().getWindow());
            window.showAndWait();
            postalTF.getScene().getWindow().hide();
        } else if (status == 1) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/error.fxml"));
            Parent root = loader.load();
            loader.<ErrorController>getController().init("Invalid input!", "Postal code, locker number or password is incorrect.");
            Stage err = new Stage();
            err.setScene(new Scene(root));
            err.setTitle("Error");
            err.initModality(Modality.WINDOW_MODAL);
            err.initOwner(postalTF.getScene().getWindow());
            err.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/error.fxml"));
            Parent root = loader.load();
            loader.<ErrorController>getController().init("Cannot unlock!", "This locker is reserved for someone else.");
            Stage err = new Stage();
            err.setScene(new Scene(root));
            err.setTitle("Error");
            err.initModality(Modality.WINDOW_MODAL);
            err.initOwner(postalTF.getScene().getWindow());
            err.showAndWait();
        }
    }

    @FXML
    private void aboutAction(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/airbooks/fxml/about.fxml")));
        Stage window = new Stage();
        window.setScene(new Scene(root));
        window.setTitle("About");
        window.initModality(Modality.WINDOW_MODAL);
        window.initOwner(((Hyperlink)e.getSource()).getScene().getWindow());
        window.showAndWait();
    }
}
