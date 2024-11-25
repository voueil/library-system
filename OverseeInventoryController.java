package controllers;

import DatabaseConnection.DatabaseConnection;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Book;

public class OverseeInventoryController implements Initializable {

    DatabaseConnection handler;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private VBox booksContainer;
    @FXML
    private Button btnBack;
    @FXML
    private ImageView logoImgView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseConnection.getInstance();
        Image logoImg = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        logoImgView.setImage(logoImg);
        displayAllBooks();
    }

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();

        root = FXMLLoader.load(getClass().getResource("/views/AdminHome.fxml"));

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void displayAllBooks() {
        List<Book> books = handler.displayAllBooks();
        booksContainer.getChildren().clear();

        if (books.isEmpty()) {
            Label noBooksLabel = new Label("No Available Books");
            booksContainer.getChildren().add(noBooksLabel);

        } else {
            for (Book book : books) {
                HBox bookBox = new HBox(60);
                bookBox.setPadding(new Insets(5));
                bookBox.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

                Label bookTitleLabel = new Label(" " + book.getTitle());
                Label bookAuthorLabel = new Label(" " + book.getAuthor());
                Label bookIsbnLabel = new Label(" " + book.getISBN());
                Label bookAvailabilityLabel = new Label(" " + book.isAvailable());

                bookBox.getChildren().addAll(bookTitleLabel, bookAuthorLabel, bookIsbnLabel, bookAvailabilityLabel);

                booksContainer.getChildren().add(bookBox);
            }
        }

    }

}
