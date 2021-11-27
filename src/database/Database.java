package database;

import entities.Movies;
import entities.Series;
import entities.Actors;
import entities.Users;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private final List<Movies> movies;
    private final List<Series> series;
    private final List<Actors> actors;
    private final List<Users> users;

    public Database() {
        this.movies = new ArrayList<>();
        this.series = new ArrayList<>();
        this.actors = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public List<Movies> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieInputData> movies) {
        for (MovieInputData movie: movies) {
            Movies dbMovie = new Movies(movie.getTitle(), movie.getYear(), movie.getCast(), movie.getGenres(), movie.getDuration());
            this.movies.add(dbMovie);
        }
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<SerialInputData> series) {
        for (SerialInputData serial: series) {
            Series dbSerial = new Series(serial.getTitle(), serial.getYear(), serial.getCast(), serial.getGenres(), serial.getNumberSeason(), serial.getSeasons());
            this.series.add(dbSerial);
        }
    }

    public List<Actors> getActors() {
        return actors;
    }

    public void setActors(List<ActorInputData> actors) {
        for (ActorInputData actor: actors) {
            Actors dbActor = new Actors(actor.getName(), actor.getCareerDescription(), actor.getFilmography(), actor.getAwards());
            this.actors.add(dbActor);
        }
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<UserInputData> users) {
        for (UserInputData user: users) {
            Users dbUser = new Users(user.getUsername(), user.getSubscriptionType(), user.getHistory(), user.getFavoriteMovies());
            this.users.add(dbUser);
        }
    }
}


