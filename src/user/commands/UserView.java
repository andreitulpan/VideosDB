package user.commands;

import database.Database;
import entities.Users;
import fileio.ActionInputData;
import utils.Utils;

import static common.Constants.SUCCESS;
import static common.Constants.VIEWED_SUCCESS;

public final class UserView {
    private UserView() { }

    /**
     * Adauga show-ul in istoricul user-ului sau incrementez
     * numarul de vizionari daca a mai fost vizionat.
     *
     * @param database baza de date
     * @param action actiunea de executat
     * @return mesajul afisat
     */
    public static String setView(final Database database, final ActionInputData action) {
        String title = action.getTitle();   // titlul show-ului

        // Verific daca user-ul a vizionat show-ul
        Users user = Utils.findUser(database, action.getUsername());

        // Creez string-ul ce trebuie returnat
        StringBuilder stringOut = new StringBuilder();

        // Verific daca exista user-ul
        if (user != null) {
            // Daca a fost vizionat incrementez numarul vizionarilor,
            // iar daca nu a fost il adaug in map
            if (user.getHistory().containsKey(title)) {
                user.getHistory().put(title, user.getHistory().get(title) + 1);
            } else {
                user.getHistory().put(title, 1);
            }
            stringOut.append(SUCCESS).append(title).append(VIEWED_SUCCESS);
            stringOut.append(user.getHistory().get(title));
        }

        return stringOut.toString();
    }
}
