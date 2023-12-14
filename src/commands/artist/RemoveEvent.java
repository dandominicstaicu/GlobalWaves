package commands.artist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
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
public class RemoveEvent extends Command {
    private String name;

    @Override
    public String toString() {
        return super.toString() + "RemoveEvent{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.REMOVE_EVENT);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

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

        if(!artist.hasEventWithName(getName())) {
            out.put(Output.MESSAGE, getUsername() + Output.EVENT_NAME_ERR);
            return;
        }

        Event event = artist.getEventWithName(getName());
        artist.removeEvent(event);

        out.put(Output.MESSAGE, getUsername() + Output.REMOVE_EVENT_SUCCESS);
    }
}
