package commands.playlist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Constants;
import common.Output;
import entities.Library;
import entities.user.side.User;
import entities.playable.Playlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwitchVisibility extends Command {
    private Integer playlistId;

    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "SwitchVisibility{"
                + ", playlistId=" + playlistId
                + '}';
    }

    /**
     * Executes the command to switch the visibility of a playlist and adds the results to the
     * outputs.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib) {
        ObjectNode out = outputs.addObject();
        out.put(Output.COMMAND, Output.SWITCH_VISIBILITY);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        User user = lib.getUserWithUsername(getUsername());
        List<Playlist> userSeenPlaylists = user.getPlaylistsOwnedByUser(lib.getPlaylists());

        if (getPlaylistId() > userSeenPlaylists.size()
                || getPlaylistId() <= Constants.LOWER_BOUND) {
            out.put(Output.MESSAGE, Output.PLAYLIST_ID_IS_TOO_HIGH);
        } else {
            Playlist playlist = userSeenPlaylists.get(getPlaylistId() - 1); // adjust to 1 idx
            boolean visibility = playlist.switchVisibility();
            out.put(Output.MESSAGE, Output.VISIBILITY_CHANGED
                    + (visibility ? Output.PUBLIC : Output.PRIVATE) + ".");
        }
    }
}
