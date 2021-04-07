package airbooks.controller;

import airbooks.model.Book;
import airbooks.model.Interface;
import airbooks.model.Locker;
import airbooks.model.SelfCollectStn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CheckoutController {
    @FXML
    private TextField postalTF;
    @FXML
    private VBox SCSListVBox;
    @FXML
    private Button selectButton;

    @FXML
    private void findSCSAction(ActionEvent e) throws IOException {
        var possible = Interface.getNearbySCS(postalTF.getText());
        SCSListVBox.getChildren().clear();
        if (possible == null) {
            var resLabel = new Label("No results!");
            VBox.setMargin(resLabel, new Insets(5, 0, 5, 0));
            SCSListVBox.getChildren().add(resLabel);
        } else {
            for (SelfCollectStn scs : possible) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/scs-tile.fxml"));
                VBox root = loader.load();
                var controller = loader.<SCSTileController>getController();
                controller.init(scs);
                VBox.setMargin(root, new Insets(0, 0, 1, 0));
                SCSListVBox.getChildren().add(root);
            }
        }
        selectButton.setDisable(false);
    }

    @FXML
    private void chooseSCSAction(ActionEvent e) throws IOException {
        SelfCollectStn scs = SCSTileController.getSelected();
        if (scs == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/error.fxml"));
            Parent root = loader.load();
            loader.<ErrorController>getController().init("Invalid input!", "Please select a station.");
            Stage err = new Stage();
            err.setScene(new Scene(root));
            err.setTitle("Error");
            err.initModality(Modality.WINDOW_MODAL);
            err.initOwner(selectButton.getScene().getWindow());
            err.showAndWait();
            return;
        }
        int lockerNum = scs.getEmptyLockerNum();
        Locker locker = scs.getLocker(lockerNum);
        String password = locker.placeItem(Interface.getCurrentStudent().getStudentID(), (ArrayList<Book>) Interface.getCart().clone());
        Interface.checkout(scs.getPostalCode(), lockerNum);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/confirm-rent.fxml"));
        Parent root = loader.load();
        loader.<ConfirmRentController>getController().init(lockerNum, password, scs.getPostalCode());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Confirmation");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(SCSListVBox.getScene().getWindow());
        stage.showAndWait();
        SCSListVBox.getScene().getWindow().hide();
    }

    @FXML
    private void aboutAction(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/airbooks/fxml/about.fxml"));
        Stage window = new Stage();
        window.setScene(new Scene(root));
        window.setTitle("About");
        window.initModality(Modality.WINDOW_MODAL);
        window.initOwner(postalTF.getScene().getWindow());
        window.showAndWait();
    }
}