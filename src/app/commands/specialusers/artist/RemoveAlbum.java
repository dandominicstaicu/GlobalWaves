package app.commands.specialusers.artist;

import app.entities.Library;
import app.entities.playable.Album;
import app.entities.userside.User;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.common.UserTypes;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveAlbum extends Command {
    private String name;

    @Override
    public String toString() {
        return super.toString() + "RemoveAlbum{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.REMOVE_ALBUM);

        User user = library.getFromAllUsers(getUsername());

        if (user == null) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.DOESNT_EXIST);
            return;
        }

        if (user.getUserType() != UserTypes.ARTIST) {
            out.put(Output.MESSAGE, getUsername() + Output.NOT_ARTIST);
            return;
        }

        if (!library.hasAlbumWithGivenName(getUsername(), getName())) {
            out.put(Output.MESSAGE, getUsername() + Output.NO_ALBUM_WITH_NAME);
            return;
        }

        Album album = library.getAlbumOfUserWithName(getUsername(), getName());

        if (library.decideDeleteAlbum(album)) {
            out.put(Output.MESSAGE, getUsername() + Output.DELETE_ALBUM_FAIL);
            return;
        }

        library.removeAlbum(album);

        out.put(Output.MESSAGE, Output.DELETE_ALBUM_SUCCESS);
    }
}
