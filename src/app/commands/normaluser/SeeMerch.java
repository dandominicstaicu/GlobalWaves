package app.commands.normaluser;

import app.commands.Command;
import app.entities.Library;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class SeeMerch extends Command {
    @Override
    public String toString() {
        return super.toString() + "seeMerch{}";
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        System.out.println(this.toString());
    }
}
