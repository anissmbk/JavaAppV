package common;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import static common.Constants.*;

public class MessageRequestBody extends PayloadBody {
    String userFrom ;
    String userTo;
    String message;

    public MessageRequestBody() {
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageRequestBody(String userFrom, String userTo, String message) {

        this.userFrom = userFrom;
        this.userTo = userTo;
        this.message = message;
    }
    @Override
    public String toString() {
        return toJSON().toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_USER_FROM, userFrom);
        obj.put(KEY_USER_TO, userTo);
        obj.put(KEY_MESSAGE, message);

        return obj;
    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        final Gson gson = new Gson();
        return gson.fromJson(jsonObj.toString(), MessageRequestBody.class);
    }

}
