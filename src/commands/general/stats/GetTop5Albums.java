package commands.general.stats;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.Stats;
import entities.playable.Album;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class GetTop5Albums extends Command {
    @Override
    public String toString() {
        return super.toString() + "GetTop5Albums{}";
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
        ObjectNode out = outputs.addObject();
        printCommandInfo(out, Output.TOP_5_ALBUMS);

        List<Album> sortedAlbums = Stats.top5Albums(library);

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (Album album : sortedAlbums) {
            resultArray.add(album.getName());
        }

    }
}
