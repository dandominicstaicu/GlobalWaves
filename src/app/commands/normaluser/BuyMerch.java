package app.commands.normaluser;

import app.commands.Command;
import app.entities.userside.artist.Monetization;
import app.common.Output;
import app.common.PageTypes;
import app.entities.Library;
import app.entities.userside.User;
import app.entities.userside.artist.Artist;
import app.entities.userside.artist.Merch;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.pages.Page;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BuyMerch extends Command {
    private String name;

    /**
     * Returns a string representation of this BuyMerch object, including the
     * merchandise name.
     *
     * @return A string representation of this BuyMerch object.
     */
    @Override
    public String toString() {
        return super.toString() + "BuyMerch{"
                + "name='" + name + '\''
                + '}';
    }

    /**
     * Executes the BuyMerch command, allowing a user to purchase merchandise from an artist's page
     * This method checks if the user and merchandise exist, if the current page is an artist page,
     * and then processes the purchase by deducting the price from the artist's monetization and
     * adding the merchandise to the user's purchased items.
     * It also adds an appropriate message to the output.
     *
     * @param outputs  The ArrayNode where the output will be added.
     * @param library  The Library object containing user and artist data.
     * @param offline  A boolean indicating whether the user is offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {

        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.BUY_MERCH);

        NormalUser user = library.getUserWithUsername(getUsername());
        if (user == null) {
            out.put(Output.MESSAGE, "The username " + getUsername() + " doesn't exist.");
            return;
        }

        Page currentPage = user.getCurrentPage();
        if (currentPage.getPageType() != PageTypes.ARTIST_PAGE) {
            out.put(Output.MESSAGE, Output.MERCH_BUY_ERR);
            return;
        }

        User specialUser = currentPage.getOwner();
        Artist artist = library.getArtistWithName(specialUser.getUsername());

        Merch merch = artist.getMerchWithName(getName());
        if (merch == null) {
            out.put(Output.MESSAGE, "The merch " + getName() + " doesn't exist.");
            return;
        }

        Monetization monetization = artist.getMonetization();
        monetization.payMerch(merch.getPrice());
        user.buyMerch(merch);

        out.put(Output.MESSAGE, getUsername() + Output.MERCH_BUY_SUCCESS);
    }
}
