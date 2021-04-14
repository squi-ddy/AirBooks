package airbooks.controller;

import airbooks.model.Book;
import airbooks.model.Interface;
import airbooks.model.Student;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    @FXML
    private VBox parentVBox;

    private Book book;
    private static ArrayList<CartTileLongController> selected;
    private static boolean selectMode;
    private Consumer<ArrayList<Object>> onClick;

    public static final boolean ONLY_ONE = true;
    public static final boolean MULTIPLE = false;

    @FXML
    private void selectAction(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) return;
        boolean isSelected = !selected.contains(this);
        if (isSelected) {
            if (selectMode == ONLY_ONE && selected.size() != 0) {
                CartTileLongController selectedTile = selected.get(0);
                selectedTile.forceDeselect();
            }
            parentVBox.setStyle("-fx-border-color: black; -fx-background-color: lightcyan;");
            selected.add(this);
        } else {
            parentVBox.setStyle("-fx-border-color: black; -fx-background-color: white;");
            selected.remove(this);
        }
        if (onClick != null) {
            var args = new ArrayList<>();
            args.add(book);
            args.add(isSelected);
            Node[] nodes = {parentVBox, bookNameLabel, subjectCodeLabel, ISBNLabel, rentalInfoLabel};
            args.add(nodes);
            onClick.accept(args);
        }
    }

    public static ArrayList<CartTileLongController> getSelected() {
        return selected.size() == 0 ? null : selected;
    }

    public Book getBook() {
        return book;
    }

    public static void setSelectionMode(boolean selectMode) {
        CartTileLongController.selectMode = selectMode;
    }

    /*
    init(): Initialises this tile.
    Book: Book object this tile represents.
    onlyOne: enables only one to be selected (default: false)
    onClick: Function that is called when this object is clicked.
    Should have an ArrayList<Object> as input.
    Passes internal book object, boolean isSelected, and all nodes to Consumer.
    By default, this function turns this tile blue when selected and white when not.
    */
    public void init(Book book, Consumer<ArrayList<Object>> onClick) {
        this.onClick = onClick;
        this.book = book;
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

    public void init(Book book) {
        init(book, null);
    }

    public void reload() {
        // Reloads book information (mostly rented info)
        if (book.getIsRented()) {
            Student student = Interface.getStudentById(book.getStudentID());
            rentalInfoLabel.setText(String.format("Rented - %s", student == null ? book.getStudentID() : (student.getName() + " (" + student.getStudentID() + ")")));
            rentalInfoLabel.setTextFill(Color.web("#CD5C5C"));
        } else {
            rentalInfoLabel.setText(String.format("Available - $%.2f, %d days", book.getDeposit(), book.getRentalPeriod()));
            rentalInfoLabel.setTextFill(Color.web("#3CB371"));
        }
    }

    private void forceDeselect() {
        selected.remove(this);
        parentVBox.setStyle("-fx-border-color: black; -fx-background-color: white;");
    }
}
