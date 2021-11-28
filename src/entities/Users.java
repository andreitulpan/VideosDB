package entities;

import database.Database;

import java.util.ArrayList;
import java.util.Map;

public class Users {
    private final String username;
    private final String subscriptionType;
    private final Map<String, Integer> history;
    private final ArrayList<String> favoriteMovies;

    public Users(String username, String subscriptionType, Map<String, Integer> history, ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.history = history;
        this.favoriteMovies = favoriteMovies;
    }

    public String getUsername() {
        return username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public int NumberOfRatings(Database database) {
        int counter = 0;
        for (Movies movie: database.getMovies()) {
            if (movie.getRatings().containsKey(username)) {
                counter++;
            }
        }
        for (Series serial: database.getSeries()) {
            for (Seasons season: serial.getSeasons()) {
                if (season.getRatings().containsKey(username)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public String toString() {
        return "User{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
