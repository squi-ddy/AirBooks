package airbooks.controller;

import airbooks.model.SelfCollectStn;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;

public class SCSTileController {
    @FXML
    private Label scsNameLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private VBox SCSVBox;

    private static SelfCollectStn selected;

    @FXML
    private void selectAction(MouseEvent e) {
        if (selected == SCSVBox.getUserData() || e.getButton() != MouseButton.PRIMARY) return;
        if (selected != null) {
            VBox list = (VBox) SCSVBox.getParent();
            for (Node child : list.getChildren()) {
                if (child.getUserData() == selected) {
                    child.setStyle("-fx-border-color: black; -fx-background-color: white; ");
                    break;
                }
            }
        }
        SCSVBox.setStyle("-fx-border-color: black; -fx-background-color: lightcyan; ");
        selected = (SelfCollectStn) SCSVBox.getUserData();
    }

    public void init(SelfCollectStn scs) {
        selected = null;
        SCSVBox.setUserData(scs);
        scsNameLabel.setText(scs.getAreaDetails());
        postalCodeLabel.setText(scs.getPostalCode());
    }

    public static SelfCollectStn getSelected() {
        return selected;
    }
}
