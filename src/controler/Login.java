package controler;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Login {

    public static boolean registered = false;

    @FXML
    private ImageView imageView;
    @FXML
    private JFXButton button;
    @FXML
    private JFXButton buttonSignIn;
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private TextField erreur;
    @FXML
    private StackPane mainPane;

    private String usernameRegister;

    @FXML
    public void initialize() {
        imageView.setVisible(false);
    }

        @FXML
    void signIn(ActionEvent event) throws IOException {

        if(user.getText().trim().isEmpty() || password.getText().trim().isEmpty()){
            erreur.setStyle("-fx-text-inner-color:red;-fx-background-color:transparent");
            erreur.setText("Veuillez remplir tout les champs");
        }
        else{

        }
    }


    @FXML
    void signUp(ActionEvent event) throws IOException {
        imageView.setVisible(true);
        try{
            Parent signUpWindow = FXMLLoader.load(getClass().getResource("../view/register.fxml"));
            Scene scene = new Scene(signUpWindow);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }catch(Exception e){
            System.out.println("erreur system verifiez tout les fichiers");
        }
        Stage mainWindow;
        mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindow.close();
        imageView.setVisible(false);
    }

    @FXML
    public void closeprog(ActionEvent event) throws IOException {
        Stage mainWindow;
        mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindow.close();
    }

}
