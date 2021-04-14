package airbooks.controller;

import airbooks.model.Book;
import airbooks.model.Interface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;


public class AdminController implements Initializable  {
    @FXML
    private TextField searchTF;
    @FXML
    private Label accName;
    @FXML
    private VBox bookViewVBox;
    @FXML
    private Label bookTotalLabel;
    @FXML
    private Button returnBookButton;

    private static final HashMap<Book, VBox> cartTileLongPreloaded = new HashMap<>();

    @FXML
    private void logoutAction(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) return;
        accName.getScene().getWindow().hide();
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

    @FXML
    private void returnBookAction() throws IOException {
        ArrayList<CartTileLongController> selected = CartTileLongController.getSelected();
        if (selected != null) {
            boolean status = Interface.returnBook(selected.get(0).getBook());
            if (!status) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/error.fxml"));
                Parent root = loader.load();
                loader.<ErrorController>getController().init("Cannot return book!", "Returning this book would exceed the $25.00 limit.");
                Stage err = new Stage();
                err.setScene(new Scene(root));
                err.setTitle("Error");
                err.initModality(Modality.WINDOW_MODAL);
                err.initOwner(accName.getScene().getWindow());
                err.showAndWait();
            } else {
                // because we are using the same object, we must force reload it
                selected.get(0).reload();
                reload();
            }
        }
    }

    @FXML
    private void searchAction() throws IOException {
        reload();
    }

    private void reload() throws IOException {
        reloadSearchResults();
        reloadReturnButton();
    }

    private void reloadReturnButton() {
        ArrayList<CartTileLongController> selected = CartTileLongController.getSelected();
        returnBookButton.setVisible(selected != null && selected.get(0).getBook().getIsRented());
    }

    private void reloadSearchResults() {
        ArrayList<Book> books = new ArrayList<>(Interface.getBooks());
        int originalSize = books.size();
        if (!searchTF.getText().isBlank()) {
            String searchString = searchTF.getText().toLowerCase();
            if (searchString.charAt(0) == '0') searchString = searchString.replaceAll("-", "");
            for (int i = 0; i < books.size(); i++) {
                if (!(books.get(i).getISBN().matches(searchString + ".*") || books.get(i).getTitle().toLowerCase().matches(searchString + ".*"))) {
                    books.remove(i);
                    i--;
                }
            }
        }
        bookViewVBox.getChildren().clear();
        if (CartTileLongController.getSelected() != null) CartTileLongController.getSelected().clear();
        for (Book book : books) {
            // Get and show the cached book tile
            VBox root = cartTileLongPreloaded.get(book);
            root.setStyle("-fx-border-color: black; -fx-background-color: white;");
            bookViewVBox.getChildren().add(root);
        }
        if (!searchTF.getText().isBlank()) {
            bookTotalLabel.setText(String.format("%d books in total (%d shown)", originalSize, books.size()));
        } else {
            bookTotalLabel.setText(String.format("%d books in total", originalSize));
        }
    }

    private void onClick(ArrayList<Object> inputs) {
        reloadReturnButton();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accName.setText("Administrator");
        CartTileLongController.setSelectionMode(CartTileLongController.ONLY_ONE);
        try {
            // Caching to ensure fast searching times (FXML loading is slow)
            for (Book book : Interface.getBooks()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/cart-tile-long.fxml"));
                VBox root = loader.load();
                loader.<CartTileLongController>getController().init(book, this::onClick);
                VBox.setMargin(root, new Insets(0, 0, 1, 0));
                cartTileLongPreloaded.put(book, root);
            }
            reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
