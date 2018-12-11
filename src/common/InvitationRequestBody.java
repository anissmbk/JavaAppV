package common;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import static common.Constants.KEY_USER_FROM;
import static common.Constants.KEY_USER_TO;

public class InvitationRequestBody extends PayloadBody {
    private String userFrom;
    private String userTo;

    public InvitationRequestBody() {
    }

    public InvitationRequestBody(String userFrom, String userTo) {
        this.userFrom = userFrom;
        this.userTo = userTo;
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
    @Override
    public String toString() {
        return toJSON().toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_USER_FROM, userFrom);
        obj.put(KEY_USER_TO, userTo);

        return obj;
    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        final Gson gson = new Gson();
        return gson.fromJson(jsonObj.toString(), InvitationRequestBody.class);
    }
}
