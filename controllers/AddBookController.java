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
import models.Book;

public class AddBookController implements Initializable {

    DatabaseConnection handler;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfIsbn;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnAdd;
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
    private void addBook(ActionEvent event) {
        if (!tfIsbn.getText().isEmpty() && !tfTitle.getText().isEmpty() && !tfAuthor.getText().isEmpty()) {
            // Call the addBook method and check the result
            boolean rowsAffected = handler.addBook(tfIsbn.getText(), tfTitle.getText(), tfAuthor.getText(), true);

            if (rowsAffected) {
                successText.setText("Book Successfully Added!");
                tfIsbn.clear();
                tfTitle.clear();
                tfAuthor.clear();
            } else {
                successText.setText("Book already exists or could not be added.");
            }
        }else{
            successText.setText("please make sure all fields are filled");
        }
    }

}
