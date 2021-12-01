package user.commands;

import database.Database;
import entities.Movies;
import entities.Series;
import entities.Users;
import fileio.ActionInputData;
import utils.Utils;

import java.util.Map;

import static common.Constants.ERROR;
import static common.Constants.NOT_SEEN;
import static common.Constants.SUCCESS;
import static common.Constants.RATED;
import static common.Constants.BY;
import static common.Constants.ALREADY_RATED;

public final class UserRating {
    private UserRating() { }

    /**
     * User-ul ofera rating unui show, daca nu a
     * mai acordat anterior.
     *
     * @param database baza de date
     * @param action actiunea de executat
     * @return mesajul afisat
     */
    public static String setRating(final Database database, final ActionInputData action) {
        String username = action.getUsername();     // username
        String title = action.getTitle();           // titlu
        int season = action.getSeasonNumber();      // numarul sezonului
        double rating = action.getGrade();          // rating

        // Verific daca user-ul a vizionat show-ul
        Users user = Utils.findUser(database, username);

        // Creez mesajul de eroare
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(ERROR).append(title).append(NOT_SEEN);

        if (user == null) {
            return stringOut.toString();
        }

        // Se cauta filmul
        Movies movie = null;
        for (Movies forMovie: database.getMovies()) {
            if (forMovie.getTitle().equals(title)) {
                movie = forMovie;
                break;
            }
        }

        // Se cauta serialul
        Series serial = null;
        for (Series forSerial: database.getSeries()) {
            if (forSerial.getTitle().equals(title)) {
                serial = forSerial;
                break;
            }
        }

        // Cazul in care show-ul este film
        if (movie != null && serial == null) {
            if (user.getHistory().containsKey(movie.getTitle())) {

                // Verific daca user-ul nu a mai acordat rating anterior
                if (!(movie.getRatings().containsKey(user.getUsername()))) {
                    movie.getRatings().put(user.getUsername(), rating);
                    stringOut = new StringBuilder();
                    stringOut.append(SUCCESS).append(title).append(RATED);
                    stringOut.append(rating).append(BY).append(username);
                } else {
                    stringOut = new StringBuilder();
                    stringOut.append(ERROR).append(title).append(ALREADY_RATED);
                }
            }
        } else if (serial != null && movie == null) {   // Cazul in care show-ul este serial
            if (user.getHistory().containsKey(serial.getTitle())) {

                // Verific daca user-ul nu a mai acordat rating anterior
                if (season <= serial.getSeasons().size()) {
                    Map<String, Double> ratings = serial.getSeasons().get(season - 1).getRatings();
                    if (!(ratings.containsKey(username))) {
                        ratings.put(user.getUsername(), rating);
                        stringOut = new StringBuilder();
                        stringOut.append(SUCCESS).append(title).append(RATED);
                        stringOut.append(rating).append(BY).append(username);
                    } else {
                        stringOut = new StringBuilder();
                        stringOut.append(ERROR).append(title).append(ALREADY_RATED);
                    }
                }
            }
        }

        return stringOut.toString();
    }
}
