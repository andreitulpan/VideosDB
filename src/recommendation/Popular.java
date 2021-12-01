package recommendation;

import database.Database;
import entities.Shows;
import entities.Users;
import utils.Utils;

import java.util.ArrayList;

import static common.Constants.POPULAR_RECOM;
import static common.Constants.ERROR_APPLIED;
import static common.Constants.PREMIUM;
import static common.Constants.RESULT;

public final class Popular {
    private Popular() { }

    /**
     * Intoarce cel mai popular show nevizualizat.
     * Doar pentru useri premium!
     *
     * @param database baza de date
     * @param username numele user-ului
     * @return show-ul cautat
     */
    public static String getResult(final Database database, final String username) {
        // Creeaza string-ul ce va fi returnat
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(POPULAR_RECOM).append(ERROR_APPLIED);

        // Cauta user-ul in baza de date dupa username
        Users user = Utils.findUser(database, username);

        // Daca user-ul exista si are premium cauta show-ul
        if (user != null && user.getSubscriptionType().equals(PREMIUM)) {
            // Creeaza lista cu toate show-uri din baza de date
            ArrayList<Shows> shows = new ArrayList<>();
            shows.addAll(database.getMovies());
            shows.addAll(database.getSeries());

            // Intoarce primul show nevizualizat din cel mai popular gen
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

    /**
     * Intoarce lista cu genurile existe in show-uri.
     *
     * @param database baza de date
     * @param shows lista de show-uri
     * @return  lista de genuri
     */
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

        // Sortez lista de show-uri dupa numarul de vizualizari al genului
        sort(database, genres, shows);
        return genres;
    }

    /**
     * Intoarce numarul de vizionari al unui gen.
     *
     * @param database baza de date
     * @param shows lista de show-uri
     * @param genre genul show-ului
     * @return numarul de vizionari
     */
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

    /**
     * Sorteaza lista de genuri dupa vizualizari.
     *
     * @param database baza de date
     * @param input lista de genuri
     * @param shows lista de show-uri
     */
    private static void sort(final Database database,
                             final ArrayList<String> input, final ArrayList<Shows> shows) {
        input.sort((s1, s2) -> {
            int views1 = getGenreViews(database, shows, s1);
            int views2 = getGenreViews(database, shows, s2);
            return views1 - views2;
        });
    }
}
