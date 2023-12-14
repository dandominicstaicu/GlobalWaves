package app.commands.specialusers.artist;

import app.entities.Library;
import app.entities.userside.User;
import app.entities.userside.artist.Artist;
import app.entities.userside.artist.Merch;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMerch extends Command {
    private String name;
    private String description;
    private Integer price;

    @Override
    public String toString() {
        return super.toString() + "AddMerch{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }


    //TODO code here alike to addEvent. REFACTOR (maybe merge add event with add merch)
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.ADD_MERCH);

        User user = library.searchAllUsersForUsername(getUsername());
        if (user == null) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.DOESNT_EXIST);
            return;
        }

        Artist artist = library.getArtistWithName(getUsername());

        if (artist == null) {
            out.put(Output.MESSAGE, getUsername() + Output.NOT_ARTIST);
            return;
        }

        if (artist.getMerchList().stream().anyMatch(merch -> merch.getName().equals(getName()))) {
            out.put(Output.MESSAGE, getUsername() + Output.MERCH_ALREADY_EXISTS);
            return;
        }

        if (getPrice() < 0) {
            out.put(Output.MESSAGE, Output.NEGATIVE_PRICE_MERCH);
            return;
        }

        Merch newMerch = new Merch(getName(), getDescription(), getPrice());
        artist.addMerch(newMerch);

        out.put(Output.MESSAGE, getUsername() + Output.MERCH_ADD_SUCCESS);
    }
}
