package app.commands.specialusers.artist;

import app.entities.Library;
import app.entities.userside.artist.Artist;
import app.entities.userside.artist.Event;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
public class RemoveEvent extends CheckExistenceArtist {
    private String name;

    /**
     * Returns a string representation of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String toString() {
        return super.toString() + "RemoveEvent{"
                + "name='" + name + '\''
                + '}';
    }

    /**
     * Executes the command to remove an event from the artist's list of events.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param library The Library where user data is stored.
     * @param offline A boolean indicating whether the command is executed in offline mode.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.REMOVE_EVENT);

        Artist artist = library.getArtistWithName(getUsername());
        if (validateUserAndArtist(library, out, getUsername())) {
            return;
        }

        if (!artist.hasEventWithName(getName())) {
            out.put(Output.MESSAGE, getUsername() + Output.EVENT_NAME_ERR);
            return;
        }

        Event event = artist.getEventWithName(getName());
        artist.removeEvent(event);

        out.put(Output.MESSAGE, getUsername() + Output.REMOVE_EVENT_SUCCESS);
    }
}
