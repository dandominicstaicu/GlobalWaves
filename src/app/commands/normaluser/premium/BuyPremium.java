package app.commands.normaluser.premium;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BuyPremium extends Command {
    /**
     * Returns a string representation of the BuyPremium command.
     *
     * @return A string describing the BuyPremium command.
     */
    @Override
    public String toString() {
        return "BuyPremium{}" + super.toString();
    }


    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        System.out.println(this.toString());

        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.BUY_PREMIUM);

        NormalUser user = library.getUserWithUsername(getUsername());

        if (user == null) {
            out.put(Output.MESSAGE, "The username " + getUsername() + " doesn't exist.");
            return;
        }

        if (user.getIsPremium()) {
            out.put(Output.MESSAGE, getUsername() + Output.ALREADY_PREMIUM);
            return;
        }

        user.setIsPremium(true);
        out.put(Output.MESSAGE, getUsername() + Output.BUY_PREMIUM_SUCCESS);
    }
}
