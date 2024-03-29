package app.commands.normaluser.playlist;

import app.entities.Library;
import app.entities.playable.Playlist;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.entities.playable.audio_files.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ShowPlaylists extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString() + "ShowPlaylist{" + '}';
    }

    /**
     * Executes the command to show all playlists and adds the results to the outputs.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib, final boolean offline) {
        ObjectNode out = outputs.addObject();
        printCommandInfo(out, Output.SHOW_PLAYLISTS);

        // chatGPT helped me write this part (the output of JSON)
        ArrayNode resultArray = out.putArray(Output.RESULT);

        NormalUser normalUser = lib.getUserWithUsername(getUsername());
        List<Playlist> userOwnedPlaylists = normalUser.getPlaylistsOwnedByUser(lib.getPlaylists());

        for (Playlist playlist : userOwnedPlaylists) {
            ObjectNode playlistJson = resultArray.addObject();
            playlistJson.put(Output.NAME, playlist.getName());

            ArrayNode songsArray = playlistJson.putArray(Output.SONGS);
            for (Song song : playlist.getSongs()) {
                songsArray.add(song.getName());
            }

            playlistJson.put(Output.VISIBILITY, playlist.getIsPublic()
                    ? Output.PUBLIC : Output.PRIVATE);
            playlistJson.put(Output.FOLLOWERS, playlist.getFollowers());
        }
    }
}
