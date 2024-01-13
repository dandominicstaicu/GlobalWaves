package app.commands.normaluser.pages;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NextPage extends Command {
    @Override
    public String toString() {
        return super.toString() + "NextPage{}";
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
//        System.out.println(this.toString());
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
