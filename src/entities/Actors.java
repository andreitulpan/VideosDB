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

    /**
     * Calculeaza rating-ul actorului, parcurgand rating-urile
     * filmelor si serialelor in care acesta a jucat.
     *
     * @param database baza de date
     * @return rating-ul actorului
     */
    public double actorAverage(final Database database) {
        double sum = 0;
        double count = 0;

        // Parcurg toate show-urile in care a jucat
        for (String showTitle: filmography) {

            // Caut daca show-ul este de tip film si ii adaug rating-ul
            for (Movies movie: database.getMovies()) {
                if (movie.getTitle().equals(showTitle)) {
                    if (movie.movieAverage() != 0) {
                        sum += movie.movieAverage();
                        count++;
                    }
                    break;
                }
            }

            // Caut daca show-ul este de tip serial si ii adaug rating-ul
            for (Series serial: database.getSeries()) {
                if (serial.getTitle().equals(showTitle)) {
                    if (serial.seriesAverage() != 0) {
                        sum += serial.seriesAverage();
                        count++;
                    }
                    break;
                }
            }
        }

        // Daca nu s-au gasit show-uri sau au toate rating-ul 0,
        // returnez rating-ul 0 pentru actor
        if (count == 0) {
            return 0;
        }

        return sum / count;
    }

    /**
     * Verifica daca actorul detine toate premiile din lista primita
     *
     * @param searchedAwards lista cu premii
     * @return 0 - nu are toate premiile | 1 - are toate premiile
     */
    public Integer actorAwards(final ArrayList<String> searchedAwards) {
        int counter = 0;

        // Parcurg lista primita
        for (String award: searchedAwards) {

            // Numarul cate premii din lista detine
            if (awards.containsKey(Utils.stringToAwards(award))) {
                counter++;
            }
        }

        // Verific daca sunt toate premiile din lista
        if (counter != searchedAwards.size()) {
            return 0;
        }

        return 1;
    }

    /**
     * Calculeaza numarul total de premii detinute de actor
     *
     * @return  numarul de premii detinute
     */
    public Integer actorTotalAwards() {
        int total = 0;

        // Parcurg lista de premii si calculez suma acestora
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
