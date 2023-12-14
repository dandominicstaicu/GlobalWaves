package app.commands.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.playable.Album;
import app.entities.playable.audio_files.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ShowAlbums extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String toString() {
        return super.toString() + "ShowAlbums{}";
    }

    /**
     * Executes the command to retrieve and display the albums of an artist in the library and
     * adds the results to the specified output.
     *
     * @param outputs  The array node where the command output should be added.
     * @param library  The library containing the albums and songs.
     * @param offline  A boolean indicating whether the command is executed in offline mode.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.SHOW_ALBUMS);

        ArrayList<Album> artistsAlbums = library.getArtistsAlbums(getUsername());

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (Album album : artistsAlbums) {
            ObjectNode albumNode = resultArray.addObject();
            albumNode.put(Output.NAME, album.getName());

            ArrayNode songsArray = albumNode.putArray(Output.SONGS);
            for (Song song : album.getSongs()) {
                songsArray.add(song.getName());
            }
        }
    }

}
