package app.commands.generalstats;

import app.entities.Library;
import app.entities.Stats;
import app.entities.userside.NormalUser;
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
public class GetOnlineUsers extends Command {
    @Override
    public String toString() {
        return super.toString() + "GetOnlineUsers{}";
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();
        printCommandInfo(out, Output.ONLINE_USERS);

        List<NormalUser> onlineUsers = Stats.getOnlineUsers(library);

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (NormalUser user : onlineUsers) {
            resultArray.add(user.getUsername());
        }
    }
}
