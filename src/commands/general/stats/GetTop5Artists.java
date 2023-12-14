package commands.general.stats;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.Stats;
import entities.playable.Album;
import entities.user.side.pages.ArtistPage;
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
    public void execute(ArrayNode outputs, Library library, boolean offline) {
        ObjectNode out = outputs.addObject();
        out.put(Output.COMMAND, Output.TOP_5_ARTISTS);
        out.put(Output.TIMESTAMP, getTimestamp());

        List<ArtistPage> sortedArtists = Stats.top5Artists(library);

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (ArtistPage artist : sortedArtists) {
            resultArray.add(artist.getName());
        }
    }

}
