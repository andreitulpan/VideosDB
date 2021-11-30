package entities;

import database.Database;

import java.util.ArrayList;

public class Shows {
    private final String title;
    private final int year;
    private final ArrayList<String> cast;
    private final ArrayList<String> genres;
    private double rating;

    public Shows(final String title, final int year, final ArrayList<String> cast,
                 final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.rating = 0;
    }

    public final double getRating() {
        return rating;
    }

    public final void setRating(final double rating) {
        this.rating = rating;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public final int favoritesCount(final Database database) {
        int counter = 0;
        for (Users user: database.getUsers()) {
            if (user.getFavoriteMovies().contains(getTitle())) {
                counter++;
            }
        }
        return counter;
    }

    public final int viewsCount(final Database database) {
        int views = 0;
        for (Users user: database.getUsers()) {
            if (user.getHistory().containsKey(getTitle())) {
                views += user.getHistory().get(getTitle());
            }
        }
        return views;
    }
}
