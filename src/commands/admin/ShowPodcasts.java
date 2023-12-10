package commands.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Library;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ShowPodcasts extends Command {
    @Override
    public String toString() {
        return super.toString() + "ShowPodcasts{}";
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
        System.out.println(this.toString());
    }
}
