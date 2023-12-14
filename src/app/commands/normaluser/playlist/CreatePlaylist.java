package app.commands.normaluser.playlist;

import app.entities.Library;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The command class in charge of creating a playlist.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePlaylist extends Command {
    private String playlistName;

    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "CreatePlaylist{"
                + ", playlistName='" + playlistName + '\''
                + '}';
    }

    /**
     * Executes the command to create a playlist and adds the results to the outputs.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib, boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.CREATE_PLAYLIST);

        if (offline) {
            userIsOffline(out);
            return;
        }

        boolean ret = lib.createPlaylist(this.getPlaylistName(), this.getUsername());
        if (ret) {
            out.put(Output.MESSAGE, Output.PLAYLIST_CREATED);
        } else {
            out.put(Output.MESSAGE, Output.PLAYLIST_EXISTS);
        }
    }
}
