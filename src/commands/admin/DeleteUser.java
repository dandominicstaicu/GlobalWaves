package commands.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.user.side.User;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class DeleteUser extends Command {
    @Override
    public String toString() {
        return super.toString() + "DeleteUser{}";
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.DELETE_USER);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

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
