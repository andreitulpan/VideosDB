package entities;

import actor.ActorsAwards;
import database.Database;
import utils.Utils;

import java.util.ArrayList;
import java.util.Map;

public final class Actors {
    private final String name;
    private final String careerDescription;
    private final ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;

    public Actors(final String name, final String careerDescription,
                  final ArrayList<String> filmography, final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public double actorAverage(final Database database) {
        double sum = 0;
        double count = 0;
        for (String showTitle: filmography) {
            for (Movies movie: database.getMovies()) {
                if (movie.getTitle().equals(showTitle)) {
                    if (movie.movieAverage() != 0) {
                        sum += movie.movieAverage();
                        count++;
                    }
                }
            }
            for (Series serial: database.getSeries()) {
                if (serial.getTitle().equals(showTitle)) {
                    if (serial.seriesAverage() != 0) {
                        sum += serial.seriesAverage();
                        count++;
                    }
                }
            }
        }
        if (count == 0) {
            return sum;
        }
        return sum / count;
    }

    public Integer actorAwards(final ArrayList<String> searchedAwards) {
        int counter = 0;
        for (String award: searchedAwards) {
            if (awards.containsKey(Utils.stringToAwards(award))) {
                counter++;
            }
        }
        if (counter != searchedAwards.size()) {
            return 0;
        }
        return 1;
    }

    public Integer actorTotalAwards() {
        int total = 0;
        for (Integer value: awards.values()) {
                total += value;
        }
        return total;
    }

    @Override
    public String toString() {
        return "Actor{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}
