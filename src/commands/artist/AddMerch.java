package commands.artist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import common.UserTypes;
import entities.Library;
import entities.user.side.Merch;
import entities.user.side.User;
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

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
        System.out.println(this.toString());
//        ObjectNode out = outputs.addObject();
//
//        out.put(Output.COMMAND, Output.ADD_MERCH);
//        out.put(Output.USER, getUsername());
//        out.put(Output.TIMESTAMP, getTimestamp());
//
//        User user = library.getFromAllUsers(getUsername());
//        if (user == null) {
//            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.DOESNT_EXIST);
//            return;
//        }
//
//        if (user.getUserType() != UserTypes.ARTIST) {
//            out.put(Output.MESSAGE, getUsername() + Output.NOT_ARTIST);
//            return;
//        }
//
//        Artist artist = (Artist) user;
//
//        if (artist.getMerches().stream().anyMatch(merch -> merch.getName().equals(getName()))) {
//            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.MERCH_ALREADY_EXISTS);
//            return;
//        }
//
//        if (getPrice() < 0) {
//            out.put(Output.MESSAGE, Output.NEGATIVE_PRICE_MERCH);
//            return;
//        }
//
//        Merch newMerch = new Merch(getName(), getDescription(), getPrice());
//        artist.addMerch(newMerch);
    }
}
