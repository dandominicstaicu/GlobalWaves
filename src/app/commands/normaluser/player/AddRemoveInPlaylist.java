package app.commands.normaluser.player;

import app.entities.Library;
import app.entities.playable.Playlist;
import app.entities.userside.NormalUser;
import app.entities.userside.UserPlayer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.entities.playable.audio_files.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a user command to skip to the next track.
 * Extends the Command class.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddRemoveInPlaylist extends Command {
    private Integer playlistId;

    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "AddRemoveInPlaylist{"
                + ", playlistId=" + playlistId
                + '}';
    }

    /**
     * Executes the command to skip to the next track and adds the results to
     * the outputs.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib, boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.ADD_REMOVE_IN_PLAYLIST);
        if (offline) {
            userIsOffline(out);
            return;
        }

        NormalUser normalUser = lib.getUserWithUsername(getUsername());

        assert normalUser != null;
        UserPlayer userPlayer = normalUser.getPlayer();

        List<Playlist> playlistsSeenByUser = normalUser.getPlaylistsOwnedByUser(lib.getPlaylists());


        if (getPlaylistId() > playlistsSeenByUser.size() || getPlaylistId() <= 0) {
            out.put(Output.MESSAGE, Output.PLAYLIST_NOT_EXIST);
        } else if (!userPlayer.playingIndexIsValid()) {
            out.put(Output.MESSAGE, Output.LOAD_ADD_RMV_ERR);
        } else if (!userPlayer.getAudioQueue().get(userPlayer.getPlayingIndex()).isSong()) {
            out.put(Output.MESSAGE, Output.LOAD_NOT_SONG_ERR);
        } else {
            Song song = (Song) userPlayer.getAudioQueue().get(userPlayer.getPlayingIndex());
            boolean ret = lib.decideAddRemove(getPlaylistId(), song, getUsername());
            if (ret) {
                out.put(Output.MESSAGE, Output.ADDED_TO_PLAYLIST);
            } else {
                out.put(Output.MESSAGE, Output.REMOVED_FROM_PLAYLIST);
            }
        }
    }
}
