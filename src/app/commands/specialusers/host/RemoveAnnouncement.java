package app.commands.specialusers.host;

import app.entities.Library;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.common.Output;
import app.entities.userside.host.Announcement;
import app.entities.userside.host.Host;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveAnnouncement extends CheckExistenceHost {
    private String name;

    /**
     * Returns a string representation of the RemoveAnnouncement command.
     *
     * @return A string describing the RemoveAnnouncement command.
     */
    @Override
    public String toString() {
        return super.toString() + "RemoveAnnouncement{"
                + "name='" + name + '\''
                + '}';
    }

    /**
     * Executes the RemoveAnnouncement command to remove an announcement from the host's profile.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param library The Library where announcement and host data is stored.
     * @param offline A boolean flag indicating if the command is executed offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.REMOVE_ANNOUNCE);

        Host host = library.getHostWithName(getUsername());
        if (validateUserAndHost(library, out, getUsername())) {
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
