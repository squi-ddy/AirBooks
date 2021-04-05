package airbooks.controller;

import airbooks.model.Book;
import airbooks.model.Interface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
    private void searchAction(ActionEvent event) {
        book = Interface.getBookByISBN(ISBNTF.getText());
        if (book == null) {
            var err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("Error!");
            err.setContentText("Invalid ISBN!");
            err.showAndWait();
            return;
        }
        updateBook();
    }

    @FXML
    private void returnBookAction(ActionEvent event) {
        boolean status = Interface.returnBook(book);
        if (!status) {
            var err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("Error!");
            err.setContentText("Cannot return book, as it will exceed $25.00!");
            err.showAndWait();
        }
        ISBNTF.getScene().getWindow().hide();
    }

    @FXML
    private void closeAction(ActionEvent event) {
        ISBNTF.getScene().getWindow().hide();
    }

    @FXML
    private void aboutAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/airbooks/fxml/about.fxml"));
        Stage window = new Stage();
        window.setScene(new Scene(root));
        window.setTitle("About");
        window.initModality(Modality.WINDOW_MODAL);
        window.initOwner(ISBNTF.getScene().getWindow());
        window.showAndWait();
    }

    private void updateBook() {
        bookNameLabel.setText(book.getTitle());
        subjectCodeLabel.setText(book.getSubjectCode());
        ISBNLabel.setText("ISBN " + Interface.convertISBN(book.getISBN()));
        if (book.getIsRented()) {
            rentalInfoLabel.setText("Unavailable");
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
