package app.commands.normaluser.premium;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CancelPremium extends Command {
    @Override
    public String toString() {
        return "CancelPremium{}" + super.toString();
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
//        System.out.println(this.toString());

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
