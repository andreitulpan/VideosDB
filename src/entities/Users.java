package entities;

import database.Database;

import java.util.ArrayList;
import java.util.Map;

public final class Users {
    private final String username;
    private final String subscriptionType;
    private final Map<String, Integer> history;
    private final ArrayList<String> favoriteMovies;

    public Users(final String username, final String subscriptionType,
                 final Map<String, Integer> history, final ArrayList<String> favoriteMovies) {
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

    /**
     * Calculeaza numarul de filme show-uri care
     * au primit rating de la user-ul curent
     *
     * @param database baza de date
     * @return numarul de rating-uri acordate de user
     */
    public int numberOfRatings(final Database database) {
        int counter = 0;

        // Numar pentru cate filme user-ul a acordat rating
        for (Movies movie: database.getMovies()) {
            if (movie.getRatings().containsKey(username)) {
                counter++;
            }
        }

        // Numar pentru cate seriale user-ul a acordat rating
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
