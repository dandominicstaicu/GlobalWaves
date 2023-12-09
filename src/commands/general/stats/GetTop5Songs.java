package commands.general.stats;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Constants;
import common.Output;
import entities.Library;
import entities.playable.audio_files.Song;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Comparator;
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
    public void execute(final ArrayNode outputs, final Library lib) {
        ObjectNode out = outputs.addObject();
        out.put(Output.COMMAND, Output.TOP_5_SONGS);
        out.put(Output.TIMESTAMP, getTimestamp());


        // chatGPT helped me optimise this part (getting the top5 songs)
        List<Song> sortedSongs = lib.getSongs().stream()
                .sorted(Comparator.comparingInt(Song::getLikes).reversed()) // descending
                .limit(Constants.MAX_LIST_RETURN)
                .toList(); // convert to list

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (Song song : sortedSongs) {
            resultArray.add(song.getName());
        }
    }

}
