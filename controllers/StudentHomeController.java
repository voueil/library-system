package controllers;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class StudentHomeController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String userId;
    private String username;

    @FXML
    private Button browseBtn;
    @FXML
    private Button returnBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button detailsBtn;
    @FXML
    private ImageView logoImgView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image logoImg = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        logoImgView.setImage(logoImg);
    }

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();

        if (source == browseBtn) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GetAvailableBooks.fxml"));
            root = loader.load();
            GetAvailableBooksController controller = loader.getController();
            controller.setUserData(userId, username);

        } else if (source == returnBtn) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ReturnBooks.fxml"));
            root = loader.load();
            ReturnBooksController controller = loader.getController();
            controller.setUserData(userId, username);

        } else if (source == detailsBtn) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserDetails.fxml"));
            root = loader.load();
            UserDetailsController controller = loader.getController();
            controller.setUserData(userId, username);

        } else if (source == logoutBtn) {
            root = FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
        }

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setUserData(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

}
