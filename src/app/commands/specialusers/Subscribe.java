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
    @Override
    public String toString() {
        return super.toString() + "Subscribe{}";
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
//        System.out.println(this.toString());
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

    private void handleOutput(final ObjectNode out, final String subscribeStat, final PageTypes specialUserType,
                              final User specialUser) {
        if (specialUserType.equals(PageTypes.ARTIST_PAGE)) {
            out.put(Output.MESSAGE, getUsername() + subscribeStat + specialUser.getUsername() + " successfully.");
        } else {
            out.put(Output.MESSAGE, getUsername() + subscribeStat + specialUser.getUsername() + " successfully.");
        }
    }
}
