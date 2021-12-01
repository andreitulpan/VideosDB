package entities;

import database.Database;

import java.util.ArrayList;

public abstract class Shows {
    private final String title;
    private final int year;
    private final ArrayList<String> cast;
    private final ArrayList<String> genres;

    public Shows(final String title, final int year, final ArrayList<String> cast,
                 final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
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

    /**
     * Calculeaza de cate ori a fost adaugat show-ul
     * la favorite de catre toti userii
     *
     * @param database baza de date
     * @return numarul de intrari in listele de favorite
     */
    public final int favoritesCount(final Database database) {
        int counter = 0;

        // Parcurg lista de show-uri favorite a fiecarui user
        // si verific daca are show-ul creunt
        for (Users user: database.getUsers()) {
            if (user.getFavoriteMovies().contains(getTitle())) {
                counter++;
            }
        }

        return counter;
    }

    /**
     * Calculeaza cate vizionari are show-ul
     *
     * @param database baza de date
     * @return numarul de vizionari
     */
    public final int viewsCount(final Database database) {
        int views = 0;

        // Parcurg istoricul fiecarui user si
        // verific daca a vizionat show-ul
        for (Users user: database.getUsers()) {
            if (user.getHistory().containsKey(getTitle())) {
                views += user.getHistory().get(getTitle());
            }
        }
        return views;
    }
}
