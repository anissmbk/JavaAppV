package common;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.util.List;

import static common.Constants.KEY_USERS;

public class ConnectedUsersResponseBody extends PayloadBody {
    private List<String> users;

    public ConnectedUsersResponseBody() {
    }

    public ConnectedUsersResponseBody(List<String> users) {
        this.users = users;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_USERS, users);

        return obj;
    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        final Gson gson = new Gson();
        return gson.fromJson(jsonObj.toString(), ConnectedUsersResponseBody.class);
    }

}
