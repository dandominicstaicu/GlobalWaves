package app.commands.specialusers;

import app.commands.Command;
import app.commands.specialusers.artist.Monetization;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.artist.Artist;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EndProgram extends Command {
    @Override
    public String toString() {
        return super.toString() +  "EndProgram{}";
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        System.out.println(this.toString());
//        ObjectNode out = outputs.addObject();
//
//        out.put(Output.COMMAND, Output.END_PROGRAM);
//
//        Map<String, Monetization> artistsMonetization = library.getArtistsMonetization();
//        ObjectNode resultNode = out.putObject(Output.RESULT);
//
//        artistsMonetization.forEach((artistName, monetization) -> {
//           ObjectNode artistNode = resultNode.putObject(artistName);
//
//           artistNode.put(Output.SONG_REVENUE, monetization.getSongRevenue());
//           artistNode.put(Output.MERCH_REVENUE, monetization.getMerchRevenue());
//           artistNode.put(Output.RANKING, monetization.)
//
//        });

        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.END_PROGRAM);
        ObjectNode resultNode = out.putObject(Output.RESULT);

        List<Artist> sortedArtists = library.getMonetizedArtists();

        int index = 1;
        for (Artist artist : sortedArtists) {
            ObjectNode artistNode = resultNode.putObject(artist.getName());

            artistNode.put(Output.SONG_REVENUE, artist.getMonetization().getSongRevenue());
            artistNode.put(Output.MERCH_REVENUE, artist.getMonetization().getMerchRevenue());
            artistNode.put(Output.RANKING, index);
            artistNode.put(Output.MOST_PROFITABLE, artist.getMonetization().getMostProfitableSong());
            index++;
        }

    }
}
