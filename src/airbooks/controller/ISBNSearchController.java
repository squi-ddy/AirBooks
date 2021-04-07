package airbooks.controller;

import airbooks.model.Book;
import airbooks.model.Interface;
import airbooks.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ISBNSearchController {
    @FXML
    private Button returnBookButton;
    @FXML
    private Button doneButton;
    @FXML
    private TextField ISBNTF;
    @FXML
    private Label bookNameLabel;
    @FXML
    private Label subjectCodeLabel;
    @FXML
    private Label ISBNLabel;
    @FXML
    private Label rentalInfoLabel;
    @FXML
    private VBox bookInfoVBox;

    private Book book;

    @FXML
    private void searchAction() throws IOException {
        book = Interface.getBookByISBN(ISBNTF.getText());
        if (book == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/error.fxml"));
            Parent root = loader.load();
            loader.<ErrorController>getController().init("Invalid input!", "No book with such ISBN exists.");
            Stage err = new Stage();
            err.setScene(new Scene(root));
            err.setTitle("Error");
            err.initModality(Modality.WINDOW_MODAL);
            err.initOwner(bookInfoVBox.getScene().getWindow());
            err.showAndWait();
            return;
        }
        updateBook();
    }

    @FXML
    private void returnBookAction() throws IOException {
        boolean status = Interface.returnBook(book);
        if (!status) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/error.fxml"));
            Parent root = loader.load();
            loader.<ErrorController>getController().init("Cannot return book!", "Returning this book would exceed the $25.00 limit.");
            Stage err = new Stage();
            err.setScene(new Scene(root));
            err.setTitle("Error");
            err.initModality(Modality.WINDOW_MODAL);
            err.initOwner(bookInfoVBox.getScene().getWindow());
            err.showAndWait();
        }
        ISBNTF.getScene().getWindow().hide();
    }

    @FXML
    private void closeAction() {
        ISBNTF.getScene().getWindow().hide();
    }

    @FXML
    private void aboutAction(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/airbooks/fxml/about.fxml"));
        Stage window = new Stage();
        window.setScene(new Scene(root));
        window.setTitle("About");
        window.initModality(Modality.WINDOW_MODAL);
        window.initOwner(((Hyperlink)e.getSource()).getScene().getWindow());
        window.showAndWait();
    }

    private void updateBook() {
        bookNameLabel.setText(book.getTitle());
        subjectCodeLabel.setText(book.getSubjectCode());
        ISBNLabel.setText("ISBN " + Interface.convertISBN(book.getISBN()));
        if (book.getIsRented()) {
            Student student = Interface.getStudentById(book.getStudentID());
            rentalInfoLabel.setText(String.format("Rented - %s", student == null ? book.getStudentID() : (student.getName() + " (" + student.getStudentID() + ")")));
            rentalInfoLabel.setTextFill(Color.web("#CD5C5C"));
            returnBookButton.setDisable(false);
        } else {
            rentalInfoLabel.setText(String.format("Available - $%.2f, %d days", book.getDeposit(), book.getRentalPeriod()));
            rentalInfoLabel.setTextFill(Color.web("#3CB371"));
        }
        doneButton.setDisable(false);
        bookInfoVBox.setVisible(true);
        doneButton.setVisible(true);
        returnBookButton.setVisible(true);
    }

    public void init(Book book) {
        // Optional: initialise this window with a book already showing.
        this.book = book;
        ISBNTF.setText(book.getISBN());
        updateBook();
    }
}
