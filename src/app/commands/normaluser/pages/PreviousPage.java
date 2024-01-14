package app.commands.normaluser.pages;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PreviousPage extends Command {
    /**
     * Returns a string representation of this PreviousPage object.
     *
     * @return A string representation of this PreviousPage object.
     */
    @Override
    public String toString() {
        return "PreviousPage{}";
    }

    /**
     * Executes the PreviousPage command, allowing the user to navigate to the previous page in
     * their history.
     * This method checks if there is a previous page in the user's history and navigates to
     * it if available.
     *
     * @param outputs  The ArrayNode where the output will be added.
     * @param library  The Library object containing user data.
     * @param offline  A boolean indicating whether the user is offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.PREV_PAGE);

        NormalUser user = library.getUserWithUsername(getUsername());
        if (user.getHistoryIndex() == 0) {
            out.put(Output.MESSAGE, Output.NO_PREV_PAGE);
            return;
        }

        user.goToPrevPage();
        out.put(Output.MESSAGE, "The user " + getUsername() + Output.PREV_PAGE_SUCCESS);
    }
}
