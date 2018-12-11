package controler;

import Entity.User;
import client.ChatEventListener;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.ChatMain;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChatWindow implements ChatEventListener, Initializable {

    //pour tester
    public static User user;
    public static String username;
    public static String currentUser;

    @FXML
    private TextArea typeMessageText;

    @FXML
    private VBox clientListBox;
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
    //aniss
    @FXML
    private TextArea txtMsg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //aniss
        scrollPane.vvalueProperty().bind(chatBox.heightProperty());
        for (Node text : emojiList.getChildren()) {
            text.setOnMouseClicked(event -> {
                txtMsg.setText(txtMsg.getText() + " " + ((Text) text).getText());
                emojiList.setVisible(false);
            });
        }

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
    public void onConnectSuccess(String token, User user) {

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
                                txtMsg.clear();
                                txtMsg.setEditable(true);
                                currentUser = user;
                                System.out.println(currentUser);
                            }
                        });
                        clientListBox.getChildren().add(pane);
                    }
                }
            }
        });
    }

    /*
        @FXML//attention
        void sendMessage(MouseEvent event) {
            try {
                System.out.println("username : " + username + "current : " + currentUser);
                ChatMain.client.sendMessage(username, currentUser, typeMessageText.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    */
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

    //aniss
    @FXML
    private AnchorPane rootPane;

    @FXML
    void closeAction(ActionEvent event) {
       /* rootPane.getScene().getWindow().hide();
        Stage log1 = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("login.fxml"));
            log1.initStyle(StageStyle.UNDECORATED);
            log1.setScene(new Scene(root));
            log1.show();
            log1.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();

        }*/
        Platform.exit();
    }

    @FXML
    private TextFlow emojiList;


    @FXML
    void emojiAction(ActionEvent event) {
        if (emojiList.isVisible()) {

            emojiList.setVisible(false);
        } else {
            emojiList.setVisible(true);
        }
    }

    @FXML
    private VBox chatBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    void sendMessage(ActionEvent event) throws RemoteException {
        // if(txtMsg.getText().trim().equals(""))return;
        //txtMsg.setText("");
        //txtMsg.requestFocus();//achercher


        String msg = "assessegggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggs";
        Text text = new Text(msg);
        text.setFill(Color.WHITE);
        text.getStyleClass().add("msg");
        TextFlow tempFlow = new TextFlow();

        //pour tester
        String user_name = "aniss";
        String user_name_send = "aniss";
        if (!user_name.equals(user_name_send)) {
            Text txtName = new Text(user_name_send + "\n");
            txtName.getStyleClass().add("txtName");
            tempFlow.getChildren().add(txtName);
        }
        tempFlow.getChildren().add(text);
        tempFlow.setMaxWidth(200);

        TextFlow flow = new TextFlow(tempFlow);
        HBox hbox = new HBox(12);
        Circle img = new Circle(32, 32, 16);

        try {
            System.out.println(user_name_send);
            String path = new File(String.format("/home/aniss/Bureau/JavaAppV/src/controler/youssef.jpg", user_name_send)).toURI().toString();
            img.setFill(new ImagePattern(new Image(path)));
        } catch (Exception ex) {
            String path = new File("/home/aniss/Bureau/JavaAppV/src/controler/user.png").toURI().toString();
            img.setFill(new ImagePattern(new Image(path)));
        }
        img.getStyleClass().add("imageView");

        if (!user_name.equals(user_name_send)) {

            tempFlow.getStyleClass().add("tempFlowFlipped");
            flow.getStyleClass().add("textFlowFlipped");
            chatBox.setAlignment(Pos.TOP_LEFT);
            hbox.setAlignment(Pos.BOTTOM_LEFT);
            hbox.getChildren().add(img);
            hbox.getChildren().add(flow);

        } else {
            text.setFill(Color.WHITE);
            tempFlow.getStyleClass().add("tempFlow");
            flow.getStyleClass().add("textFlow");
            hbox.setAlignment(Pos.BOTTOM_RIGHT);
            hbox.getChildren().add(flow);
            hbox.getChildren().add(img);
        }

        hbox.getStyleClass().add("hbox");
        Platform.runLater(() -> chatBox.getChildren().addAll(hbox));
    }
    public void setUser(User profile) {
        this.user = profile;
    }
}


