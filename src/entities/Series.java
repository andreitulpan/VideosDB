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
