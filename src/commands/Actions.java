package commands;

import java.util.ArrayList;
import java.util.List;

public class Actions {
    private final int actionId;
    private final String actionType;
    private final String type;
    private final String username;
    private final String objectType;
    private final String sortType;
    private final String criteria;
    private final String title;
    private final String genre;
    private final int number;
    private final double grade;
    private final int seasonNumber;
    private final List<List<String>> filters = new ArrayList<>();

    // COMMANDS
    public Actions(final int actionId, final String actionType, final String type,
                           final String username, final String title, final Double grade,
                           final int seasonNumber) {
        this.actionId = actionId;
        this.actionType = actionType;
        this.type = type;
        this.grade = grade;
        this.username = username;
        this.title = title;
        this.seasonNumber = seasonNumber;
        this.genre = null;
        this.objectType = null;
        this.sortType = null;
        this.criteria = null;
        this.number = 0;
    }

    // QUERIES
    public Actions(final int actionId, final String actionType, final String objectType,
                           final String sortType, final String criteria,
                           final int number, List<List<String>> filters) {
        this.actionId = actionId;
        this.actionType = actionType;
        this.objectType = objectType;
        this.sortType = sortType;
        this.criteria = criteria;
        this.number = number;
        this.filters.add(new ArrayList<>(filters.get(0)));
        this.filters.add(new ArrayList<>(filters.get(1)));
        this.filters.add(filters.get(2));
        this.filters.add(filters.get(3));
        this.title = null;
        this.type = null;
        this.username = null;
        this.genre = null;
        this.grade = 0;
        this.seasonNumber = 0;
    }

    // RECOMMENDATIONS
    public Actions(final int actionId, final String actionType,
                              final String type, final String username, final String genre) {
        this.actionId = actionId;
        this.actionType = actionType;
        this.type = type;
        this.username = username;
        this.genre = genre;
        this.objectType = null;
        this.sortType = null;
        this.criteria = null;
        this.number = 0;
        this.title = null;
        this.grade = 0;
        this.seasonNumber = 0;
    }

    public int getActionId() {
        return actionId;
    }

    public String getActionType() {
        return actionType;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getSortType() {
        return sortType;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getNumber() {
        return number;
    }

    public double getGrade() {
        return grade;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public List<List<String>> getFilters() {
        return filters;
    }

    @Override
    public String toString() {
        return "ActionInputData{"
                + "actionId=" + actionId
                + ", actionType='" + actionType + '\''
                + ", type='" + type + '\''
                + ", username='" + username + '\''
                + ", objectType='" + objectType + '\''
                + ", sortType='" + sortType + '\''
                + ", criteria='" + criteria + '\''
                + ", title='" + title + '\''
                + ", genre='" + genre + '\''
                + ", number=" + number
                + ", grade=" + grade
                + ", seasonNumber=" + seasonNumber
                + ", filters=" + filters
                + '}' + "\n";
    }
}
