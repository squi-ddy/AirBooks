package airbooks.controller;

import airbooks.model.Interface;
import airbooks.model.SelfCollectStn;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class SCSTileController {
    @FXML
    private Label scsNameLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private VBox SCSVBox;

    private Consumer<SelfCollectStn> onSelect;

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
        if (onSelect != null) onSelect.accept((SelfCollectStn)SCSVBox.getUserData());
    }

    public void init(SelfCollectStn scs, Consumer<SelfCollectStn> onSelect) {
        selected = null;
        SCSVBox.setUserData(scs);
        scsNameLabel.setText(Interface.getAreaDetails(scs));
        postalCodeLabel.setText(scs.getPostalCode());
        this.onSelect = onSelect;
    }

    public void init(SelfCollectStn scs) {
        init(scs, null);
    }

    public static void reset() {
        selected = null;
    }

    public static SelfCollectStn getSelected() {
        return selected;
    }
}
