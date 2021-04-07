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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AdminController implements Initializable  {
    @FXML
    private Label accName;
    @FXML
    private VBox bookViewVBox;
    @FXML
    private Label bookTotalLabel;

    @FXML
    private void logoutAction(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) return;
        accName.getScene().getWindow().hide();
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

    @FXML
    private void ISBNSearchAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/airbooks/fxml/isbn.fxml"));
        Stage window = new Stage();
        window.setScene(new Scene(root));
        root.requestFocus();
        window.setTitle("ISBN Search");
        window.initModality(Modality.WINDOW_MODAL);
        window.initOwner(accName.getScene().getWindow());
        window.showAndWait();
        reload();
    }

    private void reload() throws IOException {
        ArrayList<Book> books = Interface.getBooks();
        bookViewVBox.getChildren().clear();
        for (Book book : books) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/cart-tile-long.fxml"));
            VBox root = loader.load();
            loader.<CartTileLongController>getController().init(book, false, this::onDoubleClick);
            VBox.setMargin(root, new Insets(0, 0, 1, 0));
            bookViewVBox.getChildren().add(root);
        }
        bookTotalLabel.setText(String.format("%d books in total", books.size()));
    }

    private void onDoubleClick(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/airbooks/fxml/isbn.fxml"));
            Parent root = loader.load();
            loader.<ISBNSearchController>getController().init(book);
            Stage window = new Stage();
            window.setScene(new Scene(root));
            root.requestFocus();
            window.setTitle("ISBN Search");
            window.initModality(Modality.WINDOW_MODAL);
            window.initOwner(accName.getScene().getWindow());
            window.showAndWait();
            reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accName.setText("Administrator");
        try {
            reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
