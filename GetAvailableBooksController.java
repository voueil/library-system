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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Book;
import models.BookSearch;

public class GetAvailableBooksController implements Initializable {

    DatabaseConnection handler;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String userId;
    private String username;
    private BookSearch bookSearch = new BookSearch();

    @FXML
    private VBox booksContainer;
    @FXML
    private Button backBtn;
    @FXML
    private TextField searchTf;
    @FXML
    private Button searchBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private ImageView logoImgView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseConnection.getInstance();
        displayAvailableBooks();
        Image logoImg = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        logoImgView.setImage(logoImg);
    }

    public void setUserData(String userId, String username) {
        this.userId = userId;
        this.username = username;
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

    @FXML
    private void displayAvailableBooks() {
        try {
            List<Book> books = handler.displayAavilableBooks();
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
                    Button requestBtn = new Button("Request");
                    Label requestedLabel = new Label("Book is requested");

                    requestedLabel.setVisible(false);

                    requestBtn.setOnAction(e -> {
                        boolean isRequested = handler.createRequest(userId, book.getISBN());

                        if (isRequested) {
                            // Hide the request button and show the requested label
                            requestBtn.setVisible(false);
                            requestedLabel.setVisible(true);
                        } else {
                            System.out.println("Error: Could not create request");
                        }
                    });

                    bookBox.getChildren().addAll(bookTitleLabel, bookAuthorLabel, requestBtn, requestedLabel);

                    booksContainer.getChildren().add(bookBox);
                }
            }

        } catch (Exception e) {
            System.out.println("Error displaying books: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    private void performSearch(ActionEvent event) {
        List<Book> books = handler.displayAavilableBooks();
        booksContainer.getChildren().clear();

        if (!books.isEmpty()) {
            Book targetBook = new Book(null, searchTf.getText(), null, true);
            List<Book> results = bookSearch.searchList(books, targetBook);

            if (results.isEmpty()) {
                Label noBooksLabel = new Label("No Matching Books");
                booksContainer.getChildren().add(noBooksLabel);
            } else {
                for (Book result : results) {
                    HBox resultsBox = new HBox(60);
                    resultsBox.setPadding(new Insets(5));
                    resultsBox.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

                    Label bookTitleLabel = new Label(" " + result.getTitle());
                    Label bookAuthorLabel = new Label(" " + result.getAuthor());
                    Button requestBtn = new Button("Request");
                    Label requestedLabel = new Label("Book is requested");

                    requestedLabel.setVisible(false);

                    requestBtn.setOnAction(e -> {
                        boolean isRequested = handler.createRequest(userId, result.getISBN());

                        if (isRequested) {
                            // Hide the request button and show the requested label
                            requestBtn.setVisible(false);
                            requestedLabel.setVisible(true);
                        } else {
                            System.out.println("Error: Could not create request");
                        }
                    });

                    resultsBox.getChildren().addAll(bookTitleLabel, bookAuthorLabel, requestBtn, requestedLabel);

                    booksContainer.getChildren().add(resultsBox);
                }
            }
        } else {
            Label noBooksLabel = new Label("No Available Books");
            booksContainer.getChildren().add(noBooksLabel);
        }
    }
}
