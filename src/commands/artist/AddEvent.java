package commands.artist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.DateValidator;
import common.Output;
import common.UserTypes;
import entities.Library;
import entities.user.side.Event;
import entities.user.side.User;
import entities.user.side.pages.ArtistPage;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddEvent extends Command {
    private String name;
    private String description;
    private String date;

    @Override
    public String toString() {
        return super.toString() + "AddEvent{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.ADD_EVENT);

        User user = library.searchAllUsersForUsername(getUsername());
        if (user == null) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.DOESNT_EXIST);
            return;
        }

        ArtistPage artist = library.getArtistWithName(getUsername());

        if (artist == null) {
            out.put(Output.MESSAGE, getUsername() + Output.NOT_ARTIST);
            return;
        }

        if (artist.getEvents().stream().anyMatch(event -> event.getName().equals(getName()))) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.EVENT_ALREADY_EXISTS);
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
