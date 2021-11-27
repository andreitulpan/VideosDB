package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Movies extends Shows {
    private final int duration;
    private Map<String, Double> ratings;


    public Movies(String title, int year, ArrayList<String> cast, ArrayList<String> genres, int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratings = new HashMap<>();
    }

    public int getDuration() {
        return duration;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(Map<String, Double> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "Movie{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                    + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
