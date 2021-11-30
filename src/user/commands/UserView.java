package user.commands;

import database.Database;
import entities.Users;
import fileio.ActionInputData;

import static common.Constants.SUCCESS;
import static common.Constants.VIEWED_SUCCESS;

public final class UserView {
    private UserView() { }

    public static String setView(final Database database, final ActionInputData action) {
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
        if (user.getHistory().containsKey(title)) {
            user.getHistory().put(title, user.getHistory().get(title) + 1);
        } else {
            user.getHistory().put(title, 1);
        }
        stringOut.append(SUCCESS).append(title).append(VIEWED_SUCCESS);
        stringOut.append(user.getHistory().get(title));
        return stringOut.toString();
    }
}
