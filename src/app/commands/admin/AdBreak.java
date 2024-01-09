package app.commands.admin;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class AdBreak extends Command {
    private Double price;

    @Override
    public String toString() {
        return super.toString() + "AdBreak{"
                + "price=" + price
                + '}';
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
//        System.out.println(this.toString());

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

//        System.out.print(getTimestamp());
        user.insertAd(library, price);
        out.put(Output.MESSAGE, Output.AD_SUCCESS);

    }
}
