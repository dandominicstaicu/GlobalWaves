package commands.normal.user.pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Library;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class PrintCurrentPage extends Command {

    @Override
    public String toString() {
        return super.toString() + "PrintCurrentPage{}";
    }

    @Override
    public void execute(ArrayNode outputs, Library library) {
        System.out.println(this.toString());
    }
}
