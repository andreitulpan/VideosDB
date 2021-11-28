package entities;

import database.Database;

import java.util.ArrayList;

public class Shows {
    private final String title;
    private final int year;
    private final ArrayList<String> cast;
    private final ArrayList<String> genres;
    private double rating;

    public Shows(String title, int year, ArrayList<String> cast, ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.rating = 0;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public int FavoritesCount(Database database) {
        int counter = 0;
        for (Users user: database.getUsers()) {
            if (user.getFavoriteMovies().contains(getTitle())) {
                counter++;
            }
        }
        return counter;
    }

    public int ViewsCount(Database database) {
        int views = 0;
        for (Users user: database.getUsers()) {
            if (user.getHistory().containsKey(getTitle())) {
                views += user.getHistory().get(getTitle());
            }
        }
        return views;
    }
}