package userCommands;

import commands.Actions;
import database.Database;
import entities.Users;
import fileio.ActionInputData;

public class userFavorite {
    public userFavorite() {}
    public String setFavorite(Database database, Actions action) {
        String title = action.getTitle();
        Users user = null;
        for (Users forUser: database.getUsers()) {
            if (forUser.getUsername().equals(action.getUsername())) {
                user = forUser;
                break;
            }
        }
        assert user != null;
        StringBuilder stringOut = new StringBuilder();
        stringOut.append("error -> ").append(title).append(" is not seen");
        if (user.getHistory().containsKey(title)) {
            if (!user.getFavoriteMovies().contains(title)) {
                user.getFavoriteMovies().add(title);
                stringOut = new StringBuilder();
                stringOut.append("success -> ").append(title).append(" was added as favourite");
            } else {
                stringOut = new StringBuilder();
                stringOut.append("error -> ").append(title).append(" is already in favourite list");
            }
        }
        return stringOut.toString();
    }
}
