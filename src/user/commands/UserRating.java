package user.commands;

import database.Database;
import entities.Movies;
import entities.Series;
import entities.Users;
import fileio.ActionInputData;

import java.util.Map;

import static common.Constants.ERROR;
import static common.Constants.NOT_SEEN;
import static common.Constants.SUCCESS;
import static common.Constants.RATED;
import static common.Constants.BY;
import static common.Constants.ALREADY_RATED;

public final class UserRating {
    private UserRating() { }

    public static String setRating(final Database database, final ActionInputData action) {
        String username = action.getUsername();
        String title = action.getTitle();
        int season = action.getSeasonNumber();
        double rating = action.getGrade();

        Users user = null;
        for (Users forUser: database.getUsers()) {
            if (forUser.getUsername().equals(action.getUsername())) {
                user = forUser;
                break;
            }
        }
        assert user != null;
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(ERROR).append(title).append(NOT_SEEN);

        Movies movie = null;
        for (Movies forMovie: database.getMovies()) {
            if (forMovie.getTitle().equals(title)) {
                movie = forMovie;
                break;
            }
        }

        Series serial = null;
        for (Series forSerial: database.getSeries()) {
            if (forSerial.getTitle().equals(title)) {
                serial = forSerial;
                break;
            }
        }

        if (movie != null && serial == null) {
            if (user.getHistory().containsKey(movie.getTitle())) {
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
        } else if (serial != null && movie == null) {
            if (user.getHistory().containsKey(serial.getTitle())) {
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
