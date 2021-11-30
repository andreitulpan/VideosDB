package entities;

import java.util.HashMap;
import java.util.Map;

public final class Seasons {
    private final int currentSeason;
    private int duration;
    private Map<String, Double> ratings;

    public Seasons(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        ratings = new HashMap<>();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(final Map<String, Double> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }
}
