package recommendation;

import database.Database;
import entities.Shows;
import entities.Users;
import utils.Utils;

import java.util.ArrayList;

import static common.Constants.FAVORITE_RECOM;
import static common.Constants.ERROR_APPLIED;
import static common.Constants.PREMIUM;
import static common.Constants.RESULT;

public final class Favorite {
    private Favorite() { }

    /**
     * Intoarce cel mai favorit show nevizualizat.
     * Doar pentru useri premium!
     *
     * @param database baza de date
     * @param username numele user-ului
     * @return show-ul cautat
     */
    public static String getResult(final Database database, final String username) {
        // Creeaza string-ul ce va fi returnat
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(FAVORITE_RECOM).append(ERROR_APPLIED);

        // Cauta user-ul in baza de date dupa username
        Users user = Utils.findUser(database, username);

        // Daca user-ul exista si are premium cauta show-ul
        if (user != null && user.getSubscriptionType().equals(PREMIUM)) {
            // Creeaza lista cu toate show-uri din baza de date
            ArrayList<Shows> shows = new ArrayList<>();
            shows.addAll(database.getMovies());
            shows.addAll(database.getSeries());
            // Sorteaza lista dupa rating
            sort(database, shows);

            // Intoarce primul show nevizualizat
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

    /**
     * Sorteaza show-urile in functie de numarul
     * de adaugari in listele de favorite.
     *
     * @param input lista de show-uri
     */
    private static void sort(final Database database, final ArrayList<Shows> input) {
        input.sort((s1, s2) -> {
            int views1 = s1.favoritesCount(database);
            int views2 = s2.favoritesCount(database);
            return views2 - views1;
        });
    }
}
