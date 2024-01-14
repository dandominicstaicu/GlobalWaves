package app.commands.generalstats;
import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.User;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Wrapped extends Command {
    /**
     * Returns a string representation of this Wrapped object, including the superclass's string
     * representation.
     *
     * @return A string representation of this Wrapped object.
     */
    @Override
    public String toString() {
        return super.toString() + "Wrapped{}";
    }

    /**
     * Executes the Wrapped command, printing wrapped statistics for the user.
     * If the user is offline or not found, an error message is included in the output.
     *
     * @param outputs  The ArrayNode where the output will be added.
     * @param library  The Library object containing user data.
     * @param offline  A boolean indicating whether the user is offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
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
