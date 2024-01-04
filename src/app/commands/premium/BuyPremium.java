package app.commands.premium;

import app.commands.Command;
import app.entities.Library;
import com.fasterxml.jackson.databind.node.ArrayNode;

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
    }
}
