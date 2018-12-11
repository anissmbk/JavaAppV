package client;

import Entity.User;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public interface ChatEventListener {
    void onConnectSuccess(String token, User user);
    void onConnectFailed();
    void onMessageReceived(String fromId, String content);
    void oConnectedUsersListReceived(List<String> users);
    void onInvitationReceived(String fromId, String content);
    void onUserConnect(String username);   //void onUserConnect(String userId, String profile);
    void onUserLeave(String userId, String profile);
}
