package recommendation;

import database.Database;
import entities.Movies;
import entities.Series;
import entities.Shows;
import entities.Users;

import java.util.ArrayList;

import static common.Constants.SEARCH_RECOM;
import static common.Constants.ERROR_APPLIED;
import static common.Constants.PREMIUM;
import static common.Constants.RESULT;
import static common.Constants.COMMA;
import static common.Constants.RIGHT_PARANTH;
import static common.Constants.LEFT_PARANTH;

public final class Search {
    private Search() { }

    public static String getResult(final Database database,
                                   final String username, final String genre) {
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(SEARCH_RECOM).append(ERROR_APPLIED);
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
            shows.removeIf(x -> (!x.getGenres().contains(genre)));
            Users finalUser = user;
            shows.removeIf(x -> (finalUser.getHistory().containsKey(x.getTitle())));
            sort(shows);
            stringOut = new StringBuilder();
            stringOut.append(SEARCH_RECOM).append(RESULT).append(LEFT_PARANTH);
            for (int i = 0; i < shows.size(); i++) {
                stringOut.append(shows.get(i).getTitle());
                if (i != shows.size() - 1) {
                    stringOut.append(COMMA);
                }
            }
            stringOut.append(RIGHT_PARANTH);
            if (shows.size() == 0) {
                stringOut = new StringBuilder();
                stringOut.append(SEARCH_RECOM).append(ERROR_APPLIED);
            }
        }
        return stringOut.toString();
    }

    private static void sort(final ArrayList<Shows> input) {
        input.sort((s1, s2) -> {
            int comparator;
            double duration1 = 0, duration2 = 0;

            if (s1 instanceof Movies) {
                duration1 = ((Movies) s1).movieAverage();
            }
            if (s2 instanceof Movies) {
                duration2 = ((Movies) s2).movieAverage();
            }
            if (s1 instanceof Series) {
                duration1 = ((Series) s1).seriesAverage();
            }
            if (s2 instanceof Series) {
                duration2 = ((Series) s2).seriesAverage();
            }

            comparator = Double.compare(duration1, duration2);
            if (comparator == 0) {
                return s1.getTitle().compareToIgnoreCase(s2.getTitle());
            }

            return comparator;
        });
    }
}
