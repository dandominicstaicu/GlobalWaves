package commands.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Library;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class DeleteUser extends Command {
    @Override
    public String toString() {
        return "DeleteUser{}";
    }

    @Override
    public void execute(ArrayNode outputs, Library library) {
        System.out.println(this.toString());
    }
}
