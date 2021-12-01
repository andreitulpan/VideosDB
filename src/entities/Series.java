package entities;

import entertainment.Season;

import java.util.ArrayList;

public final class Series extends Shows {
    private final int numberOfSeasons;
    private final ArrayList<Seasons> seasons;

    public Series(final String title, final int year,
                  final ArrayList<String> cast, final ArrayList<String> genres,
                  final int numberOfSeasons, final ArrayList<Season> seasons) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = new ArrayList<>();
        int seasonNumber = 1;

        // Parcurg lista de sezoane si le adaug in database
        for (Season season: seasons) {
            Seasons dbSeason = new Seasons(seasonNumber, season.getDuration());
            this.seasons.add(dbSeason);
            seasonNumber++;
        }
    }

    public ArrayList<Seasons> getSeasons() {
        return seasons;
    }

    /**
     * Calculeaza rating-ul serialului
     *
     * @return rating-ul serialului
     */
    public double seriesAverage() {
        double sum = 0;
        int count = 0;

        // Parcurg fiecare sezon al serialului
        for (Seasons season: seasons) {

            // Daca sezonul nu are rating, il adaug ca rating 0
            if (season.getRatings().isEmpty()) {
                count++;
            }

            // Parcurg map-ul sezonului si adun toate rating-urile
            for (Double value: season.getRatings().values()) {
                sum += value;
                count++;
            }
        }

        // Daca nu exista niciun rating, returnez 0
        if (count == 0) {
            return sum;
        }

        return sum / count;
    }

    /**
     * Calculeaza durata serialului
     *
     * @return durata serialului
     */

    public int calculateDuration() {
        int duration = 0;

        // Adauga durata fiecarui sezon al serialului
        for (Seasons season: seasons) {
            duration += season.getDuration();
        }
        return duration;
    }

    @Override
    public String toString() {
        return "Series{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
