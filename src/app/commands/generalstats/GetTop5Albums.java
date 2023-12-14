package app.commands.generalstats;

import app.entities.Library;
import app.entities.Stats;
import app.entities.playable.Album;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class GetTop5Albums extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String toString() {
        return super.toString() + "GetTop5Albums{}";
    }

    /**
     * Executes the command to retrieve a list of the top 5 albums and adds the results to
     * the specified output.
     *
     * @param outputs  The array node where the command output should be added.
     * @param library  The library from which to retrieve album statistics.
     * @param offline  A boolean indicating whether the command is executed in offline mode.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();
        printCommandInfo(out, Output.TOP_5_ALBUMS);

        List<Album> sortedAlbums = Stats.top5Albums(library);

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (Album album : sortedAlbums) {
            resultArray.add(album.getName());
        }

    }
}
