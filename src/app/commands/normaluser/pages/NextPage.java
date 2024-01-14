package app.commands.normaluser.pages;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NextPage extends Command {
    /**
     * Returns a string representation of this NextPage object, including the superclass
     * string representation.
     *
     * @return A string representation of this NextPage object.
     */
    @Override
    public String toString() {
        return super.toString() + "NextPage{}";
    }

    /**
     * Executes the NextPage command, allowing the user to navigate to the next page in their
     * history.
     * This method checks if there is a next page in the user's history and navigates to it if
     * available.
     *
     * @param outputs  The ArrayNode where the output will be added.
     * @param library  The Library object containing user data.
     * @param offline  A boolean indicating whether the user is offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out  = outputs.addObject();

        printCommandInfo(out, Output.NEXT_PAGE);

        NormalUser user = library.getUserWithUsername(getUsername());
        if (user.getHistoryIndex() == user.getPageHistory().size() - 1) {
            out.put(Output.MESSAGE, Output.NO_NEXT_PAGE);
            return;
        }

        user.goToNextPage();
        out.put(Output.MESSAGE, "The user " + getUsername() + Output.NEXT_PAGE_SUCCESS);
    }
}
