package queries.shows;

import database.Database;
import entities.Movies;
import entities.Series;
import entities.Shows;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.List;

import static common.Constants.FILTER_GENRE;
import static common.Constants.FILTER_YEAR;
import static common.Constants.QUERY_RESULT;
import static common.Constants.RIGHT_PARANTH;
import static common.Constants.MOVIES;
import static common.Constants.SHOWS;
import static common.Constants.COMMA;
import static common.Constants.ASC;
import static common.Constants.DESC;

public final class QueryFavorite {
    private QueryFavorite() { }

    public static String getResult(final Database database, final ActionInputData action) {
        int n = action.getNumber();
        List<List<String>> filters = action.getFilters();
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(QUERY_RESULT).append(RIGHT_PARANTH);
        ArrayList<Shows> showsClone = new ArrayList<>();
        ArrayList<String> years = new ArrayList<>(filters.get(FILTER_YEAR));
        ArrayList<String> genres = new ArrayList<>(filters.get(FILTER_GENRE));
        if (action.getObjectType().equals(MOVIES)) {
            for (Movies movie : database.getMovies()) {
                int ok = 0;
                String year = String.valueOf(movie.getYear());
                if (years.contains(year)) {
                    ok = 1;
                }
                int counter = 0;
                for (String genre : genres) {
                    if (movie.getGenres().contains(genre)) {
                        counter++;
                    }
                }
                if ((ok == 1 || filters.get(FILTER_YEAR).get(0) == null)
                        && (counter == genres.size()
                        || filters.get(FILTER_GENRE).get(0) == null)
                        && movie.favoritesCount(database) != 0) {
                    showsClone.add(movie);
                }
            }
        }
        if (action.getObjectType().equals(SHOWS)) {
            for (Series serial : database.getSeries()) {
                int ok = 0;
                String year = String.valueOf(serial.getYear());
                if (years.contains(year)) {
                    ok = 1;
                }
                int counter = 0;
                for (String genre : genres) {
                    if (serial.getGenres().contains(genre)) {
                        counter++;
                    }
                }
                if ((ok == 1 || filters.get(FILTER_YEAR).get(0) == null)
                        && (counter == genres.size()
                        || filters.get(FILTER_GENRE).get(0) == null)
                        && serial.favoritesCount(database) != 0) {
                    showsClone.add(serial);
                }
            }
        }
        if (showsClone.size() < n) {
            n = showsClone.size();
        }
        sort(showsClone, database, action.getSortType());
        stringOut = new StringBuilder();
        stringOut.append(QUERY_RESULT);
        for (int i = 0; i < n; i++) {
            stringOut.append(showsClone.get(i).getTitle());
            if (i != n - 1) {
                stringOut.append(COMMA);
            }
        }
        stringOut.append(RIGHT_PARANTH);
        return stringOut.toString();
    }

    private static void sort(final ArrayList<Shows> input,
                             final Database database, final String sortType) {
        input.sort((s1, s2) -> {
            int comparator = 0;
            if (sortType.equals(ASC)) {
                comparator = s1.favoritesCount(database) - s2.favoritesCount(database);
            } else if (sortType.equals(DESC)) {
                comparator = s2.favoritesCount(database) - s1.favoritesCount(database);
            }
            if (comparator == 0) {
                if (sortType.equals(ASC)) {
                    return s1.getTitle().compareToIgnoreCase(s2.getTitle());
                } else if (sortType.equals(DESC)) {
                    return s2.getTitle().compareToIgnoreCase(s1.getTitle());
                }
            }
            return comparator;
        });
    }
}