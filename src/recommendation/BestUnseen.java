package recommendation;

import database.Database;
import entities.Movies;
import entities.Series;
import entities.Shows;
import entities.Users;

import java.util.ArrayList;
import java.util.Comparator;

public final class BestUnseen {
    private BestUnseen() {}

    public static String getResult(Database database, String username) {
        StringBuilder stringOut = new StringBuilder();
        stringOut.append("BestRatedUnseenRecommendation cannot be applied!");
        Users user = null;
        for (Users forUser: database.getUsers()) {
            if (forUser.getUsername().equals(username)) {
                user = forUser;
                break;
            }
        }
        if (user != null ) {
            ArrayList<Shows> shows = new ArrayList<>();
            shows.addAll(database.getMovies());
            shows.addAll(database.getSeries());
            sort(shows, database);
            for (Shows show: shows) {
                if (!user.getHistory().containsKey(show.getTitle())) {
                    stringOut = new StringBuilder();
                    stringOut.append("BestRatedUnseenRecommendation result: ");
                    stringOut.append(show.getTitle());
                    break;
                }
            }
        }
        return stringOut.toString();
    }

    private static void sort(ArrayList<Shows> input, Database database) {
        input.sort(new Comparator<>() {
            @Override
            public int compare(Shows s1, Shows s2) {
                double duration1 = 0, duration2 = 0;
                if(s1 instanceof Movies) { duration1 = ((Movies) s1).MovieAverage();}
                if(s2 instanceof Movies) { duration2 = ((Movies) s2).MovieAverage();}
                if(s1 instanceof Series) { duration1 = ((Series) s1).SeriesAverage();}
                if(s2 instanceof Series) { duration2 = ((Series) s2).SeriesAverage();}
                return Double.compare(duration2, duration1);
            }
        });
    }
}
