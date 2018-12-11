package controler;

import client.ChatEventListener;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.ChatMain;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChatWindow implements ChatEventListener, Initializable {

    //pour tester
    public static String username;
    public static String currentUser;

    @FXML
    private TextArea typeMessageText;
    @FXML
    private TextField userToInvite;
    @FXML
    private TextFlow messagesBoard;

    @FXML
    private VBox vboxOldMessages;

    @FXML
    private VBox vboxFriendsConnected;

    @FXML
    private FontAwesomeIcon seneMessagebutton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ChatMain.client.addChatEventListener(this);
        try {
            ChatMain.client.sendUserList();
            ChatMain.client.ConnectionNotification(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void sendInvitation(ActionEvent event) {
        try {
            ChatMain.client.invite(username, userToInvite.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectSuccess(String token, String profile) {

    }

    @Override
    public void onConnectFailed() {

    }

    @Override
    public void onMessageReceived(String fromId, String content) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                AnchorPane pane = new AnchorPane();
                pane.setStyle("-fx-background-color: aqua");
                pane.setStyle("-fx-border-color: black");
                Text text = new Text(content);
                text.setFill(Color.BLACK);
                text.setTranslateX(20);
                text.setTranslateY(500);
                text.setFont(new Font("Trebuchet MS", 36));
                pane.getChildren().add(text);
                messagesBoard.getChildren().add(pane);
            }
        });
    }

    @Override
    public void oConnectedUsersListReceived(List<String> users) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (String user : users) {
                    if (!user.equals(username)) {
                        Pane pane = new Pane();
                        Button button = new Button(user);
                        button.setPrefSize(99, 17);
                        button.setLayoutX(20);
                        button.setLayoutY(26);
                        Font font = new Font("verdana", 18);
                        button.setFont(font);
                        button.setTextFill(Color.WHITE);
                        pane.getChildren().add(button);
                        button.setId(user);
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                typeMessageText.clear();
                                typeMessageText.setEditable(true);
                                currentUser = user;
                                System.out.println(currentUser);
                            }
                        });
                        vboxFriendsConnected.getChildren().add(pane);
                    }
                }
            }
        });
    }

    @FXML
    void sendMessage(MouseEvent event) {
        try {
            System.out.println("username : " + username + "current : " + currentUser);
            ChatMain.client.sendMessage(username, currentUser, typeMessageText.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInvitationReceived(String fromId, String content) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Invitation received");
                alert.setHeaderText(content);

                ButtonType buttonAccept = new ButtonType("Accept");
                ButtonType buttonReject = new ButtonType("Rejecte");

                alert.getButtonTypes().setAll(buttonAccept, buttonReject);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonAccept) {
                    // ....
                } else {
                    // ....
                }
            }
        });


    }

    @Override
    public void onUserConnect(String userId) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Pane pane = new Pane();
                Button button = new Button(userId);
                button.setPrefSize(99, 17);
                button.setLayoutX(20);
                button.setLayoutY(26);
                Font font = new Font("verdana", 18);
                button.setFont(font);
                button.setTextFill(Color.WHITE);
                pane.getChildren().add(button);
                button.setId(userId);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        typeMessageText.clear();
                        typeMessageText.setEditable(true);
                        currentUser = userId;
                        System.out.println(currentUser);
                    }
                });
                vboxFriendsConnected.getChildren().add(pane);
            }
        });
    }

    @Override
    public void onUserLeave(String userId, String profile) {

    }

    @FXML
    void testfriends(ActionEvent event) {
        String users[] = {"hassan", "youssef"};
        for (String user : users) {
            Pane pane = new Pane();
            Label label = new Label("Username");
            label.setPrefSize(99, 17);
            label.setLayoutX(88);
            label.setLayoutY(26);
            Font font = new Font("verdana", 18);
            label.setFont(font);
            label.setTextFill(Color.WHITE);
            pane.getChildren().add(label);
            vboxFriendsConnected.getChildren().add(pane);
        }
    }

    @FXML
    void testinvit(ActionEvent event) {

    }

    @FXML
    void testmessage(ActionEvent event) {

    }

}


