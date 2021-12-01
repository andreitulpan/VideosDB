package user.commands;

import database.Database;
import entities.Users;
import fileio.ActionInputData;
import utils.Utils;

import static common.Constants.ERROR;
import static common.Constants.NOT_SEEN;
import static common.Constants.SUCCESS;
import static common.Constants.ALREADY_FAVOURITE;
import static common.Constants.ADDED_SUCCESS;


public final class UserFavorite {
    private UserFavorite() { }

    /**
     * Adauga show-ul in lista de favorite a userului,
     * daca a fost vizionat deja.
     *
     * @param database baza de date
     * @param action actiunea de executat
     * @return mesajul afisat
     */
    public static String setFavorite(final Database database, final ActionInputData action) {
        // Titlul show-ului ce trebuie adaugat
        String title = action.getTitle();

        // Cauta user-ul
        Users user = Utils.findUser(database, action.getUsername());

        // Creez string-ul de eroare
        StringBuilder stringOut = new StringBuilder();
        stringOut.append(ERROR).append(title).append(NOT_SEEN);

        // Verific daca user-ul a vizionat show-ul
        if (user != null && user.getHistory().containsKey(title)) {

            // Daca nu este deja la favorite, actiunea are succes si
            // creeaza mesajul potrivit, altfel creeaza mesajul de eroare.
            if (!user.getFavoriteMovies().contains(title)) {
                user.getFavoriteMovies().add(title);
                stringOut = new StringBuilder();
                stringOut.append(SUCCESS).append(title).append(ADDED_SUCCESS);
            } else {
                stringOut = new StringBuilder();
                stringOut.append(ERROR).append(title).append(ALREADY_FAVOURITE);
            }
        }

        return stringOut.toString();
    }
}
