package app.commands.specialusers.artist;

import app.entities.Library;
import app.entities.userside.artist.Artist;
import app.entities.userside.artist.Event;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.common.DateValidator;
import app.common.Output;
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
public class AddEvent extends CheckExistenceArtist {
    private String name;
    private String description;
    private String date;

    /**
     * Returns a string representation of the AddEvent command.
     *
     * @return A string describing the AddEvent command.
     */
    @Override
    public String toString() {
        return super.toString() + "AddEvent{"
                + "name='" + name + '\''
                + ", description='" + description + '\''
                + ", date='" + date + '\''
                + '}';
    }

    /**
     * Executes the AddEvent command to add a new event to the artist's profile.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param library The Library where artist data is stored.
     * @param offline A boolean flag indicating if the command is executed offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.ADD_EVENT);

        Artist artist = library.getArtistWithName(getUsername());
        if (validateUserAndArtist(library, out, getUsername())) {
            return;
        }

        if (artist.getEvents().stream().anyMatch(event -> event.getName().equals(getName()))) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername()
                    + Output.EVENT_ALREADY_EXISTS);
            return;
        }

        if (!DateValidator.isValidDate(getDate())) {
            out.put(Output.MESSAGE, "Event for " + getUsername() + Output.INVALID_DATE);
            return;
        }

        Event newEvent = new Event(getName(), getDescription(), getDate());
        artist.addEvent(newEvent);

        out.put(Output.MESSAGE, getUsername() + Output.EVENT_ADD_SUCCESS);
    }
}
