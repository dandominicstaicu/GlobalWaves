package commands.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Library;
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
        System.out.println(this.toString());
    }
}
