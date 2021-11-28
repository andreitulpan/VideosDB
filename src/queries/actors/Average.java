package queries.actors;

import database.Database;
import entities.*;
import queries.UtilsQueries;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Average {
    public Average() {}

    public String getResult(Database database, int number, List<List<String>> filters, String sortType) {
        ArrayList<Actors> actorsClone = new ArrayList<>();
        UtilsQueries.VerifyFiltersActors(database, actorsClone, filters);
        actorsClone.removeIf(x -> (x.ActorAverage(database) == 0));
        if (actorsClone.size() < number)
            number = actorsClone.size();
        sort(actorsClone, database, sortType);
        StringBuilder stringOut = new StringBuilder();
        stringOut.append("Query result: [");
        for (int i = 0; i < number; i++) {
            stringOut.append(actorsClone.get(i).getName());
            if (i != number - 1)
                stringOut.append(", ");
        }
        stringOut.append("]");
        return stringOut.toString();
    }

    private void sort(ArrayList<Actors> input, Database database, String sortType) {
        input.sort(new Comparator<>() {
            @Override
            public int compare(Actors a1, Actors a2) {
                int comparator = 0;
                if (sortType.equals("asc"))
                    comparator = (int) (a1.ActorAverage(database) - a2.ActorAverage(database));
                else if (sortType.equals("desc"))
                    comparator = (int) (a2.ActorAverage(database) - a1.ActorAverage(database));
                if (comparator == 0) {
                    if (sortType.equals("asc"))
                        return a1.getName().compareToIgnoreCase(a2.getName());
                    else if (sortType.equals("desc"))
                        return a2.getName().compareToIgnoreCase(a1.getName());
                }
                return comparator;
            }
        });
    }
}
