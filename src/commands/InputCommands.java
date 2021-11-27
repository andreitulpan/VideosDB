package commands;

import database.Database;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
//            switch(action.getActionType()) {
//
//                case "command" -> {
//                    switch(action.getType()) {
//                        case "favorite" -> {
//                        }
//                        case "rating" -> {
//                        }
//                        case "view" -> {
//                        }
//                    }
//                }
//
//                case "query" -> {
//                    switch(action.getCriteria()) {
//                        case "average" -> {
//                        }
//                        case "awards" -> {
//                        }
//                        case "filter_description" -> {
//                        }
//                        case "ratings" -> {
//                        }
//                        case "favorite" -> {
//                        }
//                        case "longest" -> {
//                        }
//                        case "most_viewed" -> {
//                        }
//                        case "num_ratings" -> {
//                        }
//                    }
//                }
//
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
//            }

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
