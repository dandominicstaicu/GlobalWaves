package commands.normal.user;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Library;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class SwitchConnectionStatus extends Command {
    @Override
    public String toString() {
        return super.toString() + "SwitchConnectionStatus{}";
    }

    @Override
    public void execute(ArrayNode outputs, Library library) {
        System.out.println(this.toString());
    }

}
