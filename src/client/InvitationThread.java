package client;

import common.InvitationRequestBody;
import common.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.Constants.TYPE_INVITATION;

public class InvitationThread extends ChatThread implements Runnable {
    private String userFrom;
    private String userTo;

    public InvitationThread(Socket socket, String authToken, String userFrom, String userTo) throws IOException {
        super(socket, authToken);
        this.userFrom = userFrom;
        this.userTo = userTo;
    }

    @Override
    public void run() {

        String responseId = null;
        InvitationRequestBody requestBody = new InvitationRequestBody(userFrom, userTo);
        Request request = new Request(UUID.randomUUID().toString(), responseId, TYPE_INVITATION, requestBody, authToken);
        try {

            out.println(request);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
