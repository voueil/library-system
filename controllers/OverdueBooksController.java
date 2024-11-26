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
import models.Request;

public class OverdueBooksController implements Initializable {

    DatabaseConnection handler;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btnBack;
    @FXML
    private VBox overdueContainer;
    @FXML
    private ImageView logoImgView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseConnection.getInstance();
        Image logoImg = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        logoImgView.setImage(logoImg);
        displayAllOverdues();
    }

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();

        root = FXMLLoader.load(getClass().getResource("/views/GetRequests.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void displayAllOverdues() {
        try {
            List<Request> overdue = handler.displayOverdueBorrows();
            overdueContainer.getChildren().clear();

            if (overdue.isEmpty()) {
                Label noOverduesLabel = new Label("No Overdue Books");
                overdueContainer.getChildren().add(noOverduesLabel);

            } else {
                for (Request request : overdue) {
                    HBox overdueBox = new HBox(60);
                    overdueBox.setPadding(new Insets(5));
                    overdueBox.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

                    Label requestIDLabel = new Label(" " + request.getRequestId());
                    Label userIDLabel = new Label(" " + request.getUser().getID());
                    Label bookIsbnLabel = new Label(" " + request.getBook().getISBN());
                    Label borrowDateLabel = new Label(" " + request.getBorrowDate());
                    Label returnDateLabel = new Label(" " + request.getReturnDate());

                    overdueBox.getChildren().addAll(requestIDLabel, userIDLabel, bookIsbnLabel, borrowDateLabel, returnDateLabel);

                    overdueContainer.getChildren().add(overdueBox);
                }
            }
        } catch (Exception e) {
            System.out.println("Error displaying requests: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
