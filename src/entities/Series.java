package entities;

import entertainment.Season;

import java.util.ArrayList;

public class Series extends Shows {
    private final int numberOfSeasons;
    private final ArrayList<Seasons> seasons;

    public Series(String title, int year, ArrayList<String> cast, ArrayList<String> genres, int numberOfSeasons, ArrayList<Season> seasons) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = new ArrayList<Seasons>();
        int seasonNumber = 1;
        for (Season season: seasons) {
            Seasons dbSeason = new Seasons(seasonNumber, season.getDuration());
            this.seasons.add(dbSeason);
            seasonNumber++;
        }
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Seasons> getSeasons() {
        return seasons;
    }

    public double SeriesAverage() {
        double sum = 0;
        int count = 0;
        for (Seasons season: seasons) {
            if (season.getRatings().isEmpty())
                count++;
            for (Double value: season.getRatings().values()) {
                sum += value;
                count++;
            }
        }
        if (count == 0)
            return sum;
        setRating(sum / count);
        return sum / count;
    }

    public int getDuration() {
        int duration = 0;
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
