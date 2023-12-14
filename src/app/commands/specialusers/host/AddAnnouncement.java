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
public class AddAnnouncement extends CheckExistenceHost {
    private String name;
    private String description;

    /**
     * Returns a string representation of the AddAnnouncement command.
     *
     * @return A string describing the AddAnnouncement command.
     */
    @Override
    public String toString() {
        return super.toString() + "AddAnnouncement{"
                + "name='" + name + '\''
                + ", description='" + description + '\''
                + '}';
    }

    /**
     * Executes the AddAnnouncement command to add an announcement to the host's profile.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param library The Library where host and announcement data is stored.
     * @param offline A boolean flag indicating if the command is executed offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.ADD_ANNOUNCE);

        Host host = library.getHostWithName(getUsername());
        if (validateUserAndHost(library, out, getUsername())) {
            return;
        }

        if (host.getAnnouncements().stream().anyMatch(announcement -> announcement.getName()
                .equals(getName()))) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername()
                    + Output.SAME_NAME_ANNOUNCE);
            return;
        }

        Announcement newAnnounce = new Announcement(getName(), getDescription());
        host.addAnnounce(newAnnounce);

        out.put(Output.MESSAGE, getUsername() + Output.ANNOUNCE_ADD_SUCCESS);
    }

}
