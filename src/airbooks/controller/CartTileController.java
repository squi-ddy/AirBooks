package airbooks.controller;

import airbooks.model.Book;
import airbooks.model.Interface;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class CartTileController {
    @FXML
    private Label bookTitleLabel;
    @FXML
    private Label rentalInfoLabel;

    private StudentController parentController;

    @FXML
    private void trashAction(MouseEvent e) {
        if (e.getButton() != MouseButton.PRIMARY) return;
        Node src = (Node)e.getSource();
        AnchorPane element = (AnchorPane) src.getParent();
        VBox layout = (VBox) src.getParent().getParent();
        var children = layout.getChildren();
        for (int i = 0; i < children.size(); i++) {
            if (element == children.get(i)) {
                children.remove(i);
                Interface.getCart().remove(i);
                parentController.updateCartInfo();
                break;
            }
        }
    }

    public void init(Book book, StudentController parentController) {
        this.parentController = parentController;
        bookTitleLabel.setText(book.getTitle());
        if (book.getIsRented()) {
            rentalInfoLabel.setText("Unavailable");
        } else {
            rentalInfoLabel.setText(String.format("Available - $%.2f, %d days", book.getDeposit(), book.getRentalPeriod()));
        }
    }
}
