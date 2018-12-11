package server;

import Entity.User;
import common.*;
import main.ChatMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import static common.Constants.*;


public class AccepterConnexion implements Runnable {

    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    public static final String AUTHTOKEN_TEST = "jkloduurejfksg126g5sfg489g3sdg";

    public AccepterConnexion(Socket socket) {
        try {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        boolean isConnected = !socket.isClosed() && socket.isConnected();
        while (isConnected) {
            try {
                Request request = Request.fromJSON(in.readLine());

                if (TYPE_AUTHORIZATION.equals(request.type)) {
                    authenticate(request);
                }
                if (TYPE_SEND_USERS_LIST.equals(request.type)) {
                    sendConnectedUsers();
                }
                if (TYPE_INVITATION.equals(request.type)) {
                    dispatchInvitation(request);
                }
                if (TYPE_MESSAGE.equals(request.type)){
                    sendMessage(request);
                }
                if (TYPE_CONNECTION_NOTIFICATION.equals(request.type)){
                    sendConnectionNotification(request);
                }
                if(TYPE_INSCRIPTION.equals(request.type))
                    inscription(request);

            } catch (SocketException e) {
                isConnected = false;
            } catch (IOException e) {
                isConnected = false;
            }
        }

        //peer disconnected ....

    }

    public boolean authenticate(Request request) {

        String username = ((AuthorizationRequestBody) request.body).getUsername();
        String password = ((AuthorizationRequestBody) request.body).getPassword();
        Response response = new Response();
        response.requestId = request.id;
        response.id = UUID.randomUUID().toString();

        try {
            if(Verfification_data_User(username,password)){
                new Server().AddClient(new ServerClient(socket, username));
                response.body = new AuthorizationResponseBody(AUTHTOKEN_TEST, username);
                response.status = STATUS_SUCCESS;
                response.type = TYPE_AUTHORIZATION;
            }
            else{
                response.status = STATUS_FORBIDDEN;
                response.type = TYPE_AUTHORIZATION;
                response.body = new AuthorizationResponseBody();
            }

        } catch (Exception e) {
            response.status = STATUS_INTERNAL_SERVER_ERROR;
            response.message = e.getMessage();
        }

        out.println(response);
        out.flush();

        return true;
    }

    public boolean inscription(Request request) {

        String username = ((InscriptionRequestBody) request.body).getUsername();
        String password = ((InscriptionRequestBody) request.body).getPassword();
        String email = ((InscriptionRequestBody) request.body).getEmail();
        Boolean sexe = ((InscriptionRequestBody) request.body).isSexe();
        Response response = new Response();
        response.requestId = request.id;
        response.id = UUID.randomUUID().toString();

        try {
            User user = new User(username,password,email,sexe.toString());
            if(!Verfification_data_User(username,null)){
                user.Add_data_user();
                response.body = new InscriptionResponseBody(AUTHTOKEN_TEST, username);
                response.status = STATUS_SUCCESS;
                response.type = TYPE_INSCRIPTION;
            }
            else{
                response.status = STATUS_FORBIDDEN;
                response.type = TYPE_INSCRIPTION;
                response.body = new InscriptionResponseBody();
            }
        } catch (Exception e) {
            response.status = STATUS_INTERNAL_SERVER_ERROR;
            response.message = e.getMessage();
        }

        out.println(response);
        out.flush();

        return true;
    }


    public void sendConnectedUsers(){
        List<String> users = Server.getUsers();
        Response response = new Response(UUID.randomUUID().toString(),null,TYPE_SEND_USERS_LIST,STATUS_SUCCESS,
                new ConnectedUsersResponseBody(users),null);
        out.println(response);
        out.flush();
    }

    public void sendConnectionNotification(Request request){
        String username = ((ConnectionNotificationBody) request.body).getUsername();
        Response response = new Response(UUID.randomUUID().toString(),null,TYPE_CONNECTION_NOTIFICATION,STATUS_SUCCESS,
                new ConnectionNotificationBody(username),null);
        List<String> users = Server.getUsers();
        for (String user : users){
            if(!user.equals(username)){
                ServerClient serverClient = Server.getServerClient(user);
                PrintWriter out;
                try {
                    out = new PrintWriter(serverClient.getSocket().getOutputStream());
                    out.println(response);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void dispatchInvitation(Request request) {

        String userFrom = ((InvitationRequestBody) request.body).getUserFrom();
        String userTo = ((InvitationRequestBody) request.body).getUserTo();

        Response response = new Response();
        response.requestId = request.id;
        response.id = UUID.randomUUID().toString();
        response.type = TYPE_INVITATION;

        try {
            ServerClient serverClient = Server.getServerClient(userTo);
            if (serverClient != null) {
                sendInvitation(serverClient.getSocket(), userTo);
                response.status = STATUS_SUCCESS;

            }else {
                response.status = STATUS_NOT_FOUND;
            }

        } catch (Exception e) {
            response.status = STATUS_INTERNAL_SERVER_ERROR;
            response.message = e.getMessage();
        }
        out.println(response);
        out.flush();
    }

    public void sendInvitation(Socket socket, String userFrom){
        Response response = new Response(UUID.randomUUID().toString(), null, TYPE_INVITATION, STATUS_SUCCESS, new InvitationResponseBody(userFrom, null, null), null);
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(response);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Request request) {
        String userFrom = ((MessageRequestBody) request.body).getUserFrom();
        String userTo = ((MessageRequestBody) request.body).getUserTo();
        String message = ((MessageRequestBody) request.body).getMessage();

        Response response = new Response();
        response.requestId = request.id;
        response.id = UUID.randomUUID().toString();
        ServerClient serverClient = Server.getServerClient(userTo);
        try {
            if (serverClient != null) {
                response.body = new MessageResponseBody(userFrom, userTo, message);
                response.status = STATUS_SUCCESS;
                response.type = TYPE_MESSAGE;
            }

        } catch (Exception e) {
            response.status = STATUS_INTERNAL_SERVER_ERROR;
            response.message = e.getMessage();
        }
        PrintWriter out = null;
        try {
            out = new PrintWriter(serverClient.getSocket().getOutputStream());
            out.println(response);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean Verfification_data_User(String info, String info1) throws SQLException, ParseException {
        User user = new User(info,info1);
        return user.Verification_data_user(info, info1);
    }
}
