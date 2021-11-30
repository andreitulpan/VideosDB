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

public final class FilterDescription {
    private FilterDescription() { }

    public static String getResult(final Database database, final ActionInputData action) {
        ArrayList<Actors> actorsClone = new ArrayList<>();
        UtilsQueries.verifyFiltersActors(database, actorsClone, action.getFilters());
        sort(actorsClone, action.getSortType());
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(QUERY_RESULT);
        for (int i = 0; i < actorsClone.size(); i++) {
            stringOut.append(actorsClone.get(i).getName());
            if (i != actorsClone.size() - 1) {
                stringOut.append(COMMA);
            }
        }
        stringOut.append(RIGHT_PARANTH);
        return stringOut.toString();
    }

    private static void sort(final ArrayList<Actors> input, final String sortType) {
        input.sort((a1, a2) -> {
            if (sortType.equals(ASC)) {
                return a1.getName().compareToIgnoreCase(a2.getName());
            } else if (sortType.equals(DESC)) {
                return a2.getName().compareToIgnoreCase(a1.getName());
            }
            return 0;
        });
    }
}
