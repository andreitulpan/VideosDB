package recommendation;

import database.Database;
import entities.Movies;
import entities.Series;
import entities.Shows;
import entities.Users;

import java.util.ArrayList;

import static common.Constants.BEST_UNSEEN;
import static common.Constants.ERROR_APPLIED;
import static common.Constants.RESULT;

public final class BestUnseen {
    private BestUnseen() { }

    public static String getResult(final Database database, final String username) {
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(BEST_UNSEEN).append(ERROR_APPLIED);
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
            sort(shows);
            for (Shows show: shows) {
                if (!user.getHistory().containsKey(show.getTitle())) {
                    stringOut = new StringBuilder();
                    stringOut.append(BEST_UNSEEN).append(RESULT);
                    stringOut.append(show.getTitle());
                    break;
                }
            }
        }
        return stringOut.toString();
    }

    private static void sort(final ArrayList<Shows> input) {
        input.sort((s1, s2) -> {
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

            return Double.compare(duration2, duration1);
        });
    }
}
