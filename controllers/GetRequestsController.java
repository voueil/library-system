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

public class GetRequestsController implements Initializable {

    DatabaseConnection handler;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btnBack;
    @FXML
    private Button btnOverdueBooks;
    @FXML
    private VBox requestsContainer;
    @FXML
    private ImageView logoImgView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseConnection.getInstance();
        Image logoImg = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        logoImgView.setImage(logoImg);
        displayAllRequests();
    }

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();

        if (source == btnBack) {
            root = FXMLLoader.load(getClass().getResource("/views/LibrarianHome.fxml"));
        } else if (source == btnOverdueBooks) {
            root = FXMLLoader.load(getClass().getResource("/views/OverdueBooks.fxml"));
        }

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void displayAllRequests() { //remove request ID from UI, resolve issue with status not showing
        try {
            List<Request> requests = handler.displayAllRequests();
            requestsContainer.getChildren().clear();

            if (requests.isEmpty()) {
                Label noRequestsLabel = new Label("No Available Requests");
                requestsContainer.getChildren().add(noRequestsLabel);
                
            } else {
                for (Request request : requests) {
                    if ("waiting".equals(request.getRequestStatus())) {
                        addRequestToContainer(request);
                    }
                }
                for (Request request : requests) {
                    if (!"waiting".equals(request.getRequestStatus())) {
                        addRequestToContainer(request);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error displaying requests: " + e.getMessage());
            e.printStackTrace();
        }
    }

// Helper method to add a request to the VBox container
    private void addRequestToContainer(Request request) {
        HBox requestBox = new HBox(60);
        requestBox.setPadding(new Insets(5));
        requestBox.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

        Label userIdLabel = new Label(" " + request.getUser().getID());
        Label bookIsbnLabel = new Label(" " + request.getBook().getISBN());
        Label statusLabel = new Label(" " + request.getRequestStatus());

        // Create Accept and Reject buttons only if the status is "waiting"
        if ("waiting".equals(request.getRequestStatus())) {
            Button acceptButton = new Button("Accept");
            Button rejectButton = new Button("Reject");

            acceptButton.setOnAction(e -> {
                handler.acceptRequest(request.getRequestId(), request.getBook().getISBN());
                statusLabel.setText("accepted");
                acceptButton.setVisible(false);
                rejectButton.setVisible(false);
            });

            rejectButton.setOnAction(e -> {
                handler.rejectRequest(request.getRequestId());
                statusLabel.setText("rejected");
                acceptButton.setVisible(false);
                rejectButton.setVisible(false);
            });

            requestBox.getChildren().addAll(userIdLabel, bookIsbnLabel, statusLabel, acceptButton, rejectButton);
        } else {
            // If not "waiting", display without buttons
            requestBox.getChildren().addAll(userIdLabel, bookIsbnLabel, statusLabel);
        }

        requestsContainer.getChildren().add(requestBox);
    }

}
