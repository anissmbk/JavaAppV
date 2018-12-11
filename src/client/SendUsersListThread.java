package client;

import common.ConnectedUsersRequestBody;
import common.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.Constants.TYPE_SEND_USERS_LIST;

public class SendUsersListThread extends ChatThread implements Runnable {


    public SendUsersListThread(Socket socket, String authToken) throws IOException {
        super(socket, authToken);
    }

    @Override
    public void run() {
        String responseId = null;
        ConnectedUsersRequestBody requestBody = new ConnectedUsersRequestBody();
        Request request = new Request(UUID.randomUUID().toString(), responseId, TYPE_SEND_USERS_LIST, requestBody, authToken);

        try {
            out.println(request);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
