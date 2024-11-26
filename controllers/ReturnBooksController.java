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

public class ReturnBooksController implements Initializable {

    DatabaseConnection handler;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String userId;
    private String username;

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
        displayBorrowedBooks();
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

    private void displayBorrowedBooks() {
        try {
            List<Book> books = handler.displayBooksByUserId(userId);
            booksContainer.getChildren().clear();

            if (books.isEmpty()) {
                Label noBooksLabel = new Label("No Borrowed Books");
                booksContainer.getChildren().add(noBooksLabel);

            } else {
                for (Book book : books) {
                    HBox bookBox = new HBox(60);
                    bookBox.setPadding(new Insets(5));
                    bookBox.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

                    Label bookTitleLabel = new Label(" " + book.getTitle());
                    Label bookAuthorLabel = new Label(" " + book.getAuthor());
                    Button returnBtn = new Button("Return");
                    Label returnLabel = new Label("Book Returned");

                    returnLabel.setVisible(false);

                    returnBtn.setOnAction(e -> {
                        boolean isReturned = handler.returnBook(book.getISBN());

                        if (isReturned) {
                            // Hide the return button and show the returned get label
                            returnBtn.setVisible(false);
                            returnLabel.setVisible(true);
                        } else {
                            System.out.println("Error: Could not return book");
                        }
                    });

                    bookBox.getChildren().addAll(bookTitleLabel, bookAuthorLabel, returnBtn, returnLabel);

                    booksContainer.getChildren().add(bookBox);
                }
            }

        } catch (Exception e) {
            System.out.println("Error displaying books: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
