package app.commands.generalstats;

import app.entities.Library;
import app.entities.Stats;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.entities.playable.audio_files.Song;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

/**
 * Represents a user command to get the top 5 songs by likes. Extends the Command class.
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetTop5Songs extends Command {
    /**
     * Returns a string representation of the command.
     * Used for debugging.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString() + "GetTop5Songs{}";
    }

    /**
     * Executes the command to get the top 5 songs by likes and adds the results to the outputs.
     * Part of the Command Pattern.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib, boolean offline) {
        ObjectNode out = outputs.addObject();
        printCommandInfo(out, Output.TOP_5_SONGS);

        List<Song> sortedSongs = Stats.top5Songs(lib);

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (Song song : sortedSongs) {
            resultArray.add(song.getName());
        }
    }

}
