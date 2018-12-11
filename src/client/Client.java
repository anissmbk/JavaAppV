package client;

import Entity.User;
import common.*;
import controler.Login;
import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static common.Constants.*;

public class Client implements Runnable {

    public Socket socket;
    private BufferedReader in;
    private String authToken = null;
    User user;
    private List<ChatEventListener> listeners = new ArrayList<>();

    public void addChatEventListener(ChatEventListener listener) {
        synchronized (listeners) {
            if (!listeners.contains(listener)) {
                listeners.add((listener));
            }
        }
    }

    public static void main(String[] args) {

    }

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 2009);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                Response response = Response.fromJSON(in.readLine());
                System.out.println(response);

                if (TYPE_AUTHORIZATION.equals(response.type)) {
                    if (STATUS_SUCCESS.equals(response.status)) {
                        user = ((AuthorizationResponseBody) response.body).getUser();
                        System.out.println(((AuthorizationResponseBody) response.body).getUser());
                        authToken = ((AuthorizationResponseBody) response.body).getToken();
                        if (authToken != null) {
                            for (ChatEventListener listener :
                                    listeners) {
                                listener.onConnectSuccess(authToken,user);
                            }
                            continue;
                        }
                    }else if(STATUS_FORBIDDEN.equals(response.status)){
                        for (ChatEventListener listener :
                        listeners) {
                            listener.onConnectFailed();
                        }
                        continue;
                    }
                }
                if (TYPE_INSCRIPTION.equals(response.type)) {
                    if (STATUS_SUCCESS.equals(response.status)) {
                            for (ChatEventListener listener :
                                    listeners) {
                                listener.onConnectSuccess(authToken,null);
                            }
                    }else if(STATUS_FORBIDDEN.equals(response.status)){
                        for (ChatEventListener listener :
                                listeners) {
                            listener.onConnectFailed();
                        }
                        continue;
                    }
                }

                if(TYPE_INVITATION.equals(response.type)){
                    if (STATUS_SUCCESS.equals(response.status)) {
                        if(response.body != null){
                            String fromId = ((InvitationRequestBody)response.body).getUserFrom();
                            for (ChatEventListener listener : listeners){
                                listener.onInvitationReceived(fromId,null);
                            }
                        }else {

                        }
                    }
                }
                if(TYPE_SEND_USERS_LIST.equals(response.type)){
                    if(STATUS_SUCCESS.equals(response.status)){
                        List<String> users = ((ConnectedUsersResponseBody)response.body).getUsers();
                        for (ChatEventListener listener : listeners){
                            listener.oConnectedUsersListReceived(users);
                        }
                    }
                }
                if (TYPE_MESSAGE.equals(response.type)){
                    if(STATUS_SUCCESS.equals(response.status)){
                        String userFrom = ((MessageResponseBody)response.body).getUserFrom();
                        System.out.println(userFrom);
                        String content = ((MessageResponseBody)response.body).getMessage();
                        for (ChatEventListener listener : listeners){
                            listener.onMessageReceived(userFrom, content);
                        }
                    }
                }
                if (TYPE_CONNECTION_NOTIFICATION.equals(response.type)){
                    if (STATUS_SUCCESS.equals(response.status)){
                        String username = ((ConnectionNotificationBody)response.body).getUsername();
                        for (ChatEventListener listener : listeners){
                            listener.onUserConnect(username);
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect(String username, String password) throws IOException {
        new Thread(new ConnectThread(socket, authToken, username, password)).start();
    }

    public void invite(String userFrom, String userTo) throws IOException {
        new Thread(new InvitationThread(socket, authToken, userFrom, userTo)).start();
    }

    public void sendMessage(String userFrom, String userTo ,String message) throws IOException {
        new Thread(new MessageThread(socket, authToken, userTo, userFrom , message)).start();
    }

    public void sendUserList() throws IOException {
        new Thread(new SendUsersListThread(socket , authToken)).start();
    }
    public void ConnectionNotification(String username) throws IOException {
        new Thread(new ConnectionNotificationThread(socket , authToken, username)).start();
    }

    public void inscriptionUser(String username,String password,String email,boolean sexe) throws IOException {
        new Thread(new InscriptionThread(socket,authToken,username,password,email,sexe)).start();
    }
}
