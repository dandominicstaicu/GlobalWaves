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
public class AddAnnouncement extends Command {
    private String name;
    private String description;

    @Override
    public String toString() {
        return super.toString() + "AddAnnouncement{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
        System.out.println(this.toString());
    }
}
