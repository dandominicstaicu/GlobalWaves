package app.commands.normaluser.pages;

import app.commands.Command;
import app.entities.Library;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class NextPage extends Command {
    @Override
    public String toString() {
        return super.toString() + "NextPage{}";
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        System.out.println(this.toString());
    }
}
