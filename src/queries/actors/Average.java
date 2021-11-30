package queries.actors;

import database.Database;
import entities.Actors;
import fileio.ActionInputData;
import queries.UtilsQueries;

import java.util.ArrayList;

import static common.Constants.QUERY_RESULT;
import static common.Constants.RIGHT_PARANTH;
import static common.Constants.COMMA;
import static common.Constants.ASC;
import static common.Constants.DESC;

public final class Average {
    private Average() { }

    public static String getResult(final Database database, final ActionInputData action) {
        int number = action.getNumber();
        ArrayList<Actors> actorsClone = new ArrayList<>();
        UtilsQueries.verifyFiltersActors(database, actorsClone, action.getFilters());
        actorsClone.removeIf(x -> (x.actorAverage(database) == 0));
        if (actorsClone.size() < number) {
            number = actorsClone.size();
        }
        sort(actorsClone, database, action.getSortType());
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(QUERY_RESULT);
        for (int i = 0; i < number; i++) {
            stringOut.append(actorsClone.get(i).getName());
            if (i != number - 1) {
                stringOut.append(COMMA);
            }
        }
        stringOut.append(RIGHT_PARANTH);
        return stringOut.toString();
    }

    private static void sort(final ArrayList<Actors> input,
                             final Database database, final String sortType) {
        input.sort((a1, a2) -> {
            int comparator = 0;
            if (sortType.equals(ASC)) {
                comparator = Double.compare(a1.actorAverage(database), a2.actorAverage(database));
            } else if (sortType.equals(DESC)) {
                comparator = Double.compare(a2.actorAverage(database), a1.actorAverage(database));
            }
            if (comparator == 0) {
                if (sortType.equals(ASC)) {
                    return a1.getName().compareToIgnoreCase(a2.getName());
                } else if (sortType.equals(DESC)) {
                    return a2.getName().compareToIgnoreCase(a1.getName());
                }
            }
            return comparator;
        });
    }
}
