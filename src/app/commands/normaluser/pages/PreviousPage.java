package app.commands.normaluser.pages;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PreviousPage extends Command {

    @Override
    public String toString() {
        return "PreviousPage{}";
    }

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
