package commands.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Library;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ShowAlbums extends Command {
    @Override
    public String toString() {
        return super.toString() + "ShowAlbums{}";
    }

    @Override
    public void execute(ArrayNode outputs, Library library) {
        System.out.println(this.toString());
    }
}
