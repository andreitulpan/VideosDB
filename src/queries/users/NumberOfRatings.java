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

    public static String getResult(final Database database, final ActionInputData action) {
        int n = action.getNumber();
        ArrayList<Users> usersClone = new ArrayList<>(database.getUsers());
        usersClone.removeIf(x -> (x.numberOfRatings(database) == 0));
        sort(usersClone, database, action.getSortType());
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(QUERY_RESULT);
        if (usersClone.size() < n) {
            n = usersClone.size();
        }
        for (int i = 0; i < n; i++) {
            stringOut.append(usersClone.get(i).getUsername());
            if (i != n - 1) {
                stringOut.append(COMMA);
            }
        }
        stringOut.append(RIGHT_PARANTH);
        return stringOut.toString();
    }

    private static void sort(final ArrayList<Users> input,
                             final Database database, final String sortType) {
        input.sort((user1, user2) -> {
            int comparator = 0;
            if (sortType.equals(ASC)) {
                comparator = user1.numberOfRatings(database) - user2.numberOfRatings(database);
            } else if (sortType.equals(DESC)) {
                comparator = user2.numberOfRatings(database) - user1.numberOfRatings(database);
            }
            if (comparator == 0) {
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
