package airbooks.controller;

import airbooks.model.Book;
import airbooks.model.Interface;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    @FXML
    private Label accNameLabel;
    @FXML
    private Label walletLabel;
    @FXML
    private VBox rentalCartVBox;
    @FXML
    private Label cartInfoLabel;
    @FXML
    private ComboBox<String> subjCB;
    @FXML
    private VBox rentBooksVBox;

    private String searchedSubject;

    @FXML
    private void logoutAction(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) return;
        accNameLabel.getScene().getWindow().hide();
    }

    @FXML
    private void checkoutAction() throws IOException {
        if (Interface.getCartSum() > Interface.getCurrentAccount().getWallet()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/error.fxml"));
            Parent root = loader.load();
            loader.<ErrorController>getController().init("Not enough money!", "Deposit for books in rental cart cannot be paid.");
            Stage err = new Stage();
            err.setScene(new Scene(root));
            err.setTitle("Error");
            err.initModality(Modality.WINDOW_MODAL);
            err.initOwner(subjCB.getScene().getWindow());
            err.showAndWait();
        } else if (Interface.getCart().size() == 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/error.fxml"));
            Parent root = loader.load();
            loader.<ErrorController>getController().init("Nothing in cart!", "Rental cart is empty.");
            Stage err = new Stage();
            err.setScene(new Scene(root));
            err.setTitle("Error");
            err.initModality(Modality.WINDOW_MODAL);
            err.initOwner(subjCB.getScene().getWindow());
            err.showAndWait();
        } else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/airbooks/fxml/checkout.fxml")));
            Stage main = new Stage();
            main.setScene(new Scene(root));
            main.setTitle("Checkout");
            main.initModality(Modality.WINDOW_MODAL);
            main.initOwner(accNameLabel.getScene().getWindow());
            main.showAndWait();
            reload();
        }
    }

    @FXML
    private void findAction() throws IOException {
        rentBooksVBox.getChildren().clear();
        String subject = subjCB.getValue();
        var books = Interface.getBooksBySubjectCode(subject);
        for (Book book : books) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/cart-tile-long.fxml"));
            VBox root = loader.load();
            var controller = loader.<CartTileLongController>getController();
            controller.init(book, true);
            VBox.setMargin(root, new Insets(0, 0, 1, 0));
            rentBooksVBox.getChildren().add(root);
        }
        if (books.size() == 0) {
            var resLabel = new Label("No results!");
            VBox.setMargin(resLabel, new Insets(5, 0, 5, 0));
            rentBooksVBox.getChildren().add(resLabel);
        }
        searchedSubject = subject;
    }

    @FXML
    private void cartAction() throws IOException {
        rentBooksVBox.getChildren().clear();
        ArrayList<Book> selected = CartTileLongController.getSelected();
        if (selected == null) return;
        for (Book book : selected) {
            Interface.getCart().add(book);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/cart-tile.fxml"));
            AnchorPane root = loader.load();
            loader.<CartTileController>getController().init(book, this::onTrash);
            VBox.setMargin(root, new Insets(0, 0, 1, 0));
            rentalCartVBox.getChildren().add(root);
        }
        CartTileLongController.getSelected().clear();
        reload();
        cartInfoLabel.setText(String.format("%d books, $%.2f", Interface.getCart().size(), Interface.getCartSum()));
    }

    @FXML
    private void collectLockerAction() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/airbooks/fxml/collect-book.fxml")));
        Stage main = new Stage();
        main.setScene(new Scene(root));
        main.setTitle("Collect from Locker");
        main.initModality(Modality.WINDOW_MODAL);
        main.initOwner(accNameLabel.getScene().getWindow());
        main.showAndWait();
        reload();
    }

    @FXML
    private void aboutAction(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/airbooks/fxml/about.fxml")));
        Stage window = new Stage();
        window.setScene(new Scene(root));
        window.setTitle("About");
        window.initModality(Modality.WINDOW_MODAL);
        window.initOwner(((Hyperlink)e.getSource()).getScene().getWindow());
        window.showAndWait();
    }

    private void reload() throws IOException {
        walletLabel.setText(String.format("$%.2f", Interface.getCurrentAccount().getWallet()));
        cartInfoLabel.setText(String.format("%d books, $%.2f", Interface.getCart().size(), Interface.getCartSum()));
        rentalCartVBox.getChildren().clear();
        rentBooksVBox.getChildren().clear();
        var books = Interface.getCart();
        for (Book book : books) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/cart-tile.fxml"));
            AnchorPane root = loader.load();
            loader.<CartTileController>getController().init(book, this::onTrash);
            VBox.setMargin(root, new Insets(0, 0, 1, 0));
            rentalCartVBox.getChildren().add(root);
        }
        if (searchedSubject != null) {
            var searchedBooks = Interface.getBooksBySubjectCode(searchedSubject);
            for (Book book : searchedBooks) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/cart-tile-long.fxml"));
                VBox root = loader.load();
                var controller = loader.<CartTileLongController>getController();
                controller.init(book, true);
                VBox.setMargin(root, new Insets(0, 0, 1, 0));
                rentBooksVBox.getChildren().add(root);
            }
            if (searchedBooks.size() == 0) {
                var resLabel = new Label("No results!");
                VBox.setMargin(resLabel, new Insets(5, 0, 5, 0));
                rentBooksVBox.getChildren().add(resLabel);
            }
            subjCB.getSelectionModel().select(searchedSubject);
        }
    }

    private void onTrash(Book book) {
        try {
            reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            accNameLabel.setText(Interface.getCurrentStudent().getName());
            subjCB.setItems(FXCollections.observableArrayList(Interface.getSubjectCodes()));
            subjCB.getSelectionModel().select(0);
            searchedSubject = subjCB.getValue();
            reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
