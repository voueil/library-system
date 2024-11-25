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
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.User;

public class HomeController implements Initializable {

    DatabaseConnection handler;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String userId;
    private String username;

    private String role;

    @FXML
    private TextField userIdField;
    @FXML
    private TextField usernameField;
    @FXML
    private Button submitButton;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView bookImgView;
    @FXML
    private ImageView logoImgView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseConnection.getInstance();
        Image bookImg = new Image(getClass().getResourceAsStream("/images/home.jpg"));
        bookImgView.setImage(bookImg);
        Image logoImg = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        logoImgView.setImage(logoImg);
    }

    private void handleNavigation(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();
        FXMLLoader loader;

        // Determine the correct FXML file based on user role
        switch (role) {
            case "Administrator":
                loader = new FXMLLoader(getClass().getResource("/views/AdminHome.fxml"));
                root = loader.load();
                break;
            case "Librarian":
                loader = new FXMLLoader(getClass().getResource("/views/LibrarianHome.fxml"));
                root = loader.load();
                break;
            case "Student":
                loader = new FXMLLoader(getClass().getResource("/views/StudentHome.fxml"));
                root = loader.load();

                StudentHomeController scontroller = loader.getController();
                scontroller.setUserData(userId, username);
                break;
            default:
                errorLabel.setText("Error: Unknown user role for navigation.");
                return;
        }

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        // Retrieve text from the input fields
        String userIdText = userIdField.getText();
        userId = userIdText;

        String usernameText = usernameField.getText();
        username = usernameText;

        // Validate userId as a number
        try {
            Integer.parseInt(userIdText);
        } catch (NumberFormatException e) {
            // Show error if userId is not a number
            errorLabel.setText("Invalid type: User ID must be a number");
            return;
        }

        // Check if username is provided
        if (usernameText.isEmpty()) {
            errorLabel.setText("Invalid type: Username cannot be empty");
            return;

        }

        // Determine user type based on the userId prefix
        if (!userExists(userIdText)) {

            if (userIdText.startsWith("11")) {
                role = "Administrator";
                boolean rowsAffected = handler.addUser(userIdText, usernameText, role);
                if (rowsAffected) {
                    System.out.println("New Administrator account created.");
                }

            } else if (userIdText.startsWith("22")) {
                role = "Librarian";
                boolean rowsAffected = handler.addUser(userIdText, usernameText, role);
                if (rowsAffected) {
                    System.out.println("New Librarian account created.");
                }

            } else if (userIdText.startsWith("33")) {
                role = "Student";
                boolean rowsAffected = handler.addUser(userIdText, usernameText, role);
                if (rowsAffected) {
                    System.out.println("New Student account created.");
                }

            } else {
                errorLabel.setText("Invalid type: Unknown user type");
                return;
            }

        } else {
            User user = handler.getUserById(userIdText);
            role = user.getRole();
            System.out.println("User already exists. Proceeding with login.");
        }

        try {
            handleNavigation(event);
        } catch (IOException e) {
            System.out.println("Error navigating to UserDetails.fxml: " + e.getMessage());
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
