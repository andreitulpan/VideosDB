package recommendation;

import database.Database;
import entities.Movies;
import entities.Series;
import entities.Shows;
import entities.Users;

import java.util.ArrayList;
import java.util.Comparator;

public final class Search {
    private Search() {}

    public static String getResult(Database database, String username, String genre) {
        StringBuilder stringOut = new StringBuilder();
        stringOut.append("SearchRecommendation cannot be applied!");
        Users user = null;
        for (Users forUser: database.getUsers()) {
            if (forUser.getUsername().equals(username)) {
                user = forUser;
                break;
            }
        }
        if (user != null && user.getSubscriptionType().equals("PREMIUM")) {
            ArrayList<Shows> shows = new ArrayList<>();
            shows.addAll(database.getMovies());
            shows.addAll(database.getSeries());
            shows.removeIf(x -> (!x.getGenres().contains(genre)));
            Users finalUser = user;
            shows.removeIf(x -> (finalUser.getHistory().containsKey(x.getTitle())));
            sort(shows, database);
            stringOut = new StringBuilder();
            stringOut.append("SearchRecommendation result: [");
            for (int i = 0; i < shows.size(); i++) {
                stringOut.append(shows.get(i).getTitle());
                if (i != shows.size() - 1)
                    stringOut.append(", ");
            }
            stringOut.append("]");
            if (shows.size() == 0) {
                stringOut = new StringBuilder();
                stringOut.append("SearchRecommendation cannot be applied!");
            }
        }
        return stringOut.toString();
    }

    private static void sort(ArrayList<Shows> input, Database database) {
        input.sort(new Comparator<>() {
            @Override
            public int compare(Shows s1, Shows s2) {
                int comparator = 0;
                double duration1 = 0, duration2 = 0;
                if(s1 instanceof Movies) { duration1 = ((Movies) s1).MovieAverage();}
                if(s2 instanceof Movies) { duration2 = ((Movies) s2).MovieAverage();}
                if(s1 instanceof Series) { duration1 = ((Series) s1).SeriesAverage();}
                if(s2 instanceof Series) { duration2 = ((Series) s2).SeriesAverage();}
                comparator = Double.compare(duration1, duration2);
                if (comparator == 0) {
                    return s1.getTitle().compareToIgnoreCase(s2.getTitle());
                }
                return comparator;
            }
        });
    }
}
