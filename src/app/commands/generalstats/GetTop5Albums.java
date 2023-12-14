package app.commands.generalstats;

import app.entities.Library;
import app.entities.Stats;
import app.entities.playable.Album;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
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
