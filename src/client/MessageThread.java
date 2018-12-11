package client;


import common.MessageRequestBody;
import common.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.Constants.TYPE_MESSAGE;

public class MessageThread extends ChatThread implements Runnable {

    private String userTo;
    private String userFrom;
    private String message;

    public MessageThread(Socket socket, String authToken, String userTo, String userFrom, String message) throws IOException {
        super(socket, authToken);
        this.userTo = userTo;
        this.userFrom = userFrom;
        this.message = message;
    }

    @Override
    public void run() {

        String responseId = null;
        MessageRequestBody requestBody = new MessageRequestBody(userFrom, userTo, message);
        Request request = new Request(UUID.randomUUID().toString(), responseId, TYPE_MESSAGE, requestBody, authToken);

        try {
            out.println(request);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
