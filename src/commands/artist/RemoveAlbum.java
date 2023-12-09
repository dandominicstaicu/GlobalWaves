package commands.artist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Library;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveAlbum extends Command {
    private String name;

    @Override
    public String toString() {
        return super.toString() + "RemoveAlbum{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Library library) {
        System.out.println(this.toString());
    }
}
