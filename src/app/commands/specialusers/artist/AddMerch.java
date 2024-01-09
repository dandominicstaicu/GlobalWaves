package app.commands.specialusers.artist;

import app.entities.Library;
import app.entities.userside.artist.Artist;
import app.entities.userside.artist.Merch;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.common.Output;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMerch extends CheckExistenceArtist {
    private String name;
    private String description;
    private Double price;

    /**
     * Returns a string representation of the AddMerch command.
     *
     * @return A string describing the AddMerch command.
     */
    @Override
    public String toString() {
        return super.toString() + "AddMerch{"
                + "name='" + name + '\''
                + ", description='" + description + '\''
                + ", price=" + price
                + '}';
    }

    /**
     * Executes the AddMerch command to add merchandise to the artist's profile.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param library The Library where artist data is stored.
     * @param offline A boolean flag indicating if the command is executed offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.ADD_MERCH);

        Artist artist = library.getArtistWithName(getUsername());
        if (validateUserAndArtist(library, out, getUsername())) {
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
