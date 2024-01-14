package app.commands.normaluser;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class SeeMerch extends Command {
    /**
     * Returns a string representation of this SeeMerchandise object, including the superclass's string representation.
     *
     * @return A string representation of this SeeMerchandise object.
     */
    @Override
    public String toString() {
        return super.toString() + "seeMerch{}";
    }

    /**
     * Executes the SeeMerchandise command, allowing the user to see available merchandise.
     * This method retrieves the user's merchandise data and adds it to the output.
     *
     * @param outputs  The ArrayNode where the output will be added.
     * @param library  The Library object containing user data.
     * @param offline  A boolean indicating whether the user is offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.SEE_MERCH);

        NormalUser user = library.getUserWithUsername(getUsername());
        if (user == null) {
            out.put(Output.MESSAGE, "The username " + getUsername() + " doesn't exist.");
            return;
        }

        ArrayNode resultNode = out.putArray(Output.RESULT);
        ArrayList<String> merchNames = user.getAllMerchNames();
        for (String merchName : merchNames) {
            resultNode.add(merchName);
        }

    }
}
