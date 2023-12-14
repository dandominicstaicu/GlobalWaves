package commands.general.stats;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.Stats;
import entities.user.side.artist.Artist;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class GetTop5Artists extends Command {
    @Override
    public String toString() {
        return super.toString() + "GetTop5Artists{}";
    }

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
