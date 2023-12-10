package commands.general.stats;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.Stats;
import entities.user.side.NormalUser;
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
    public void execute(ArrayNode outputs, Library library, boolean offline) {
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();
        out.put(Output.COMMAND, Output.ONLINE_USERS);
        out.put(Output.TIMESTAMP, getTimestamp());

        List<NormalUser> onlineUsers = Stats.getOnlineUsers(library);

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (NormalUser user : onlineUsers) {
            resultArray.add(user.getUsername());
        }
    }
}
