package controllers;

import DatabaseConnection.DatabaseConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DeleteBookController implements Initializable {

    DatabaseConnection handler;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField tfISBN;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnDelete;
    @FXML
    private Label successText;
    @FXML
    private ImageView logoImgView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseConnection.getInstance();
        Image logoImg = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        logoImgView.setImage(logoImg);
    }

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();

        root = FXMLLoader.load(getClass().getResource("/views/GetBooks.fxml"));

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void deleteBook(ActionEvent event) {
        try {
            boolean rowsAffected = handler.deleteBook(tfISBN.getText());

            if (rowsAffected) {
                successText.setText("Book Deleted Successfully!");
                tfISBN.clear();
            } else {
                successText.setText("No book found with the provided ISBN.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting book: " + e.getMessage());
            successText.setText("Error: Book could not be deleted.");
        }
    }

}
