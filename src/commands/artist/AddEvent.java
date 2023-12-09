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
public class AddEvent extends Command {
    private String name;
    private String description;
    private String date;

    @Override
    public String toString() {
        return super.toString() + "AddEvent{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Library library) {
        System.out.println(this.toString());
    }
}
