package queries;

import database.Database;
import entities.Actors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static common.Constants.FILTER_AWARDS;
import static common.Constants.FILTER_WORDS;

public final class UtilsQueries {
    private UtilsQueries() { }

    public static void verifyFiltersActors(final Database database,
                                           final ArrayList<Actors> actorsClone,
                                           final List<List<String>> filters) {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> awards = new ArrayList<>();

        if (filters.get(FILTER_WORDS) != null) {
            words.addAll(filters.get(FILTER_WORDS));
        }

        if (filters.get(FILTER_AWARDS) != null) {
            awards.addAll(filters.get(FILTER_AWARDS));
        }

        for (Actors actor : database.getActors()) {
            int ok = 1;
            for (String word : words) {
                String description = actor.getCareerDescription();
                Pattern pattern = Pattern.compile("[^A-Za-z0-9]" + word + "[^A-Za-z0-9]",
                                    Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(description);
                if (!matcher.find()) {
                    ok = 0;
                    break;
                }
            }
            int total = actor.actorAwards(awards);
            if (total == 0) {
                ok = 0;
            }
            if (ok == 1) {
                actorsClone.add(actor);
            }
        }
    }
}
