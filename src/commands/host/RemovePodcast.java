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
public class RemovePodcast extends Command {
    private String name;

    @Override
    public String toString() {
        return "RemovePodcast{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
        System.out.println(this.toString());
    }
}
