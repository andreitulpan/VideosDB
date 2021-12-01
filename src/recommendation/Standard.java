package recommendation;

import database.Database;
import entities.Shows;
import entities.Users;
import utils.Utils;

import java.util.ArrayList;

import static common.Constants.STANDARD_RECOM;
import static common.Constants.ERROR_APPLIED;
import static common.Constants.RESULT;

public final class Standard {
    private Standard() { }

    /**
     * Intoarce primul video nevizualizat de user.
     *
     * @param database baza de date
     * @param username numele user-ului
     * @return show-ul cautat
     */
    public static String getResult(final Database database, final String username) {
        // Creeaza string-ul ce va fi returnat
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(STANDARD_RECOM).append(ERROR_APPLIED);

        // Cauta user-ul in baza de date dupa username
        Users user = Utils.findUser(database, username);

        // Daca user-ul exista cauta show-ul
        if (user != null) {
            // Creeaza lista cu toate show-uri din baza de date
            ArrayList<Shows> shows = new ArrayList<>();
            shows.addAll(database.getMovies());
            shows.addAll(database.getSeries());

            // Intoarce primul show nevizualizat
            for (Shows show: shows) {
                if (!user.getHistory().containsKey(show.getTitle())) {
                    stringOut = new StringBuilder();
                    stringOut.append(STANDARD_RECOM).append(RESULT);
                    stringOut.append(show.getTitle());
                    break;
                }
            }
        }

        return stringOut.toString();
    }
}
