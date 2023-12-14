package commands.special.users.host.host;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.user.side.host.Announcement;
import entities.user.side.User;
import entities.user.side.host.Host;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveAnnouncement extends Command {
    private String name;

    @Override
    public String toString() {
        return super.toString() + "RemoveAnnouncement{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.REMOVE_ANNOUNCE);

        User user = library.searchAllUsersForUsername(getUsername());
        if (user == null) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.DOESNT_EXIST);
            return;
        }

        Host host = library.getHostWithName(getUsername());
        if (host == null) {
            out.put(Output.MESSAGE, getUsername() + Output.NOT_HOST);
            return;
        }

        if (!host.hasAnnouncementWithName(getName())) {
            out.put(Output.MESSAGE, getUsername() + Output.ANNOUNCE_NAME_ERR);
            return;
        }

        Announcement announcement = host.getAnnounceWithName(getName());
        host.removeAnnounce(announcement);
        out.put(Output.MESSAGE, getUsername() + Output.REMOVE_ANNOUNCE_SUCCESS);
    }
}
