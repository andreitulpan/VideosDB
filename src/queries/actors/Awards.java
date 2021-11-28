package queries.actors;

import database.Database;
import entities.Actors;
import queries.UtilsQueries;

import java.util.*;

public class Awards {
    public Awards() {
    }

    public String getResult(Database database, List<List<String>> filters, String sortType) {
        ArrayList<Actors> actorsClone = new ArrayList<>();
        UtilsQueries.VerifyFiltersActors(database, actorsClone, filters);
        sort(actorsClone, (ArrayList<String>) filters.get(3), sortType);

        StringBuilder stringOut = new StringBuilder();
        stringOut.append("Query result: [");
        for (int i = 0; i < actorsClone.size(); i++) {
            stringOut.append(actorsClone.get(i).getName());
            if (i != actorsClone.size() - 1)
                stringOut.append(", ");
        }
        stringOut.append("]");
        return stringOut.toString();
    }

    private void sort(ArrayList<Actors> input, ArrayList<String> searchedAwards, String sortType) {
        input.sort(new Comparator<Actors>() {
            @Override
            public int compare(Actors a1, Actors a2) {
                int comparator = 0;
                if (sortType.equals("asc"))
                    comparator = a1.TotalActorAwards() - a2.TotalActorAwards();
                else if (sortType.equals("desc"))
                    comparator = a2.TotalActorAwards() - a1.TotalActorAwards();
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