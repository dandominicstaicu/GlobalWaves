package commands.general.stats;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.user.side.User;
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
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();
        out.put(Output.COMMAND, Output.GET_ALL_USERS);
        out.put(Output.TIMESTAMP, getTimestamp());

        List<User> allUsers = library.getAllUsers();

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (User user : allUsers) {
            resultArray.add(user.getUsername());
        }

    }
}
