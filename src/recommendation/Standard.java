package recommendation;

import database.Database;
import entities.Shows;
import entities.Users;

import java.util.ArrayList;

public final class Standard {
    private Standard() {}

    public static String getResult(Database database, String username) {
        StringBuilder stringOut = new StringBuilder();
        stringOut.append("StandardRecommendation cannot be applied!");
        Users user = null;
        for (Users forUser: database.getUsers()) {
            if (forUser.getUsername().equals(username)) {
                user = forUser;
                break;
            }
        }
        if (user != null ) {
            ArrayList<Shows> shows = new ArrayList<>();
            shows.addAll(database.getMovies());
            shows.addAll(database.getSeries());
            for (Shows show: shows) {
                if (!user.getHistory().containsKey(show.getTitle())) {
                    stringOut = new StringBuilder();
                    stringOut.append("StandardRecommendation result: ");
                    stringOut.append(show.getTitle());
                    break;
                }
            }
        }
        return stringOut.toString();
    }
}
