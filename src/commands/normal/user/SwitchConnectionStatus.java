package commands.normal.user;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import common.UserTypes;
import entities.Library;
import entities.user.side.NormalUser;
import entities.user.side.User;
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
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.SWITCH_CONNECTION_STATUS);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        User user = library.getFromAllUsers(getUsername());

        try {
            assert user != null;

            if (user.getUserType() != UserTypes.NORMAL_USER) {
                out.put(Output.MESSAGE, getUsername() + Output.NOT_NORMAL_USER_ERR);
                return;
            }

        } catch (NullPointerException e) {
            out.put(Output.MESSAGE,  "The username " + getUsername() + " doesn't exist.");
            return;
        }

        NormalUser normalUser = library.getUserWithUsername(getUsername());

        normalUser.switchConnectionStatus(this.getTimestamp());
        out.put(Output.MESSAGE, getUsername() + Output.CONNECTION_STATUS_CHANGED);
    }
}
