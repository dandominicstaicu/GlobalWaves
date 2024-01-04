package app.commands.generalstats;

import app.commands.Command;
import app.entities.Library;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Wrapped extends Command {
    @Override
    public String toString() {
        return super.toString() + "Wrapped{}";
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
        System.out.println(this.toString());
    }
}
