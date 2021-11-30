package user.commands;

import database.Database;
import entities.Users;
import fileio.ActionInputData;

import static common.Constants.ERROR;
import static common.Constants.NOT_SEEN;
import static common.Constants.SUCCESS;
import static common.Constants.ALREADY_FAVOURITE;
import static common.Constants.ADDED_SUCCESS;


public final class UserFavorite {
    private UserFavorite() { }
    public static String setFavorite(final Database database, final ActionInputData action) {
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
        stringOut.append(ERROR).append(title).append(NOT_SEEN);
        if (user.getHistory().containsKey(title)) {
            if (!user.getFavoriteMovies().contains(title)) {
                user.getFavoriteMovies().add(title);
                stringOut = new StringBuilder();
                stringOut.append(SUCCESS).append(title).append(ADDED_SUCCESS);
            } else {
                stringOut = new StringBuilder();
                stringOut.append(ERROR).append(title).append(ALREADY_FAVOURITE);
            }
        }
        return stringOut.toString();
    }
}
