package common;

import Entity.User;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

import static common.Constants.*;

public class AuthorizationResponseBody extends PayloadBody {

    private String token;
    private User user;

    public AuthorizationResponseBody() {

    }

    public AuthorizationResponseBody(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_TOKEN, token);
        obj.put(KEY_USERNAME, user.getUsername());
        obj.put(KEY_EMAIL, user.getEmail());
        obj.put(KEY_SEXE ,user.isSexe());

        return obj;
    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        final Gson gson = new Gson();
        return gson.fromJson(jsonObj.toString(), AuthorizationResponseBody.class);
    }
}
