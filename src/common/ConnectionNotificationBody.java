package common;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import static common.Constants.KEY_USERNAME;

public class ConnectionNotificationBody extends PayloadBody {

    private String username;

    public ConnectionNotificationBody() {
    }

    public ConnectionNotificationBody(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put(KEY_USERNAME, username);
        return obj;

    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        final Gson gson = new Gson();
        return gson.fromJson(jsonObj.toString(), ConnectionNotificationBody.class);
    }
}
