package airbooks.controller;

import airbooks.model.Book;
import airbooks.model.Interface;
import airbooks.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
    private Consumer<Book> onDoubleClick;

    @FXML
    private void selectAction(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) return;
        if ((event.getClickCount() == 1 && selectable) || (event.getClickCount() == 2 && onDoubleClick == null)) {
            VBox src = (VBox) event.getSource();
            if (!selected.contains(book)) {
                src.setStyle("-fx-border-color: black; -fx-background-color: lightcyan;");
                selected.add(book);
            } else {
                src.setStyle("-fx-border-color: black; -fx-background-color: white;");
                selected.remove(book);
            }
        } else if (event.getClickCount() == 2) {
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
            Student student = Interface.getStudentById(book.getStudentID());
            rentalInfoLabel.setText(String.format("Rented - %s", student == null ? book.getStudentID() : (student.getName() + " (" + student.getStudentID() + ")")));
            rentalInfoLabel.setTextFill(Color.web("#CD5C5C"));
        } else {
            rentalInfoLabel.setText(String.format("Available - $%.2f, %d days", book.getDeposit(), book.getRentalPeriod()));
            rentalInfoLabel.setTextFill(Color.web("#3CB371"));
        }
        selected = new ArrayList<>();
    }

    public static ArrayList<Book> getSelected() {
        return selected;
    }

    public void init(Book book, boolean selectable, Consumer<Book> onDoubleClick) {
        this.onDoubleClick = onDoubleClick;
        init(book, selectable);
    }
}
