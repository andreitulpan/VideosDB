package queries.shows;

import database.Database;
import entities.Movies;
import entities.Series;
import entities.Shows;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MostViewed {
    public MostViewed() {}

    public String getResult(Database database, int n, List<List<String>> filters, String objectType, String sortType) {
        StringBuilder stringOut = new StringBuilder();
        stringOut.append("Query result: []");
        ArrayList<Shows> showsClone = new ArrayList<>();
        ArrayList<String> years = new ArrayList<>(filters.get(0));
        ArrayList<String> genres = new ArrayList<>(filters.get(1));
        if (objectType.equals("movies")) {
            for (Movies movie: database.getMovies()) {
                int ok = 0;
                String year = String.valueOf(movie.getYear());
                if (years.contains(year)) {
                    ok = 1;
                }
                int counter = 0;
                for (String genre: genres) {
                    if (movie.getGenres().contains(genre)) {
                        counter++;
                    }
                }
                if ((ok == 1 || filters.get(0).get(0) == null) && (counter == genres.size() || filters.get(1).get(0) == null) && movie.ViewsCount(database) != 0) {
                    showsClone.add(movie);
                }
            }
        }
        if (objectType.equals("shows")) {
            for (Series serial: database.getSeries()) {
                int ok = 0;
                String year = String.valueOf(serial.getYear());
                if (years.contains(year)) {
                    ok = 1;
                }
                int counter = 0;
                for (String genre: genres) {
                    if (serial.getGenres().contains(genre)) {
                        counter++;
                    }
                }
                if ((ok == 1 || filters.get(0).get(0) == null) && (counter == genres.size() || filters.get(1).get(0) == null) && serial.ViewsCount(database) != 0) {
                    showsClone.add(serial);
                }
            }
        }
        if (showsClone.size() < n)
            n = showsClone.size();
        sort(showsClone, database, sortType);
        stringOut = new StringBuilder();
        stringOut.append("Query result: [");
        for (int i = 0; i < n; i++) {
            stringOut.append(showsClone.get(i).getTitle());
            if (i != n - 1)
                stringOut.append(", ");
        }
        stringOut.append("]");
        return stringOut.toString();
    }

    private void sort(ArrayList<Shows> input, Database database, String sortType) {
        input.sort(new Comparator<>() {
            @Override
            public int compare(Shows s1, Shows s2) {
                int comparator = 0;
                if (sortType.equals("asc"))
                    comparator = s1.ViewsCount(database) - s2.ViewsCount(database);
                else if (sortType.equals("desc"))
                    comparator = s2.ViewsCount(database) - s1.ViewsCount(database);
                if (comparator == 0) {
                    if (sortType.equals("asc"))
                        return s1.getTitle().compareToIgnoreCase(s2.getTitle());
                    else if (sortType.equals("desc"))
                        return s2.getTitle().compareToIgnoreCase(s1.getTitle());
                } else {
                    return comparator;
                }
                return comparator;
            }
        });
    }
}
