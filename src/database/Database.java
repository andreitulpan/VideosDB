package database;

import entities.Movies;
import entities.Series;
import entities.Actors;
import entities.Users;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Input;

import java.util.ArrayList;
import java.util.List;

public final class Database {
    private static Database instance;
    private List<Movies> movies;
    private List<Series> series;
    private List<Actors> actors;
    private List<Users> users;
    private List<ActionInputData> actions;

    private Database() {
        this.movies = null;
        this.series = null;
        this.actors = null;
        this.users = null;
        this.actions = null;
    }

    /**
     * Intoarce instanta bazei de date.
     * Baza de date a fost implementata
     * utilizand lazy singleton.
     * @return instanta bazei de date
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public List<ActionInputData> getActions() {
        return actions;
    }

    /**
     * Initializeaza lista din baza de actiuni cu cele primite la input.
     *
     * @param inputActions lista de actiuni
     */
    public void initActions(final List<ActionInputData> inputActions) {
        this.actions = new ArrayList<>(inputActions);
    }

    public List<Movies> getMovies() {
        return movies;
    }

    /**
     * Initializeaza lista din baza de date cu filmele primite la input.
     *
     * @param inputMovies input-ul pentru filme
     */
    private void initMovies(final List<MovieInputData> inputMovies) {
        for (MovieInputData movie: inputMovies) {
            Movies dbMovie = new Movies(movie.getTitle(), movie.getYear(),
                                        movie.getCast(), movie.getGenres(), movie.getDuration());
            this.movies.add(dbMovie);
        }
    }

    public List<Series> getSeries() {
        return series;
    }

    /**
     * Initializeaza lista din baza de date cu serialele primite la input.
     *
     * @param inputSeries input-ul pentru seriale
     */
    private void initSeries(final List<SerialInputData> inputSeries) {
        for (SerialInputData serial: inputSeries) {
            Series dbSerial = new Series(serial.getTitle(), serial.getYear(),
                                        serial.getCast(), serial.getGenres(),
                                        serial.getNumberSeason(), serial.getSeasons());
            this.series.add(dbSerial);
        }
    }

    public List<Actors> getActors() {
        return actors;
    }

    /**
     * Initializeaza lista din baza de date cu actorii primiti la input.
     *
     * @param inputActors input-ul pentru actori
     */
    private void initActors(final List<ActorInputData> inputActors) {
        for (ActorInputData actor: inputActors) {
            Actors dbActor = new Actors(actor.getName(), actor.getCareerDescription(),
                                        actor.getFilmography(), actor.getAwards());
            this.actors.add(dbActor);
        }
    }

    public List<Users> getUsers() {
        return users;
    }

    /**
     * Initializeaza lista din baza de date cu userii primiti la input.
     *
     * @param inputUsers input-ul pentru useri
     */
    private void initUsers(final List<UserInputData> inputUsers) {
        for (UserInputData user: inputUsers) {
            Users dbUser = new Users(user.getUsername(), user.getSubscriptionType(),
                                    user.getHistory(), user.getFavoriteMovies());
            this.users.add(dbUser);
        }
    }

    /**
     * Initializeaza baza de date cu input-ul primit
     *
     * @param input input-ul primit
     */
    public void initDatabase(final Input input) {
        this.movies = new ArrayList<>();
        this.series = new ArrayList<>();
        this.actors = new ArrayList<>();
        this.users = new ArrayList<>();
        initMovies(input.getMovies());
        initSeries(input.getSerials());
        initActors(input.getActors());
        initUsers(input.getUsers());
        initActions(input.getCommands());
    }
}
