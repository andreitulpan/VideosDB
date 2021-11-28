package queries.actors;

import database.Database;
import entities.Actors;
import queries.UtilsQueries;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FilterDescription {
    public FilterDescription() {}

    public String getResult(Database database, List<List<String>> filters, String sortType) {
        ArrayList<Actors> actorsClone = new ArrayList<>();
        UtilsQueries.VerifyFiltersActors(database, actorsClone, filters);
        sort(actorsClone, sortType);
//        if (sortType.equals("asc"))
//            sort_asc(actorsClone);
//        else if (sortType.equals("desc"))
//            sort_desc(actorsClone);

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

    private void sort(ArrayList<Actors> input, String sortType) {
        input.sort(new Comparator<Actors>() {
            @Override
            public int compare(Actors a1, Actors a2) {
                if (sortType.equals("asc"))
                    return a1.getName().compareToIgnoreCase(a2.getName());
                else if (sortType.equals("desc"))
                    return a2.getName().compareToIgnoreCase(a1.getName());
                return 0;
            }
        });
    }
}
