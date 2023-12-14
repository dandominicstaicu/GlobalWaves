package app.commands.generalstats;

import app.entities.Library;
import app.entities.userside.User;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class GetAllUsers extends Command {
    @Override
    public String toString() {
        return super.toString() + "GetAllUsers{}";
    }

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
