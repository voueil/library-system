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

public class EditUserController implements Initializable {

    DatabaseConnection handler;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField tfOldId;
    @FXML
    private TextField tfNewId;
    @FXML
    private TextField tfNewUsername;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnEdit;
    @FXML
    private Label msgLabel;
    @FXML
    private ImageView userImgView;
    @FXML
    private ImageView logoImgView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseConnection.getInstance();
        Image userImg = new Image(getClass().getResourceAsStream("/images/user.jpg"));
        userImgView.setImage(userImg);
        Image logoImg = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        logoImgView.setImage(logoImg);
    }

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();

        root = FXMLLoader.load(getClass().getResource("/views/ManageUsers.fxml"));

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void editUser(ActionEvent event) {
        String id = tfNewId.getText();
        String username = tfNewUsername.getText();
        String oldId = tfOldId.getText();
        String role;
        boolean rowsAffected = false;

        if (id.startsWith("11")) {
            role = "Administrator";
            rowsAffected = handler.editUser(username, role, id, oldId);
        } else if (id.startsWith("22")) {
            role = "Librarian";
            rowsAffected = handler.editUser(username, role, id, oldId);
        } else if (id.startsWith("33")) {
            role = "Student";
            rowsAffected = handler.editUser(username, role, id, oldId);
        } else {
            msgLabel.setText("Invalid type: Unknown user type");
        }

        if (rowsAffected) {
            msgLabel.setText("User Updated Successfully!");
            tfNewId.clear();
            tfNewUsername.clear();
            tfOldId.clear();
        } else {
            msgLabel.setText("No user found with the provided ID to update.");
        }
    }

}
