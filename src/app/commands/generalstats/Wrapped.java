package app.commands.generalstats;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.User;
import app.entities.userside.normaluser.WrappedStats;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map;

public class Wrapped extends Command {
    @Override
    public String toString() {
        return super.toString() + "Wrapped{}";
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.WRAPPED);

        if (offline) {
            userIsOffline(out);
            return;
        }

        User user = library.getFromAllUsers(getUsername());
        if (user == null) {
            out.put(Output.MESSAGE, Output.WRAPPED_ERR + getUsername());
            return;
        }

        user.printWrappedStats(out);
    }
}
