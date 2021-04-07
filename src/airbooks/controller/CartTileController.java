package airbooks.controller;

import airbooks.model.Book;
import airbooks.model.Interface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class CartTileController {
    @FXML
    private Label bookTitleLabel;
    @FXML
    private Label rentalInfoLabel;
    @FXML
    private AnchorPane cartTileAP;

    private Consumer<Book> onTrash;
    private Book book;

    @FXML
    private void trashAction(MouseEvent e) {
        if (e.getButton() != MouseButton.PRIMARY) return;
        ((VBox)cartTileAP.getParent()).getChildren().remove(cartTileAP);
        Interface.getCart().remove(book);
        onTrash.accept(book);
    }

    public void init(Book book, Consumer<Book> onTrash) {
        this.onTrash = onTrash;
        this.book = book;
        bookTitleLabel.setText(book.getTitle());
        if (book.getIsRented()) {
            rentalInfoLabel.setText("Unavailable");
        } else {
            rentalInfoLabel.setText(String.format("Available - $%.2f, %d days", book.getDeposit(), book.getRentalPeriod()));
        }
    }
}
