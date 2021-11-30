package recommendation;

import database.Database;
import entities.Shows;
import entities.Users;

import java.util.ArrayList;

import static common.Constants.FAVORITE_RECOM;
import static common.Constants.ERROR_APPLIED;
import static common.Constants.PREMIUM;
import static common.Constants.RESULT;

public final class Favorite {
    private Favorite() { }

    public static String getResult(final Database database, final String username) {
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(FAVORITE_RECOM).append(ERROR_APPLIED);
        Users user = null;
        for (Users forUser: database.getUsers()) {
            if (forUser.getUsername().equals(username)) {
                user = forUser;
                break;
            }
        }
        if (user != null && user.getSubscriptionType().equals(PREMIUM)) {
            ArrayList<Shows> shows = new ArrayList<>();
            shows.addAll(database.getMovies());
            shows.addAll(database.getSeries());
            sort(database, shows);
            for (Shows show: shows) {
                if (!user.getHistory().containsKey(show.getTitle())
                        && show.favoritesCount(database) != 0) {
                    stringOut = new StringBuilder();
                    stringOut.append(FAVORITE_RECOM).append(RESULT);
                    stringOut.append(show.getTitle());
                    break;
                }
            }
        }
        return stringOut.toString();
    }

    private static void sort(final Database database, final ArrayList<Shows> input) {
        input.sort((s1, s2) -> {
            int views1 = s1.favoritesCount(database);
            int views2 = s2.favoritesCount(database);
            return views2 - views1;
        });
    }
}
