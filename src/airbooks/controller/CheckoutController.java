package airbooks.controller;

import airbooks.model.Interface;
import airbooks.model.Locker;
import airbooks.model.SelfCollectStn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CheckoutController {
    @FXML
    private TextField postalTF;
    @FXML
    private VBox SCSListVBox;
    @FXML
    private Button selectButton;

    @FXML
    private void findSCSAction() throws IOException {
        var possible = Interface.getNearbySCS(postalTF.getText());
        SCSListVBox.getChildren().clear();
        if (possible == null) {
            var resLabel = new Label("No results!");
            VBox.setMargin(resLabel, new Insets(5, 0, 5, 0));
            SCSListVBox.getChildren().add(resLabel);
        } else {
            for (SelfCollectStn scs : possible) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/view/scs-tile.fxml"));
                VBox root = loader.load();
                var controller = loader.<SCSTileController>getController();
                controller.init(scs, this::onSelect);
                VBox.setMargin(root, new Insets(0, 0, 1, 0));
                SCSListVBox.getChildren().add(root);
            }
        }
        selectButton.setVisible(false);
        SCSTileController.reset();
    }

    @FXML
    private void chooseSCSAction() throws IOException {
        SelfCollectStn scs = SCSTileController.getSelected();
        int lockerNum = scs.getEmptyLockerNum();
        Locker locker = scs.getLocker(lockerNum);
        String password = locker.placeItem(Interface.getCurrentStudent().getStudentID(), new ArrayList<>(Interface.getCart()));
        Interface.checkout(scs.getPostalCode(), lockerNum);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/view/confirm-rent.fxml"));
        Parent root = loader.load();
        loader.<ConfirmRentController>getController().init(lockerNum, password, scs.getPostalCode());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        root.requestFocus();
        stage.setTitle("Confirmation");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(SCSListVBox.getScene().getWindow());
        stage.showAndWait();
        SCSTileController.reset();
        SCSListVBox.getScene().getWindow().hide();
    }

    @FXML
    private void aboutAction(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/airbooks/view/about.fxml")));
        Stage window = new Stage();
        window.setScene(new Scene(root));
        window.setTitle("About");
        window.initModality(Modality.WINDOW_MODAL);
        window.initOwner(((Hyperlink)e.getSource()).getScene().getWindow());
        window.showAndWait();
    }

    private void onSelect(SelfCollectStn scs) {
        selectButton.setVisible(true);
    }
}