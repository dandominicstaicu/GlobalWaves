package app.commands.normaluser.pages;

import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.pages.LikedContentPage;
import app.entities.userside.pages.Page;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.entities.userside.pages.HomePage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePage extends Command {
    private String nextPage;

    /**
     * Returns a string representation of the command including the next page.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return "ChangePage{"
                + "nextPage='" + nextPage + '\''
                + '}';
    }

    /**
     * Sets the user's current page to the specified page and creates a message indicating
     * the page change.
     *
     * @param user    The NormalUser whose page is being changed.
     * @param page    The new Page to set as the user's current page.
     * @param message The message describing the page change.
     * @param out     The ObjectNode where the message should be added.
     */
    private void setPageAndCreateMessage(final NormalUser user, final Page page,
                                         final String message, final ObjectNode out) {
        user.setCurrentPage(page);
        out.put(Output.MESSAGE, getUsername() + " accessed " + message + " successfully.");
    }

    /**
     * Executes the command to change the user's current page.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The Library where user data is stored.
     * @param offline A boolean indicating whether the command is executed in offline mode.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.CHANGE_PAGE);

        if (offline) {
            userIsOffline(out);
            return;
        }

        NormalUser user = lib.getUserWithUsername(getUsername());
        assert user != null;

        switch (nextPage) {
            case "Home":
                setPageAndCreateMessage(user, new HomePage(), "Home", out);
                break;
            case "LikedContent":
                setPageAndCreateMessage(user, new LikedContentPage(), "LikedContent", out);
                break;
            default:
                out.put(Output.MESSAGE, getUsername() + Output.NON_EXISTENT_PAGE);
                break;
        }
    }
}
