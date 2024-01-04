package app.commands.specialusers;

import app.commands.Command;
import app.entities.Library;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Subscribe extends Command {
    @Override
    public String toString() {
        return super.toString() + "Subscribe{}";
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        System.out.println(this.toString());
    }
}
