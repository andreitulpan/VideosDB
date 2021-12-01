package queries.actors;

import database.Database;
import entities.Actors;
import fileio.ActionInputData;
import utils.Utils;

import java.util.ArrayList;

import static common.Constants.QUERY_RESULT;
import static common.Constants.RIGHT_PARANTH;
import static common.Constants.COMMA;
import static common.Constants.ASC;
import static common.Constants.DESC;

public final class FilterDescription {
    private FilterDescription() { }

    /**
     * Returneaza toti actorii a caror descriere
     * contine toate keyword-urile primite ca filtru.
     *
     * @param database baza de date
     * @param action actiunea de executat
     * @return string cu actorii
     */
    public static String getResult(final Database database, final ActionInputData action) {

        // Creez o lista noua, adaug doar actorii care trec
        // de toate filtrele si o sortez
        ArrayList<Actors> actorsClone = new ArrayList<>();
        Utils.verifyFiltersActors(database, actorsClone, action.getFilters());
        sort(actorsClone, action.getSortType());

        // Creez string-ul ce va fi returnat
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(QUERY_RESULT);

        // Adaug actorii din lista in string-ul ce va fi returnat
        for (int i = 0; i < actorsClone.size(); i++) {
            stringOut.append(actorsClone.get(i).getName());
            if (i != actorsClone.size() - 1) {
                stringOut.append(COMMA);
            }
        }
        stringOut.append(RIGHT_PARANTH);

        return stringOut.toString();
    }

    /**
     * Sorteaza lista lexicografic in functie de
     * tipul primit ca parametru ascendent/descendent.
     *
     * @param input lista de sortat
     * @param sortType tipul (asc/desc)
     */
    private static void sort(final ArrayList<Actors> input, final String sortType) {
        input.sort((a1, a2) -> {
            // Sortarea se face lexicografic
            if (sortType.equals(ASC)) {
                return a1.getName().compareToIgnoreCase(a2.getName());
            } else if (sortType.equals(DESC)) {
                return a2.getName().compareToIgnoreCase(a1.getName());
            }
            return 0;
        });
    }
}
