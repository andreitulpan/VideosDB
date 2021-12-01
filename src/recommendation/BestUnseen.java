package recommendation;

import database.Database;
import entities.Movies;
import entities.Series;
import entities.Shows;
import entities.Users;
import utils.Utils;

import java.util.ArrayList;

import static common.Constants.BEST_UNSEEN;
import static common.Constants.ERROR_APPLIED;
import static common.Constants.RESULT;

public final class BestUnseen {
    private BestUnseen() { }

    /**
     * Intoarce cel mai bun video nevizualizat.
     *
     * @param database baza de date
     * @param username numele user-ului
     * @return show-ul cautat
     */
    public static String getResult(final Database database, final String username) {
        // Creeaza string-ul ce va fi returnat
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(BEST_UNSEEN).append(ERROR_APPLIED);

        // Cauta user-ul in baza de date dupa username
        Users user = Utils.findUser(database, username);

        // Daca user-ul exista cauta show-ul
        if (user != null) {
            // Creeaza lista cu toate show-uri din baza de date
            ArrayList<Shows> shows = new ArrayList<>();
            shows.addAll(database.getMovies());
            shows.addAll(database.getSeries());
            // Sorteaza lista dupa rating
            sort(shows);

            // Intoarce primul show nevizualizat
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

    /**
     * Sorteaza show-urile in functie de rating.
     *
     * @param input lista de show-uri
     */
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
