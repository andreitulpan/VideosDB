package recommendation;

import database.Database;
import entities.Movies;
import entities.Series;
import entities.Shows;
import entities.Users;
import utils.Utils;

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

    /**
     * Intoarce toate show-urile nevizualizate
     * de user dintr-un anumit gen.
     *
     * @param database baza de date
     * @param username numele user-ului
     * @return show-urile cautate
     */
    public static String getResult(final Database database,
                                   final String username, final String genre) {
        // Creeaza string-ul ce va fi returnat
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(SEARCH_RECOM).append(ERROR_APPLIED);

        // Cauta user-ul in baza de date dupa username
        Users user = Utils.findUser(database, username);

        // Daca user-ul exista si are premium cauta show-ul
        if (user != null && user.getSubscriptionType().equals(PREMIUM)) {
            // Creeaza lista cu toate show-uri din baza de date
            ArrayList<Shows> shows = new ArrayList<>();
            shows.addAll(database.getMovies());
            shows.addAll(database.getSeries());

            // Sterge show-urile care nu contin gen-ul primit ca filtru
            shows.removeIf(x -> (!x.getGenres().contains(genre)));

            // Cauta user-ul in baza de date dupa username
            Users finalUser = user;
            shows.removeIf(x -> (finalUser.getHistory().containsKey(x.getTitle())));

            // Sorteaza lista de show-uri dupa rating
            sort(shows);

            // Intoarce lista de show-uri nevizualizate
            stringOut = new StringBuilder();
            stringOut.append(SEARCH_RECOM).append(RESULT).append(LEFT_PARANTH);
            for (int i = 0; i < shows.size(); i++) {
                stringOut.append(shows.get(i).getTitle());
                if (i != shows.size() - 1) {
                    stringOut.append(COMMA);
                }
            }
            stringOut.append(RIGHT_PARANTH);

            // Daca nu exista show-uri afiseaza un mesaj de eroare
            if (shows.size() == 0) {
                stringOut = new StringBuilder();
                stringOut.append(SEARCH_RECOM).append(ERROR_APPLIED);
            }
        }
        return stringOut.toString();
    }

    /**
     *  Sorteaza lista de show-uri dupa rating-uri.
     *
      * @param input lista de show-uri
     */
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
