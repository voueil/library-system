
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
import models.Setting;

public class OverseeSettingsController implements Initializable {
    
    DatabaseConnection handler;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btnBack;
    @FXML
    private VBox settingsContainer;
    @FXML
    private ImageView settingsImgView;
    @FXML
    private ImageView logoImgView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseConnection.getInstance();
        Image settingsImg = new Image(getClass().getResourceAsStream("/images/settings.png"));
        settingsImgView.setImage(settingsImg);
        Image logoImg = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        logoImgView.setImage(logoImg);
        displayAllSettings();
    }    

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();

        root = FXMLLoader.load(getClass().getResource("/views/AdminHome.fxml"));

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private void displayAllSettings(){
        List<Setting> settings = handler.disaplyAllSettings();
        settingsContainer.getChildren().clear();

        if (settings.isEmpty()) {
            Label nosettingsLabel = new Label("No Available Settings");
            settingsContainer.getChildren().add(nosettingsLabel);

        } else {
            for (Setting setting : settings) {
                HBox settingBox = new HBox(50);
                settingBox.setPadding(new Insets(5));
                settingBox.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

                Label settingKeyLabel = new Label(" " + setting.getKey());
                Label settingValueLabel = new Label(" " + setting.getValue());
                Label descriptionLabel = new Label(" " + setting.getDescription());

                settingBox.getChildren().addAll(settingKeyLabel, settingValueLabel, descriptionLabel);

                settingsContainer.getChildren().add(settingBox);
            }
        }
    }
    
}
