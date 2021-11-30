package recommendation;

import database.Database;
import entities.Shows;
import entities.Users;

import java.util.ArrayList;

import static common.Constants.STANDARD_RECOM;
import static common.Constants.ERROR_APPLIED;
import static common.Constants.RESULT;

public final class Standard {
    private Standard() { }

    public static String getResult(final Database database, final String username) {
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(STANDARD_RECOM).append(ERROR_APPLIED);
        Users user = null;
        for (Users forUser: database.getUsers()) {
            if (forUser.getUsername().equals(username)) {
                user = forUser;
                break;
            }
        }
        if (user != null) {
            ArrayList<Shows> shows = new ArrayList<>();
            shows.addAll(database.getMovies());
            shows.addAll(database.getSeries());
            for (Shows show: shows) {
                if (!user.getHistory().containsKey(show.getTitle())) {
                    stringOut = new StringBuilder();
                    stringOut.append(STANDARD_RECOM).append(RESULT);
                    stringOut.append(show.getTitle());
                    break;
                }
            }
        }
        return stringOut.toString();
    }
}
