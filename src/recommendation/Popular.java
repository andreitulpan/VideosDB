package recommendation;

import database.Database;
import entities.Shows;
import entities.Users;

import java.util.ArrayList;

import static common.Constants.POPULAR_RECOM;
import static common.Constants.ERROR_APPLIED;
import static common.Constants.PREMIUM;
import static common.Constants.RESULT;

public final class Popular {
    private Popular() { }

    public static String getResult(final Database database, final String username) {
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(POPULAR_RECOM).append(ERROR_APPLIED);
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
            ArrayList<String> genres = popularGenres(database, shows);
            for (String genre: genres) {
                for (Shows show: shows) {
                    if (!user.getHistory().containsKey(show.getTitle())
                            && show.getGenres().contains(genre)) {
                        stringOut = new StringBuilder();
                        stringOut.append(POPULAR_RECOM).append(RESULT);
                        stringOut.append(show.getTitle());
                        break;
                    }
                }
            }
        }
        return stringOut.toString();
    }

    private static ArrayList<String> popularGenres(final Database database,
                                                   final ArrayList<Shows> shows) {
        ArrayList<String> genres = new ArrayList<>();
        for (Shows show: shows) {
            for (String genre: show.getGenres()) {
                if (!genres.contains(genre)) {
                    genres.add(genre);
                }
            }
        }
        sort(database, genres, shows);
        return genres;
    }

    private static int getGenreViews(final Database database,
                                     final ArrayList<Shows> shows, final String genre) {
        int views = 0;
        for (Shows show: shows) {
            if (show.getGenres().contains(genre)) {
                views += show.viewsCount(database);
            }
        }
        return views;
    }

    private static void sort(final Database database,
                             final ArrayList<String> input, final ArrayList<Shows> shows) {
        input.sort((s1, s2) -> {
            int views1 = getGenreViews(database, shows, s1);
            int views2 = getGenreViews(database, shows, s2);
            return views1 - views2;
        });
    }
}
