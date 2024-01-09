package app.commands.normaluser;

import app.commands.Command;
import app.commands.specialusers.artist.Monetization;
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

    @Override
    public String toString() {
        return super.toString() + "BuyMerch{"
                + "name='" + name + '\''
                + '}';
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
//        System.out.println(this.toString());

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
