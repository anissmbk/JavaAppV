package controler;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class App /*implements Initializable */{
    AnchorPane home;

    @FXML
    public AnchorPane pane1;
    @FXML
    private TextFlow tofl;


    @FXML
    void send(ActionEvent event) {

    }

    public void setNode(Node node) {
        //pane1.getChildren().clear();
        pane1.getChildren().add((Node) node);
        FadeTransition tr = new FadeTransition(Duration.millis(1500));
        tr.setNode(node);
        tr.setFromValue(0.1);
        tr.setToValue(1);
        tr.setCycleCount(1);
        tr.setAutoReverse(false);
        tr.play();

    }


 /*   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            home = FXMLLoader.load(getClass().getResource("profile.fxml"));
            setNode(home);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void createpage() {
        try {

            home = FXMLLoader.load(getClass().getResource("profile.fxml"));
            setNode(home);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        pane1.getScene().getWindow().hide();
        Stage log = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(root);
            log.initStyle(StageStyle.UNDECORATED);
            log.setScene(scene);
            log.show();
            log.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void exit() {
        Platform.exit();
    }

    static void loadScreen(ActionEvent event) {
        Parent appWindow = null;
        try {
            appWindow = FXMLLoader.load(App.class.getResource("app.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage mainWindow;
        mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene newScene = new Scene(appWindow);

        mainWindow.setScene(newScene);
    }


}