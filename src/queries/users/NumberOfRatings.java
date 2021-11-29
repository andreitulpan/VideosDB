package queries.users;

import database.Database;
import entities.Users;

import java.util.ArrayList;
import java.util.Comparator;

public class NumberOfRatings {
    public NumberOfRatings() {}

    public String getResult(Database database, int n, String sortType) {
//        StringBuilder stringOut = new StringBuilder();
//        stringOut.append("Query result: []");
        ArrayList<Users> usersClone = new ArrayList<>(database.getUsers());
        usersClone.removeIf(x -> (x.NumberOfRatings(database) == 0));
        sort(usersClone, database, sortType);
        StringBuilder stringOut = new StringBuilder();
        stringOut.append("Query result: [");
        if (usersClone.size() < n)
            n = usersClone.size();
        for (int i = 0; i < n; i++) {
            stringOut.append(usersClone.get(i).getUsername());
            if (i != n - 1)
                stringOut.append(", ");
        }
        stringOut.append("]");
        return stringOut.toString();
    }

    private void sort(ArrayList<Users> input, Database database, String sortType) {
        input.sort(new Comparator<>() {
            @Override
            public int compare(Users user1, Users user2) {
                int comparator = 0;
                if (sortType.equals("asc"))
                    comparator = user1.NumberOfRatings(database) - user2.NumberOfRatings(database);
                else if (sortType.equals("desc"))
                    comparator = user2.NumberOfRatings(database) - user1.NumberOfRatings(database);
                if (comparator == 0) {
                    if (sortType.equals("asc"))
                        return user1.getUsername().compareToIgnoreCase(user2.getUsername());
                    else if (sortType.equals("desc"))
                        return user2.getUsername().compareToIgnoreCase(user1.getUsername());
                } else {
                    return comparator;
                }
                return comparator;
            }
        });
    }
}
