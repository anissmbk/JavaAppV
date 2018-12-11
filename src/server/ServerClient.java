package server;

import java.net.Socket;

public class ServerClient {

    private Socket socket;
    private String user;

    public ServerClient(Socket socket, String user) {
        this.socket = socket;
        this.user = user;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
