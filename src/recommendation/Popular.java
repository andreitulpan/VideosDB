package recommendation;

import database.Database;
import entities.Shows;
import entities.Users;

import java.util.ArrayList;
import java.util.Comparator;

public final class Popular {
    private Popular() {}

    public static String getResult(Database database, String username) {
        StringBuilder stringOut = new StringBuilder();
        stringOut.append("PopularRecommendation cannot be applied!");
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
            ArrayList<String> genres = PopularGenres(database, shows);
            for (String genre: genres) {
                for (Shows show: shows) {
                    if (!user.getHistory().containsKey(show.getTitle()) &&
                        show.getGenres().contains(genre)) {
                        stringOut = new StringBuilder();
                        stringOut.append("PopularRecommendation result: ");
                        stringOut.append(show.getTitle());
                        break;
                    }
                }
            }
        }
        return stringOut.toString();
    }

    private static ArrayList<String> PopularGenres(Database database, ArrayList<Shows> shows) {
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

    private static int getGenreViews(Database database,ArrayList<Shows> shows, String genre) {
        int views = 0;
        for (Shows show: shows) {
            if (show.getGenres().contains(genre))
                views += show.ViewsCount(database);
        }
        return views;
    }

    private static void sort(Database database, ArrayList<String> input, ArrayList<Shows> shows) {
        input.sort(new Comparator<>() {
            @Override
            public int compare(String s1, String s2) {
                int views1 = getGenreViews(database, shows, s1);
                int views2 = getGenreViews(database, shows, s2);
                return views1 - views2;
            }
        });
    }
}
