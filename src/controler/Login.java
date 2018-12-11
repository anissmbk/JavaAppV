package controler;

import Entity.User;
import client.ChatEventListener;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
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
import main.ChatMain;

import java.io.IOException;
import java.util.List;

public class Login implements ChatEventListener {

    public static boolean registered = false;

    public Login() {
        ChatMain.client.addChatEventListener(this);
    }
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
        Platform.runLater(() -> {
            if (this.usernameRegister != null || !this.usernameRegister.isEmpty()) {
                Dialog.loadDialog(usernameRegister,mainPane);
                usernameRegister = null;
            }
        });
    }

    public void setUser(String user_id){
        this.usernameRegister = user_id;
    }

        @FXML
    void signIn(ActionEvent event) throws IOException {

        if(user.getText().trim().isEmpty() || password.getText().trim().isEmpty()){
            erreur.setStyle("-fx-text-inner-color:red;-fx-background-color:transparent");
            erreur.setText("Veuillez remplir tout les champs");
        }
        else{
            imageView.setVisible(true);
            button.setDisable(true);
            buttonSignIn.setDisable(true);
            ChatMain.client.connect(user.getText(), password.getText());
        }
    }


    @FXML
    void signUp(ActionEvent event) throws IOException {
        imageView.setVisible(true);
        try{
            imageView.setVisible(true);
            Parent signUpWindow = FXMLLoader.load(getClass().getResource("../view/register.fxml"));
            Scene scene = new Scene(signUpWindow);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            imageView.setVisible(false);
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


    @Override
    public void onConnectSuccess(String token, User profile) {
        System.out.println("token = " + token);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ChatWindow.username = user.getText();
                Parent appWindow = null;
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/chatWindow.fxml"));
                    //ChatWindow controller = fxmlLoader.<ChatWindow>getController();
                    appWindow = FXMLLoader.load(ChatWindow.class.getResource("../view/chatWindow.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene newScene = new Scene(appWindow);
                ChatMain.primaryStage.setScene(newScene);
            }
        });
        imageView.setVisible(false);
        button.setDisable(false);
        buttonSignIn.setDisable(false);
    }

    @Override
    public void onConnectFailed() {
        imageView.setVisible(false);
        button.setDisable(false);
        buttonSignIn.setDisable(false);
        erreur.setStyle("-fx-text-inner-color: red;-fx-background-color: rgba(0, 0, 0, 0);");
        erreur.setText("Username Incorrect");
    }

    @Override
    public void onMessageReceived(String fromId, String content) {

    }

    @Override
    public void oConnectedUsersListReceived(List<String> users) {

    }

    @Override
    public void onInvitationReceived(String fromId, String content) {

    }

    @Override
    public void onUserConnect(String username) {

    }

    @Override
    public void onUserLeave(String userId, String profile) {

    }

}
