package airbooks.controller;

import airbooks.model.Book;
import airbooks.model.Interface;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.function.Consumer;

public class CartTileLongController {
    @FXML
    private Label bookNameLabel;
    @FXML
    private Label subjectCodeLabel;
    @FXML
    private Label ISBNLabel;
    @FXML
    private Label rentalInfoLabel;

    private boolean selectable;
    private Book book;
    private static ArrayList<Book> selected;
    private static Consumer<Book> onDoubleClick;

    @FXML
    private void selectAction(MouseEvent event) throws InvocationTargetException, IllegalAccessException {
        if (event.getButton() != MouseButton.PRIMARY) return;
        if (event.getClickCount() == 1 && selectable) {
            VBox src = (VBox) event.getSource();
            if (!selected.contains(book)) {
                src.setStyle("-fx-border-color: black; -fx-background-color: lightcyan;");
                selected.add(book);
            } else {
                src.setStyle("-fx-border-color: black; -fx-background-color: white;");
                selected.remove(book);
            }
        } else if (event.getClickCount() == 2 && onDoubleClick != null) {
            onDoubleClick.accept(book);
        }
    }


    public void init(Book book, boolean selectable) {
        this.book = book;
        this.selectable = selectable;
        bookNameLabel.setText(book.getTitle());
        subjectCodeLabel.setText(book.getSubjectCode());
        ISBNLabel.setText("ISBN " + Interface.convertISBN(book.getISBN()));
        if (book.getIsRented()) {
            rentalInfoLabel.setText("Unavailable");
            rentalInfoLabel.setTextFill(Color.web("#CD5C5C"));
        } else {
            rentalInfoLabel.setText(String.format("Available - $%.2f, %d days", book.getDeposit(), book.getRentalPeriod()));
            rentalInfoLabel.setTextFill(Color.web("#3CB371"));
        }
        selected = new ArrayList<Book>();
    }

    public static void setOnDoubleClick(Consumer<Book> onDoubleClick) {
        CartTileLongController.onDoubleClick = onDoubleClick;
    }

    public static ArrayList<Book> getSelected() {
        return selected;
    }
}
