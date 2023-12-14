package app.commands.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.User;
import app.entities.userside.UserFactory;
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
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.ADD_USER);

        User user = library.getFromAllUsers(getUsername());
        if (user != null) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.USER_ALREADY_TAKEN);
        } else {
            User newUser = UserFactory.createUser(getUsername(), getAge(), getCity(), getType());

            newUser.addUser(library);

            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.USER_ADDED);
        }
    }
}
