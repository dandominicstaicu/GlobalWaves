package commands.stats;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.playable.audio_files.Song;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ShowPreferredSongs extends Command {
    /**
     * Returns a string representation of the command.
     * Used for debugging.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString() + "ShowPreferredSongs{" + '}';
    }

    /**
     * Executes the command to show preferred songs and adds the results to the outputs.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib) {
        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.PREFERRED_SONGS);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        List<Song> favoriteSongs = lib.getUserWithUsername(getUsername()).getFavoriteSongs();

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (Song song : favoriteSongs) {
            resultArray.add(song.getName());
        }

    }
}
