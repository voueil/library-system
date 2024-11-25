package controllers;

import DatabaseConnection.DatabaseConnection;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
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
import models.User;

public class AddUserController implements Initializable {

    DatabaseConnection handler;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField tfID;
    @FXML
    private TextField tfUsername;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnAdd;
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
    private void addUser(ActionEvent event) {
        String role;
        String userIdText = tfID.getText();

        if (!userExists(userIdText)) {

            if (userIdText.startsWith("11")) {

                role = "Administrator";
                boolean rowsAffected = handler.addUser(userIdText, tfUsername.getText(), role);

                if (rowsAffected) {
                    msgLabel.setText("New Administrator account created.");
                    tfID.clear();
                    tfUsername.clear();
                } else {
                    msgLabel.setText("User already exists or could not be added.");
                }

            } else if (userIdText.startsWith("22")) {
                role = "Librarian";
                boolean rowsAffected = handler.addUser(userIdText, tfUsername.getText(), role);

                if (rowsAffected) {
                    msgLabel.setText("New Librarian account created.");
                    tfID.clear();
                    tfUsername.clear();
                } else {
                    msgLabel.setText("User already exists or could not be added.");
                }

            } else if (userIdText.startsWith("33")) {
                role = "Student";
                boolean rowsAffected = handler.addUser(userIdText, tfUsername.getText(), role);

                if (rowsAffected) {
                    msgLabel.setText("New Student account created.");
                    tfID.clear();
                    tfUsername.clear();
                } else {
                    msgLabel.setText("User already exists or could not be added.");
                }

            } else {
                msgLabel.setText("Invalid type: Unknown user type");
                return;
            }
        } else {
            msgLabel.setText("User already exists.");
        }
    }

    private boolean userExists(String userId) {
        List<User> allUsers = handler.displayAllUsers();

        Iterator<User> it = allUsers.iterator();

        while (it.hasNext()) {
            User currentUser = it.next();
            if (currentUser.getID().equals(userId)) {
                return true;
            }
        }
        return false;
    }

}
