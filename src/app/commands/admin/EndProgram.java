package app.commands.admin;

import app.commands.Command;
import app.commands.specialusers.artist.Monetization;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.artist.Artist;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EndProgram extends Command {
    @Override
    public String toString() {
        return super.toString() + "EndProgram{}";
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
//        System.out.println(this.toString());

        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.END_PROGRAM);
        ObjectNode resultNode = out.putObject(Output.RESULT);

        // do the splitting of the users that are still premium
        List<NormalUser> normalUsers = library.getUsers();
        for (NormalUser normalUser : normalUsers) {
            if (normalUser.getIsPremium()) {
//                System.out.println("premium user name: " + normalUser.getUsername());
                normalUser.payPremiumArtist(library);
                normalUser.setIsPremium(false);
            }
        }

        List<Artist> sortedArtists = library.getMonetizedArtists();

        int index = 1;
        for (Artist artist : sortedArtists) {
            ObjectNode artistNode = resultNode.putObject(artist.getName());

            Monetization monetization = artist.getMonetization();

            Double songRevenue = monetization.getSongRevenue();
            Double displaySongRevenue = Math.round(songRevenue * 100.0) / 100.0;

            Double merchRevenue = monetization.getMerchRevenue();
            Double displayMerchRevenue = Math.round(merchRevenue * 100.0) / 100.0;

            monetization.decideMostProfitableSong();

            String mostProfitableSong = monetization.getMostProfitableSong();
            if (mostProfitableSong == null) {
                mostProfitableSong = "N/A";
            }

            artistNode.put(Output.SONG_REVENUE, displaySongRevenue);
            artistNode.put(Output.MERCH_REVENUE, displayMerchRevenue);
            artistNode.put(Output.RANKING, index);
            artistNode.put(Output.MOST_PROFITABLE, mostProfitableSong);

            index++;
        }
    }
}
