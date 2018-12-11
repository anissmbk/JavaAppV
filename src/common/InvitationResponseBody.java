package common;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import static common.Constants.KEY_ANSWER;
import static common.Constants.KEY_USER_FROM;

public class InvitationResponseBody extends PayloadBody {
    private String userFrom;
    private String userTo;
    private String answer;

    public InvitationResponseBody() {
    }

    public InvitationResponseBody(String userFrom, String userTo, String answer) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.answer = answer;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_USER_FROM, userFrom);
        obj.put(KEY_USER_FROM, userTo);
        obj.put(KEY_ANSWER,answer);

        return obj;
    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        final Gson gson = new Gson();
        return gson.fromJson(jsonObj.toString(), InvitationResponseBody.class);
    }
}
