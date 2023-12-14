package app.commands.generalstats;

import app.entities.Library;
import app.entities.userside.User;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class GetAllUsers extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String toString() {
        return super.toString() + "GetAllUsers{}";
    }

    /**
     * Executes the command to retrieve a list of all users and stores the result in the
     * outputs array.
     *
     * @param outputs  The array to store command outputs.
     * @param library  The library containing user data.
     * @param offline  A flag indicating whether the command is executed in offline mode.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.GET_ALL_USERS);

        List<User> allUsers = library.getAllUsers();

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (User user : allUsers) {
            resultArray.add(user.getUsername());
        }
    }
}
