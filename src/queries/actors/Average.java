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

public final class Average {
    private Average() { }

    /**
     * Returneaza primii n actori in functie de media
     * rating-urilor filmelor si serialelor in care au jucat
     *
     * @param database baza de date
     * @param action actiunea de executat
     * @return string cu actorii
     */
    public static String getResult(final Database database, final ActionInputData action) {
        // Numarul de actori de returnat
        int number = action.getNumber();

        // Creez o lista noua si adaug doar actorii care trec de toate filtrele
        ArrayList<Actors> actorsClone = new ArrayList<>();
        Utils.verifyFiltersActors(database, actorsClone, action.getFilters());

        // Ii elimin pe cei cu rating-ul 0
        actorsClone.removeIf(x -> (x.actorAverage(database) == 0));

        // Daca numarul este mai mare decat size-ul, atunci ii returnez pe toti
        if (actorsClone.size() < number) {
            number = actorsClone.size();
        }

        // Sortez lista de actori
        sort(actorsClone, database, action.getSortType());

        // Creez string-ul ce va fi returnat
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(QUERY_RESULT);

        // Adaug primii n actori in string-ul ce va fi returnat
        for (int i = 0; i < number; i++) {
            stringOut.append(actorsClone.get(i).getName());
            if (i != number - 1) {
                stringOut.append(COMMA);
            }
        }
        stringOut.append(RIGHT_PARANTH);

        return stringOut.toString();
    }

    /**
     * Sorteaza lista in functie de media rating-urilor, apoi lexicografic
     * in functie de tipul primit ca parametru ascendent/descendent.
     *
     * @param input lista de sortat
     * @param database baza de date
     * @param sortType tipul (asc/desc)
     */
    private static void sort(final ArrayList<Actors> input,
                             final Database database, final String sortType) {
        input.sort((a1, a2) -> {
            int comparator = 0;
            // Sortarea se face in functie de media rating-urilor
            if (sortType.equals(ASC)) {
                comparator = Double.compare(a1.actorAverage(database), a2.actorAverage(database));
            } else if (sortType.equals(DESC)) {
                comparator = Double.compare(a2.actorAverage(database), a1.actorAverage(database));
            }
            if (comparator == 0) {
                // Sortarea se face lexicografic
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
