package app.commands.admin;

import app.commands.Command;
import app.entities.userside.artist.Monetization;
import app.common.Constants;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.artist.Artist;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

public class EndProgram extends Command {
    /**
     * Returns a string representation of this EndProgram object, including the
     * superclass' string representation.
     *
     * @return A string representation of this EndProgram object.
     */
    @Override
    public String toString() {
        return super.toString() + "EndProgram{}";
    }

    /**
     * Executes the EndProgram command, finalizing the program execution.
     * This method calculates and displays revenue information for artists and resets premium
     * user status.
     *
     * @param outputs  The ArrayNode where the output will be added.
     * @param library  The Library object containing user and artist data.
     * @param offline  A boolean indicating whether the user is offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.END_PROGRAM);
        ObjectNode resultNode = out.putObject(Output.RESULT);

        // do the splitting of the users that are still premium
        List<NormalUser> normalUsers = library.getUsers();
        for (NormalUser normalUser : normalUsers) {
            if (normalUser.getIsPremium()) {
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
            Double displaySongRevenue =
                    Math.round(songRevenue * Constants.DISPLAY_CONST) / Constants.DISPLAY_CONST;

            Double merchRevenue = monetization.getMerchRevenue();
            Double displayMerchRevenue =
                    Math.round(merchRevenue * Constants.DISPLAY_CONST) / Constants.DISPLAY_CONST;

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
