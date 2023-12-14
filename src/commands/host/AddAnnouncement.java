package commands.host;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import common.UserTypes;
import entities.Library;
import entities.user.side.Announcement;
import entities.user.side.User;
import entities.user.side.pages.HostPage;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddAnnouncement extends Command {
    private String name;
    private String description;

    @Override
    public String toString() {
        return super.toString() + "AddAnnouncement{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.ADD_ANNOUNCE);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        User user = library.searchAllUsersForUsername(getUsername());
        if (user == null) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.DOESNT_EXIST);
            return;
        }

        HostPage host = library.getHostWithName(getUsername());
        if (host == null) {
            out.put(Output.MESSAGE, getUsername() + Output.NOT_HOST);
            return;
        }

        if (host.getAnnouncements().stream().anyMatch(announcement -> announcement.getName().equals(getName()))) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.SAME_NAME_ANNOUNCE);
            return;
        }

        Announcement newAnnounce = new Announcement(getName(), getDescription());
        host.addAnnounce(newAnnounce);

        out.put(Output.MESSAGE, getUsername() + Output.ANNOUNCE_ADD_SUCCESS);
    }

}
