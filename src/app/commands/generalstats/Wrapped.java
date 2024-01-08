package app.commands.generalstats;
import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.User;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Wrapped extends Command {
    @Override
    public String toString() {
        return super.toString() + "Wrapped{}";
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.WRAPPED);

        if (offline) {
            userIsOffline(out);
            return;
        }

        User user = library.getFromAllUsers(getUsername());
        if (user == null) {
            out.put(Output.MESSAGE, Output.WRAPPED_ERR_USER + getUsername() + ".");
            return;
        }

        user.printWrappedStats(out);
    }
}
