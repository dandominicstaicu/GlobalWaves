package commands.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import common.UserTypes;
import entities.Library;
import entities.user.side.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddUser extends Command {
    private String type;
    private Integer age;
    private String city;

    @Override
    public String toString() {
        return super.toString() + "AddUser{" +
                "type='" + type + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.ADD_USER);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        User user = library.getFromAllUsers(getUsername());
        if (user != null) {
            out.put(Output.MESSAGE, getUsername() + Output.USER_ALREADY_TAKEN);
        } else {
//            User newUser = new User(getUsername(), getAge(), getCity(), UserTypes.fromString(getType()));
            User newUser = switch (getType().toLowerCase()) {
                case "normal" -> new NormalUser(getUsername(), getAge(), getCity());
                case "artist" -> new Artist(getUsername(), getAge(), getCity());
                case "host" -> new Host(getUsername(), getAge(), getCity());
                default -> null;
            };
            library.addUser(newUser);

            out.put(Output.MESSAGE, getUsername() + Output.USER_ADDED);
        }
    }
}
