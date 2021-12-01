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

public final class MostViewed {
    private MostViewed() { }

    /**
     * Returneaza primele n video-uri sortate in
     * functie de numarul de vizionari al acestora
     *
     * @param database baza de date
     * @param action actiunea de executat
     * @return string cu show-urile
     */
    public static String getResult(final Database database, final ActionInputData action) {

        // Numarul de filme ce va fi returnat
        int n = action.getNumber();

        // Creez lista de filtre
        List<List<String>> filters = action.getFilters();

        // Creez string-ul ce va fi returnat
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(QUERY_RESULT).append(RIGHT_PARANTH);

        // Creez lista de show-uri ce va fi returnata
        ArrayList<Shows> showsClone = new ArrayList<>();

        // Creez listele pentru filtre
        ArrayList<String> years = new ArrayList<>(filters.get(FILTER_YEAR));
        ArrayList<String> genres = new ArrayList<>(filters.get(FILTER_GENRE));

        // Daca show-ul este film si indeplineste filtrele il adaug in lista
        if (action.getObjectType().equals(MOVIES)) {
            for (Movies movie: database.getMovies()) {

                // Verific filtrele pentru ani
                int ok = 0;
                String year = String.valueOf(movie.getYear());
                if (years.contains(year)) {
                    ok = 1;
                }

                // Verific filtrele pentru genuri
                int counter = 0;
                for (String genre: genres) {
                    if (movie.getGenres().contains(genre)) {
                        counter++;
                    }
                }

                // Daca a trecut de toate filtrele il adaug in lista
                if ((ok == 1 || filters.get(FILTER_YEAR).get(0) == null)
                        && (counter == genres.size()
                        || filters.get(FILTER_GENRE).get(0) == null)
                        && movie.viewsCount(database) != 0) {
                    showsClone.add(movie);
                }
            }
        }

        // Daca show-ul este serial si indeplineste filtrele il adaug in lista
        if (action.getObjectType().equals(SHOWS)) {
            for (Series serial: database.getSeries()) {

                // Verific filtrele pentru ani
                int ok = 0;
                String year = String.valueOf(serial.getYear());
                if (years.contains(year)) {
                    ok = 1;
                }

                // Verific filtrele pentru genuri
                int counter = 0;
                for (String genre: genres) {
                    if (serial.getGenres().contains(genre)) {
                        counter++;
                    }
                }

                // Daca a trecut de toate filtrele il adaug in lista
                if ((ok == 1 || filters.get(FILTER_YEAR).get(0) == null)
                        && (counter == genres.size()
                        || filters.get(FILTER_GENRE).get(0) == null)
                        && serial.viewsCount(database) != 0) {
                    showsClone.add(serial);
                }
            }
        }

        // Daca numarul este mai mare decat
        // size-ul, atunci le returnez pe toate
        if (showsClone.size() < n) {
            n = showsClone.size();
        }

        // Sortez lista
        sort(showsClone, database, action.getSortType());

        // Adaug toate show-urile in string-ul ce va fi returnat
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

    /**
     * Sorteaza lista in functie de numarul de vizionari, apoi lexicografic
     * in functie de tipul primit ca parametru ascendent/descendent.
     *
     * @param input lista de sortat
     * @param sortType tipul (asc/desc)
     */
    private static void sort(final ArrayList<Shows> input,
                             final Database database, final String sortType) {
        input.sort((s1, s2) -> {
            int comparator = 0;
            // Sortarea se face in functie de numarul de vizionari
            if (sortType.equals(ASC)) {
                comparator = s1.viewsCount(database) - s2.viewsCount(database);
            } else if (sortType.equals(DESC)) {
                comparator = s2.viewsCount(database) - s1.viewsCount(database);
            }
            if (comparator == 0) {
                // Sortarea se face lexicografic
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
