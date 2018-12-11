package client;

import java.util.List;

public interface ChatEventListener {
    void onConnectSuccess(String token, String profile);
    void onConnectFailed();
    void onMessageReceived(String fromId, String content);
    void oConnectedUsersListReceived(List<String> users);
    void onInvitationReceived(String fromId, String content);
    void onUserConnect(String username);   //void onUserConnect(String userId, String profile);
    void onUserLeave(String userId, String profile);
}
