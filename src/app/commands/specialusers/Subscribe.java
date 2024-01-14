package app.commands.specialusers;

import app.commands.Command;
import app.common.Output;
import app.common.PageTypes;
import app.entities.Library;
import app.entities.userside.User;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.pages.Page;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Subscribe extends Command {
    /**
     * Returns a string representation of this Subscribe object.
     *
     * @return A string representation of this Subscribe object.
     */
    @Override
    public String toString() {
        return super.toString() + "Subscribe{}";
    }

    /**
     * Executes the Subscribe command, allowing a user to subscribe or unsubscribe from an artist
     * or host's page.
     * This method checks if the user and the current page exist and if it's an artist or
     * host page.
     * It then subscribes or unsubscribes the user accordingly and adds an appropriate message
     * to the output.
     *
     * @param outputs  The ArrayNode where the output will be added.
     * @param library  The Library object containing user, artist, and host data.
     * @param offline  A boolean indicating whether the user is offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.SUBSCRIBE);

        NormalUser user = library.getUserWithUsername(getUsername());

        if (user == null) {
            out.put(Output.MESSAGE, "The username " + getUsername() + " doesn't exist.");
            return;
        }

        Page currentPage = user.getCurrentPage();
        if (currentPage.getPageType() != PageTypes.ARTIST_PAGE
                && currentPage.getPageType() != PageTypes.HOST_PAGE) {
            out.put(Output.MESSAGE, Output.SUBSCRIBE_ERR);
        }

        User specialUser = currentPage.getOwner();

        if (specialUser.isSubscribed(this.getUsername())) {
            specialUser.unsubscribe(user);
            handleOutput(out, Output.UNSUBSCRIBED, currentPage.getPageType(), specialUser);
        } else {
            specialUser.subscribe(user);
            handleOutput(out, Output.SUBSCRIBED, currentPage.getPageType(), specialUser);
        }
    }

    private void handleOutput(final ObjectNode out, final String subscribeStat,
                              final PageTypes specialUserType, final User specialUser) {
        if (specialUserType.equals(PageTypes.ARTIST_PAGE)) {
            out.put(Output.MESSAGE, getUsername() + subscribeStat + specialUser.getUsername()
                    + " successfully.");
        } else {
            out.put(Output.MESSAGE, getUsername() + subscribeStat + specialUser.getUsername()
                    + " successfully.");
        }
    }
}
