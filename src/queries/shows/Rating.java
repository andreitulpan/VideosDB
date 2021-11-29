package queries.shows;

import database.Database;
import entities.Movies;
import entities.Series;
import entities.Shows;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Rating {
    public Rating() {}

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
                if ((ok == 1 || filters.get(0).get(0) == null) && (counter == genres.size() || filters.get(1).get(0) == null) && movie.MovieAverage() != 0) {
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
                if ((ok == 1 || filters.get(0).get(0) == null) && (counter == genres.size() || filters.get(1).get(0) == null) && serial.SeriesAverage() != 0) {
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
                double duration1 = 0, duration2 = 0;
                if(s1 instanceof Movies) { duration1 = ((Movies) s1).MovieAverage();}
                if(s2 instanceof Movies) { duration2 = ((Movies) s2).MovieAverage();}
                if(s1 instanceof Series) { duration1 = ((Series) s1).SeriesAverage();}
                if(s2 instanceof Series) { duration2 = ((Series) s2).SeriesAverage();}
                if (sortType.equals("asc"))
                    comparator = Double.compare(duration1, duration2);
                else if (sortType.equals("desc"))
                    comparator = Double.compare(duration2, duration1);
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
