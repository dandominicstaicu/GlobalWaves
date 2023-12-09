package commands.general.stats;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Library;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class GetTop5Artists extends Command {
    @Override
    public String toString() {
        return super.toString() + "GetTop5Artists{}";
    }

    @Override
    public void execute(ArrayNode outputs, Library library) {
        System.out.println(this.toString());
    }


}
