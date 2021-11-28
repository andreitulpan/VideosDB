package queries;

import database.Database;
import entities.Actors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UtilsQueries {
    private UtilsQueries() {}

    public static void VerifyFiltersActors(Database database, ArrayList<Actors> actorsClone, List<List<String>> filters) {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> awards = new ArrayList<>();

        if (filters.get(2) != null)
            words.addAll(filters.get(2));

        if (filters.get(3) != null)
            awards.addAll(filters.get(3));

        for (Actors actor : database.getActors()) {
            int ok = 1;
            for (String word : words) {
                String description = actor.getCareerDescription();
                Pattern pattern = Pattern.compile("[^A-Za-z0-9]" + word + "[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(description);
                if (!matcher.find()) {
                    ok = 0;
                    break;
                }
            }
            int total = actor.ActorAwards(awards);
            if (total == 0)
                ok = 0;
            if (ok == 1) {
                actorsClone.add(actor);
            }
        }
    }
}
