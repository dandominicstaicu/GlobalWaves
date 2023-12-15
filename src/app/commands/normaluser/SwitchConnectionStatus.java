package app.commands.normaluser;

import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.User;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.common.UserTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class SwitchConnectionStatus extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String toString() {
        return super.toString() + "SwitchConnectionStatus{}";
    }

    /**
     * Executes the command to switch the user's online connection status.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param library The Library where user data is stored.
     * @param offline A boolean indicating whether the command is executed in offline mode.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.SWITCH_CONNECTION_STATUS);

        User user = library.getFromAllUsers(getUsername());

        try {
            assert user != null;

            if (user.getUserType() != UserTypes.NORMAL_USER) {
                out.put(Output.MESSAGE, getUsername() + Output.NOT_NORMAL_USER_ERR);
                return;
            }

        } catch (NullPointerException e) {
            out.put(Output.MESSAGE, "The username " + getUsername() + " doesn't exist.");
            return;
        }

        NormalUser normalUser = library.getUserWithUsername(getUsername());

        normalUser.switchConnectionStatus(this.getTimestamp());
        out.put(Output.MESSAGE, getUsername() + Output.CONNECTION_STATUS_CHANGED);
    }
}
