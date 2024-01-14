package app.commands.admin;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
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
public class AdBreak extends Command {
    private Double price;

    /**
     * Returns a string representation of this AdBreak object, including the superclass'
     * string representation.
     *
     * @return A string representation of this AdBreak object.
     */
    @Override
    public String toString() {
        return super.toString() + "AdBreak{"
                + "price=" + price
                + '}';
    }

    /**
     * Executes the AdBreak command, inserting an advertisement into the user's playlist.
     * This method verifies the user's existence, checks if the user is playing music, and inserts
     * the ad accordingly.
     *
     * @param outputs  The ArrayNode where the output will be added.
     * @param library  The Library object containing user data.
     * @param offline  A boolean indicating whether the user is offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {

        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.AD_BREAK);

        NormalUser user = library.getUserWithUsername(getUsername());
        if (user == null) {
            out.put(Output.MESSAGE, "The username " + getUsername() + " doesn't exist.");
            return;
        }

        if (!user.getPlayer().getIsPlaying()) {
            out.put(Output.MESSAGE, getUsername() + Output.NOT_PLAYING);
            return;
        }

        user.insertAd(library, price);
        out.put(Output.MESSAGE, Output.AD_SUCCESS);
    }
}
