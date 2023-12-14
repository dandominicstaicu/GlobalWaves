package app.commands.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class DeleteUser extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String toString() {
        return super.toString() + "DeleteUser{}";
    }

    /**
     * Executes the command to delete a user from the library and adds the result to the
     * specified output.
     *
     * @param outputs  The array node where the command output should be added.
     * @param library  The library containing user data and other entities.
     * @param offline  A boolean indicating whether the command is executed in offline mode.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.DELETE_USER);

        User user = library.getFromAllUsers(getUsername());

        if (user == null) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.DOESNT_EXIST);
            return;
        }

        boolean deleteUserReturn = user.handleDeletion(library);

        if (!deleteUserReturn) {
            out.put(Output.MESSAGE, getUsername() + Output.DELETE_USER_FAIL);
            return;
        }

        out.put(Output.MESSAGE, getUsername() + Output.DELETE_USER_SUCCESS);
    }
}
