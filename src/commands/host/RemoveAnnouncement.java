package commands.host;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Library;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveAnnouncement extends Command {
    private String name;

    @Override
    public String toString() {
        return super.toString() + "RemoveAnnouncement{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Library library) {
        System.out.println(this.toString());
    }

}
