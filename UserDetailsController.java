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

public class UserDetailsController implements Initializable {

    DatabaseConnection handler;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String userId;
    private String username;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label userIdLabel;
    @FXML
    private VBox booksContainer;
    @FXML
    private Button backBtn;
    @FXML
    private ImageView logoImgView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseConnection.getInstance();
        Image logoImg = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        logoImgView.setImage(logoImg);
    }

    public void setUserData(String userId, String username) {
        this.userId = userId;
        this.username = username;
        displayUserBooks();
        usernameLabel.setText(username);
        userIdLabel.setText(userId);
    }

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/StudentHome.fxml"));
        root = loader.load();
        StudentHomeController controller = loader.getController();
        controller.setUserData(userId, username);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void displayUserBooks() {
        List<Book> books = handler.displayBooksByUserId(userId);
        booksContainer.getChildren().clear();

        if (books.isEmpty()) {
            Label noBooksLabel = new Label("No Borrowed Books");
            booksContainer.getChildren().add(noBooksLabel);

        } else {
            for (Book book : books) {
                HBox bookBox = new HBox(100);
                bookBox.setPadding(new Insets(10));
                bookBox.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

                Label bookTitleLabel = new Label(" " + book.getTitle());
                Label bookAuthorLabel = new Label(" " + book.getAuthor());

                bookBox.getChildren().addAll(bookTitleLabel, bookAuthorLabel);
                booksContainer.getChildren().add(bookBox);
            }
        }
    }

}
