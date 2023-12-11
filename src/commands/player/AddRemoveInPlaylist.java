package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.user.side.NormalUser;
import entities.user.side.UserPlayer;
import entities.playable.Playlist;
import entities.playable.audio_files.Song;
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
        if (offline) {
            userIsOffline(outputs);
            return;
        }

        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.ADD_REMOVE_IN_PLAYLIST);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        NormalUser normalUser = lib.getUserWithUsername(getUsername());
        if (normalUser == null) {
            System.out.println("User not found. is null in add remove in playlist");
            return;
        }

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
