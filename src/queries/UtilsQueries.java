//package queries;
//
//import database.Database;
//import entities.Actors;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import static common.Constants.FILTER_AWARDS;
//import static common.Constants.FILTER_WORDS;
//
//public final class UtilsQueries {
//    private UtilsQueries() { }
//
//    /**
//     * Verifica filtrele pe actori, iar cei ce indeplinesc
//     * toate conditiile sunt adaugati in lista
//     *
//     * @param database baza de date
//     * @param actorsClone lista de actori
//     * @param filters listele de filtre
//     */
//    public static void verifyFiltersActors(final Database database,
//                                           final ArrayList<Actors> actorsClone,
//                                           final List<List<String>> filters) {
//        // Listele de filtre pentru actori
//        ArrayList<String> words = new ArrayList<>();
//        ArrayList<String> awards = new ArrayList<>();
//
//        // Se verifica daca listele de filtre nu sunt goale
//        if (filters.get(FILTER_WORDS) != null) {
//            words.addAll(filters.get(FILTER_WORDS));
//        }
//        if (filters.get(FILTER_AWARDS) != null) {
//            awards.addAll(filters.get(FILTER_AWARDS));
//        }
//
//        // Se parcurge lista de actori din baza de date
//        for (Actors actor : database.getActors()) {
//            int ok = 1;
//
//            // Se verifica filtrele pentru keyword-uri
//            for (String word : words) {
//                String description = actor.getCareerDescription();
//                Pattern pattern = Pattern.compile("[^A-Za-z0-9]" + word + "[^A-Za-z0-9]",
//                                    Pattern.CASE_INSENSITIVE);
//                Matcher matcher = pattern.matcher(description);
//                if (!matcher.find()) {
//                    ok = 0;
//                    break;
//                }
//            }
//
//            // Se verifica filtrele pentru premii
//            int total = actor.actorAwards(awards);
//            if (total == 0) {
//                ok = 0;
//            }
//
//            // Daca toate filtrele sunt ok, se adauga actorul
//            if (ok == 1) {
//                actorsClone.add(actor);
//            }
//        }
//    }
//}
