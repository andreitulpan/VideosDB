package commands;

import database.Database;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import queries.actors.Average;
import queries.actors.Awards;
import queries.actors.FilterDescription;
import queries.shows.QueryFavorite;
import queries.shows.Longest;
import queries.shows.MostViewed;
import queries.shows.Rating;
import queries.users.NumberOfRatings;
import recommendation.BestUnseen;
import recommendation.Popular;
import recommendation.Search;
import recommendation.Standard;
import user.commands.UserFavorite;
import user.commands.UserRating;
import user.commands.UserView;

import java.io.IOException;

@SuppressWarnings("unchecked")

public final class InputCommands {

    private InputCommands() { }

    /**
     * Verifica tipul actiunii primite ca input din fisierul JSON si apeleaza
     * pentru fiecare actiune in parte, metoda potrivita pentru tipul acesteia.
     *
     * @param  database  baza de date
     * @param  fileWriter transforma output-ul in format JSON
     * @param  arrayResult se adauga rezultatele actiunilor in format JSON
     */
    public static void actionsExecute(final Database database, final Writer fileWriter,
                                      final JSONArray arrayResult) {

        // Verific si execut fiecare actiune primita ca input
        for (ActionInputData action: database.getActions()) {
            String stringOut = "";  // Initializez string-ul de output gol
            switch (action.getActionType()) {

                case "command" -> {     // Verific daca este comanda
                    switch (action.getType()) {     // Verific tipul comenzii
                        case "favorite" ->  {
                            stringOut = UserFavorite.setFavorite(database, action);
                        }
                        case "rating" -> {
                            stringOut = UserRating.setRating(database, action);
                        }
                        case "view" -> {
                            stringOut = UserView.setView(database, action);
                        }
                        default -> { }
                    }
                }

                case "query" -> {   // Verific daca este query
                    switch (action.getCriteria()) {     // Verific tipul query-ului
                        case "average" -> {
                            stringOut = Average.getResult(database, action);
                        }
                        case "awards" -> {
                            stringOut = Awards.getResult(database, action);
                        }
                        case "filter_description" -> {
                            stringOut = FilterDescription.getResult(database, action);
                        }

                        case "ratings" -> {
                            stringOut = Rating.getResult(database, action);
                        }
                        case "favorite" -> {
                            stringOut = QueryFavorite.getResult(database, action);
                        }
                        case "longest" -> {
                            stringOut = Longest.getResult(database, action);
                        }
                        case "most_viewed" -> {
                            stringOut = MostViewed.getResult(database, action);
                        }

                        case "num_ratings" -> {
                            stringOut = NumberOfRatings.getResult(database, action);
                        }
                        default -> { }
                    }
                }

                case "recommendation" -> {  // Verific daca este recomandare
                    switch (action.getType()) {     // Verific tipul recomandarii
                        case "standard" -> {
                            stringOut = Standard.getResult(
                                        database, action.getUsername());
                        }
                        case "best_unseen" -> {
                            stringOut = BestUnseen.getResult(
                                        database, action.getUsername());
                        }
                        case "popular" -> {
                            stringOut = Popular.getResult(
                                        database, action.getUsername());
                        }
                        case "favorite" -> {
                            stringOut = recommendation.Favorite.getResult(
                                        database, action.getUsername());
                        }
                        case "search" -> {
                            stringOut = Search.getResult(
                                        database, action.getUsername(), action.getGenre());
                        }
                        default -> { }
                    }
                }

                default -> { }
            }

            // Scriu output-ul in format JSON si il adaug in arrayResult
            JSONObject output = null;
            try {
                output = fileWriter.writeFile(action.getActionId(), null, stringOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
            arrayResult.add(output);
        }
    }
}
