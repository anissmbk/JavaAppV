package common;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import static common.Constants.KEY_SEND_USERS;

public class ConnectedUsersRequestBody extends PayloadBody {



    public ConnectedUsersRequestBody() {
    }


    @Override
    public String toString() {
        return toJSON().toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put(KEY_SEND_USERS,"sendUsers");
        return obj;

    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        final Gson gson = new Gson();
        return gson.fromJson(jsonObj.toString(), ConnectedUsersRequestBody.class);
    }
}
