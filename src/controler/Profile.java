package controler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Profile {

    @FXML
    public AnchorPane pane2;
    @FXML
    public void backactionn() {

        pane2.getScene().getWindow().hide();
        Stage log1 = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("app.fxml"));
            log1.initStyle(StageStyle.UNDECORATED);
            log1.setScene(new Scene(root));
            log1.show();
            log1.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    @FXML AnchorPane hidden;

    AnchorPane home;


    public void createpage() {
        try {

            home = FXMLLoader.load(getClass().getResource("hiddenprofile.fxml"));
            setNode(home);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setNode(Node node) {
        //pane1.getChildren().clear();
        hidden.getChildren().add((Node) node);


    }
}