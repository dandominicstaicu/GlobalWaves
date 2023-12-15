package app.commands.normaluser.pages;

import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.entities.userside.pages.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class PrintCurrentPage extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String toString() {
        return super.toString() + "PrintCurrentPage{}";
    }

    /**
     * Executes the command to print the content of the user's current page.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param library The Library where user data is stored.
     * @param offline A boolean indicating whether the command is executed in offline mode.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.PRINT_CURRENT_PAGE);

        if (offline) {
            out.put(Output.MESSAGE, getUsername() + Output.IS_OFFLINE);
            return;
        }

        NormalUser user = library.getUserWithUsername(getUsername());
        assert user != null;
        Page currentPage = user.getCurrentPage();

        out.put(Output.MESSAGE, currentPage.printPage(library, user));
    }
}
