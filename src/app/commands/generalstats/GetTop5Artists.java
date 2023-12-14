package app.commands.generalstats;

import app.entities.Library;
import app.entities.Stats;
import app.entities.userside.artist.Artist;
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
public class GetTop5Artists extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String toString() {
        return super.toString() + "GetTop5Artists{}";
    }

    /**
     * Executes the command to retrieve the top 5 artists and stores the result in the outputs
     * array.
     *
     * @param outputs  The array to store command outputs.
     * @param library  The library containing user and artist data.
     * @param offline  A flag indicating whether the command is executed in offline mode.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();
        printCommandInfo(out, Output.TOP_5_ARTISTS);

        List<Artist> sortedArtists = Stats.top5Artists(library);

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (Artist artist : sortedArtists) {
            resultArray.add(artist.getName());
        }
    }

}
