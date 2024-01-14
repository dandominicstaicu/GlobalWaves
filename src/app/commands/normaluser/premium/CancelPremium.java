package app.commands.normaluser.premium;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CancelPremium extends Command {
    /**
     * Returns a string representation of this CancelPremium object, including the superclass's string representation.
     *
     * @return A string representation of this CancelPremium object.
     */
    @Override
    public String toString() {
        return "CancelPremium{}" + super.toString();
    }

    /**
     * Executes the CancelPremium command, allowing a user to cancel their premium subscription.
     * This method checks if the user exists, if they are currently premium, and then cancels their premium subscription.
     * It also adds an appropriate message to the output.
     *
     * @param outputs  The ArrayNode where the output will be added.
     * @param library  The Library object containing user data.
     * @param offline  A boolean indicating whether the user is offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.CANCEL_PREMIUM);

        NormalUser user = library.getUserWithUsername(getUsername());

        if (user == null) {
            out.put(Output.MESSAGE, "The username " + getUsername() + " doesn't exist.");
            return;
        }

        if (!user.getIsPremium()) {
            out.put(Output.MESSAGE, getUsername() + Output.NOT_PREMIUM);
            return;
        }

        user.payPremiumArtist(library);
        user.setIsPremium(false);

        out.put(Output.MESSAGE, getUsername() + Output.CANCEL_PREMIUM_SUCCESS);
    }
}
