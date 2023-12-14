package app.commands.normaluser.playlist;

import app.entities.Library;
import app.entities.playable.Playable;
import app.entities.playable.Playlist;
import app.entities.userside.NormalUser;
import app.entities.userside.UserPlayer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The command class in charge of switching the visibility of a playlist.
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class FollowPlaylist extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "FollowPlaylist{"
                + '}';
    }

    /**
     * Executes the command to follow a playlist and adds the results to the outputs.
     *
     * @param outputs  The ArrayNode to which command outputs are added.
     * @param lib  The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib, boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.FOLLOW);

        if (offline) {
            userIsOffline(out);
            return;
        }

        NormalUser normalUser = lib.getUserWithUsername(getUsername());

        assert normalUser != null;
        UserPlayer userPlayer = normalUser.getPlayer();

        Playable selected = userPlayer.getSearchBar().getSelectedResult();

        if (selected == null) {
            out.put(Output.MESSAGE, Output.LOAD_FOLLOW_ERR);
        } else if (!selected.isPlaylist()) {
            out.put(Output.MESSAGE, Output.SELECTED_NOT_PLAYLIST);
        } else if (((Playlist) selected).getOwner().equals(getUsername())) {
            out.put(Output.MESSAGE, Output.OWN_PLAYLIST_ERR);
        } else {
            if (normalUser.followUnfollowPlaylist((Playlist) selected)) {
                out.put(Output.MESSAGE, Output.FOLLOW_SUCCESS);
            } else {
                out.put(Output.MESSAGE, Output.UNFOLLOW_SUCCESS);
            }
        }
    }
}
