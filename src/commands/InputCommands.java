package commands;

import database.Database;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import queries.actors.Average;
import queries.actors.Awards;
import queries.actors.FilterDescription;
import queries.shows.Favorite;
import queries.shows.Longest;
import queries.shows.MostViewed;
import queries.shows.Rating;
import queries.users.NumberOfRatings;
import userCommands.userFavorite;
import userCommands.userRating;
import userCommands.userView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputCommands {
    private final List<Actions> actionsList;
    private Database database;
    private Writer fileWriter;
    private JSONArray arrayResult;

    public InputCommands() {
        this.actionsList = new ArrayList<>();
        this.database = null;
    }
    public List<Actions> getActionsList() {
        return actionsList;
    }

    public void setActionsList(List<ActionInputData> actionsList) {
        for (ActionInputData action: actionsList) {
            switch (action.getActionType()) {
                case "recommendation" -> {
                    Actions thisAction = new Actions(action.getActionId(), action.getActionType(),
                            action.getType(), action.getUsername(), action.getGenre());
                    this.actionsList.add(thisAction);
                }
                case "query" -> {
                    Actions thisAction = new Actions(action.getActionId(), action.getActionType(),
                            action.getObjectType(), action.getSortType(), action.getCriteria(), action.getNumber(), action.getFilters());
                    this.actionsList.add(thisAction);
                }
                case "command" -> {
                    Actions thisAction = new Actions(action.getActionId(), action.getActionType(), action.getType(), action.getUsername(),
                            action.getTitle(), action.getGrade(), action.getSeasonNumber());
                    this.actionsList.add(thisAction);
                }
            }
        }
    }

    public void getData(Database database, Writer fileWriter ,JSONArray arrayResult) {
        this.database = database;
        this.fileWriter = fileWriter;
        this.arrayResult = arrayResult;
    }

    public void ActionsExecute() {
        for (Actions action: actionsList) {
            String stringOut = "";
            switch(action.getActionType()) {

                case "command" -> {
                    switch(action.getType()) {
                        case "favorite" -> stringOut = new userFavorite().setFavorite(database, action);
                        case "rating" -> stringOut = new userRating().setRating(database, action);
                        case "view" -> stringOut = new userView().setView(database, action);
                    }
                }

                case "query" -> {
                    switch(action.getCriteria()) {
                        case "average" -> stringOut = new Average().getResult(database, action.getNumber(), action.getFilters(), action.getSortType());
//                        case "awards" -> stringOut = new Awards().getResult(database, (ArrayList<String>) action.getFilters().get(2), (ArrayList<String>) action.getFilters().get(3), action.getSortType());
                        case "awards" -> stringOut = new Awards().getResult(database, action.getFilters(), action.getSortType());
                        case "filter_description" -> stringOut = new FilterDescription().getResult(database, action.getFilters(), action.getSortType());
                        case "ratings" -> stringOut = new Rating().getResult(database, action.getNumber(), action.getFilters(), action.getObjectType(), action.getSortType());
                        case "favorite" -> stringOut = new Favorite().getResult(database, action.getNumber(), action.getFilters(), action.getObjectType(), action.getSortType());
                        case "longest" -> stringOut = new Longest().getResult(database, action.getNumber(), action.getFilters(), action.getObjectType(), action.getSortType());
                        case "most_viewed" -> stringOut = new MostViewed().getResult(database, action.getNumber(), action.getFilters(), action.getObjectType(), action.getSortType());
                        case "num_ratings" -> stringOut = new NumberOfRatings().getResult(database, action.getNumber(), action.getSortType());
                    }
                }

//                case "recommendation" -> {
//                    switch(action.getType()) {
//                        case "standard" -> {
//                        }
//                        case "best_unseen" -> {
//                        }
//                        case "popular" -> {
//                        }
//                        case "favorite" -> {
//                        }
//                        case "search" -> {
//                        }
//                    }
//                }
            }

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
