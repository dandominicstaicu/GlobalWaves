package commands.general.stats;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Constants;
import common.Output;
import entities.Library;
import entities.Stats;
import entities.playable.Playlist;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;


import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetTop5Playlists extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString() + "GetTop5Playlists{}";
    }

    /**
     * Executes the command to get the top 5 songs by likes and adds the results to the outputs.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib, boolean offline) {
        ObjectNode out = outputs.addObject();
        printCommandInfo(out, Output.TOP_5_PLAYLISTS);

        List<Playlist> sortedPlaylists = Stats.top5Playlists(lib);

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (Playlist playlist : sortedPlaylists) {
            resultArray.add(playlist.getName());
        }
    }
}
