package app.commands.premium;

import app.commands.Command;
import app.entities.Library;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class CancelPremium extends Command {
    @Override
    public String toString() {
        return "CancelPremium{}" + super.toString();
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        System.out.println(this.toString());
    }
}
