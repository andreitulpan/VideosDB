package recommendation;

import database.Database;
import entities.Shows;
import entities.Users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class Favorite {
    private Favorite() {}

    public static String getResult(Database database, String username) {
        StringBuilder stringOut = new StringBuilder();
        stringOut.append("FavoriteRecommendation cannot be applied!");
        Users user = null;
        for (Users forUser: database.getUsers()) {
            if (forUser.getUsername().equals(username)) {
                user = forUser;
                break;
            }
        }
        if (user != null && user.getSubscriptionType().equals("PREMIUM")) {
            ArrayList<Shows> shows = new ArrayList<>();
            shows.addAll(database.getMovies());
            shows.addAll(database.getSeries());
            sort(database, shows);
            System.out.println(" \n\n");
            for (Shows show: shows) {
                if (!user.getHistory().containsKey(show.getTitle()) &&
                    show.FavoritesCount(database) != 0) {
                    stringOut = new StringBuilder();
                    stringOut.append("FavoriteRecommendation result: ");
                    stringOut.append(show.getTitle());
                    break;
                }
            }
        }
        return stringOut.toString();
    }

    private static void sort(Database database, ArrayList<Shows> input) {
        input.sort(new Comparator<>() {
            @Override
            public int compare(Shows s1, Shows s2) {
                int views1 = s1.FavoritesCount(database);
                int views2 = s2.FavoritesCount(database);
                return views2 - views1;
            }
        });
    }
}
