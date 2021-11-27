package userCommands;

import database.Database;
import entities.Users;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class userView {
    public userView() {}

    public String setView(Database database, String username, String title) {
        Users user = null;
        for (Users forUser: database.getUsers()) {
            if (forUser.getUsername().equals(username)) {
                user = forUser;
                break;
            }
        }
        assert user != null;
        StringBuilder stringOut = new StringBuilder();
        if (user.getHistory().containsKey(title)) {
            user.getHistory().put(title, user.getHistory().get(title) + 1);
        } else {
            user.getHistory().put(title, 1);
        }
        stringOut.append("success -> ").append(title).append(" was viewed with total views of ").append(user.getHistory().get(title));
        return stringOut.toString();
    }
}
