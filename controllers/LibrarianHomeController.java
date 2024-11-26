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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LibrarianHomeController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Label label;
    @FXML
    private Button btnBooks;
    @FXML
    private Button btnRequests;
    @FXML
    private Button logoutBtn;
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

            if (source == btnBooks) {
                root = FXMLLoader.load(getClass().getResource("/views/GetBooks.fxml"));
            } else if (source == btnRequests) {
                root = FXMLLoader.load(getClass().getResource("/views/GetRequests.fxml"));
            } else if (source == logoutBtn) {
                root = FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
            }

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }


}
