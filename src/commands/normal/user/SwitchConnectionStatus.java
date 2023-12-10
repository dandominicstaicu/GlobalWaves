package commands.normal.user;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import common.UserTypes;
import entities.Library;
import entities.user.side.NormalUser;
import entities.user.side.UserPlayer;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class SwitchConnectionStatus extends Command {
    @Override
    public String toString() {
        return super.toString() + "SwitchConnectionStatus{}";
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.SWITCH_CONNECTION_STATUS);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        NormalUser normalUser = library.getUserWithUsername(getUsername());

        try {
            assert normalUser != null;

            if (normalUser.getUserType() != UserTypes.NORMAL_USER) {
                out.put(Output.MESSAGE, getUsername() + Output.NOT_NORMAL_USER_ERR);
                return;
            }

//            UserPlayer player = normalUser.getPlayer();
        } catch (NullPointerException e) {
            System.out.println("null pointer exception at switch connection status");
            out.put(Output.MESSAGE,  "The username " + getUsername() + " doesn't exist.");
            return;
        }

        normalUser.switchConnectionStatus();
        out.put(Output.MESSAGE, getUsername() + Output.CONNECTION_STATUS_CHANGED);


    }

}
