package queries.users;

import database.Database;
import entities.Users;
import fileio.ActionInputData;

import java.util.ArrayList;

import static common.Constants.QUERY_RESULT;
import static common.Constants.RIGHT_PARANTH;
import static common.Constants.COMMA;
import static common.Constants.ASC;
import static common.Constants.DESC;

public final class NumberOfRatings {
    private NumberOfRatings() { }

    /**
     * Returneaza primii n useri sortati in
     * functie de numarul de rating-uri acordate
     *
     * @param database baza de date
     * @param action actiunea de executat
     * @return string cu userii
     */
    public static String getResult(final Database database, final ActionInputData action) {

        // Numarul de useri ce va fi returnat
        int n = action.getNumber();

        // Creez lista de useri si ii sterg pe cei care nu
        // au acordat rating niciodata, apoi sortez lista
        ArrayList<Users> usersClone = new ArrayList<>(database.getUsers());
        usersClone.removeIf(x -> (x.numberOfRatings(database) == 0));
        sort(usersClone, database, action.getSortType());

        // Creez string-ul ce va fi returnat
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(QUERY_RESULT);

        // Daca numarul este mai mare decat
        // size-ul, atunci ii returnez pe toti
        if (usersClone.size() < n) {
            n = usersClone.size();
        }
        // Adaug toti userii in string-ul ce va fi returnat
        for (int i = 0; i < n; i++) {
            stringOut.append(usersClone.get(i).getUsername());
            if (i != n - 1) {
                stringOut.append(COMMA);
            }
        }
        stringOut.append(RIGHT_PARANTH);

        return stringOut.toString();
    }

    /**
     * Sorteaza lista in functie de numarul de rating-uri acordate, apoi lexicografic
     * in functie de tipul primit ca parametru ascendent/descendent.
     *
     * @param input lista de sortat
     * @param sortType tipul (asc/desc)
     */
    private static void sort(final ArrayList<Users> input,
                             final Database database, final String sortType) {
        input.sort((user1, user2) -> {
            int comparator = 0;
            // Sortarea se face in functie de numarul de rating-uri acordate
            if (sortType.equals(ASC)) {
                comparator = user1.numberOfRatings(database) - user2.numberOfRatings(database);
            } else if (sortType.equals(DESC)) {
                comparator = user2.numberOfRatings(database) - user1.numberOfRatings(database);
            }
            if (comparator == 0) {
                // Sortarea se face lexicografic
                if (sortType.equals(ASC)) {
                    return user1.getUsername().compareToIgnoreCase(user2.getUsername());
                } else if (sortType.equals(DESC)) {
                    return user2.getUsername().compareToIgnoreCase(user1.getUsername());
                }
            }
            return comparator;
        });
    }
}
