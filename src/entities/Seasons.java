package entities;

import java.util.HashMap;
import java.util.Map;

public class Seasons {
    private final int currentSeason;
    private int duration;
    private Map<String, Double> ratings;

    public Seasons(int currentSeason, int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        ratings = new HashMap<>();
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(Map<String, Double> ratings) {
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
