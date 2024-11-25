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
import models.Student;
import models.User;
import models.UserSearch;

public class ManageUsersController implements Initializable {

    DatabaseConnection handler;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private UserSearch userSearch = new UserSearch();
    
    @FXML
    private Button backBtn;
    @FXML
    private Button Addbtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private VBox usersContainer;
    @FXML
    private Button editBtn;
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
        Image logoImg = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        logoImgView.setImage(logoImg);
        displayAllUsers();
    }

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();

        if (source == backBtn) {
            root = FXMLLoader.load(getClass().getResource("/views/AdminHome.fxml"));
        } else if (source == Addbtn) {
            root = FXMLLoader.load(getClass().getResource("/views/AddUser.fxml"));
        } else if (source == editBtn) {
            root = FXMLLoader.load(getClass().getResource("/views/EditUser.fxml"));
        } else if (source == deleteBtn) {
            root = FXMLLoader.load(getClass().getResource("/views/DeleteUser.fxml"));
        }

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void displayAllUsers() {
        try {
            List<User> users = handler.displayAllUsers();
            usersContainer.getChildren().clear();

            if (users.isEmpty()) {
                Label noUsersLabel = new Label("No Available Users");
                usersContainer.getChildren().add(noUsersLabel);

            } else {
                for (User user : users) {
                    HBox userBox = new HBox(100);
                    userBox.setPadding(new Insets(10));
                    userBox.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

                    Label userIdLabel = new Label(" " + user.getID());
                    Label usernameLabel = new Label(" " + user.getUsername());
                    Label userRoleLabel = new Label(" " + user.getRole());

                    userBox.getChildren().addAll(userIdLabel, usernameLabel, userRoleLabel);

                    usersContainer.getChildren().add(userBox);
                }
            }

        } catch (Exception e) {
            System.out.println("Error displaying users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void performSearch(ActionEvent event) {
        List<User> users = handler.displayAllUsers();
        usersContainer.getChildren().clear();

        if (!users.isEmpty()) {
            User targetUser = new Student(searchTf.getText(), null);
            List<User> results = userSearch.searchList(users, targetUser);

            if (results.isEmpty()) {
                Label noUsersLabel = new Label("No Matching Users");
                usersContainer.getChildren().add(noUsersLabel);
            } else {
                for (User result : results) {
                    HBox resultsBox = new HBox(100);
                    resultsBox.setPadding(new Insets(10));
                    resultsBox.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

                    Label userIdLabel = new Label(" " + result.getID());
                    Label usernameLabel = new Label(" " + result.getUsername());
                    Label userRoleLabel = new Label(" " + result.getRole());

                    resultsBox.getChildren().addAll(userIdLabel, usernameLabel, userRoleLabel);
                    usersContainer.getChildren().add(resultsBox);
                }
            }
        } else {
            Label noBooksLabel = new Label("No Available Books");
            usersContainer.getChildren().add(noBooksLabel);
        }
    }

}
