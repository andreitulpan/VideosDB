package userCommands;

import database.Database;
import entities.Movies;
import entities.Series;
import entities.Users;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public class userRating {
//    public Rating(Users user, String title, double rating, Database database) {
//        if (user.getHistory().containsKey(title)) {
//            for (Movies movie: database.getMovies()) {
//                if (movie.getTitle().equals(title) && !(movie.getRatings().containsKey(user.getUsername()))) {
//                    movie.getRatings().put(user.getUsername(), rating);
//                }
//            }
//        }
//    }
    public userRating() {}

    public String setRating(Database database, String username, String title, int season, double rating) {
        Users user = null;
        for (Users forUser: database.getUsers()) {
            if (forUser.getUsername().equals(username)) {
                user = forUser;
                break;
            }
        }
        assert user != null;
        StringBuilder stringOut = new StringBuilder();
        stringOut.append("error -> ").append(title).append(" is not seen");
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
                if (!(movie.getRatings().containsKey(user.getUsername()))){
                    movie.getRatings().put(user.getUsername(), rating);
                    stringOut = new StringBuilder();
                    stringOut.append("success -> ").append(title).append(" was rated with ").append(rating).append(" by ").append(username);
                } else {
                    stringOut = new StringBuilder();
                    stringOut.append("error -> ").append(title).append(" has been already rated");
                }
            }
        } else if (serial != null && movie == null) {
            if (user.getHistory().containsKey(serial.getTitle())) {
                if (season <= serial.getSeasons().size()) {
                    if (!(serial.getSeasons().get(season - 1).getRatings().containsKey(user.getUsername()))) {
                        serial.getSeasons().get(season - 1).getRatings().put(user.getUsername(), rating);
                        stringOut = new StringBuilder();
                        stringOut.append("success -> ").append(title).append(" was rated with ").append(rating).append(" by ").append(username);
                    } else {
                        stringOut = new StringBuilder();
                        stringOut.append("error -> ").append(title).append(" has been already rated");
                    }
                }
            }
        }
        return stringOut.toString();
    }

//    public void getRating(Users user, Movies movie, double rating) {
//        if (user.getHistory().containsKey(movie.getTitle())) {
//            if (!(movie.getRatings().containsKey(user.getUsername()))){
//                movie.getRatings().put(user.getUsername(), rating);
//            }
//        }
//    }
//
//    public void getRating(Users user, Series serial, int season, double rating) {
//        if (user.getHistory().containsKey(serial.getTitle())) {
//            if (season <= serial.getSeasons().size()) { // ???????????
//                if (!(serial.getSeasons().get(season - 1).getRatings().containsKey(user.getUsername()))) {
//                    serial.getSeasons().get(season - 1).getRatings().put(user.getUsername(), rating);
//                }
//            }
//        }
//    }

}