package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Movies extends Shows {
    private final int duration;
    private Map<String, Double> ratings;

    public Movies(final String title, final int year, final ArrayList<String> cast,
                  final ArrayList<String> genres, final int duration) {
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

    public void setRatings(final Map<String, Double> ratings) {
        this.ratings = ratings;
    }

    /**
     * Calculeaza rating-ul filmului
     *
     * @return rating-ul filmului
     */
    public double movieAverage() {
        // Daca nu a primit rating returnez 0
        if (ratings.isEmpty()) {
            return 0;
        }

        double sum = 0;
        int count = 0;

        // Parcurg map-ul si adun toate rating-urile
        for (Double value: ratings.values()) {
            if (value != 0) {
                sum += value;
                count++;
            }
        }

        // Returnez media rating-urilor
        return sum / count;
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
