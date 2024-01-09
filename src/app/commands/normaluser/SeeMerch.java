package app.commands.normaluser;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class SeeMerch extends Command {
    @Override
    public String toString() {
        return super.toString() + "seeMerch{}";
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
//        System.out.println(this.toString());
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
