package app.commands.admin;

import app.commands.Command;
import app.entities.Library;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class AdBreak extends Command {
    private Integer price;

    @Override
    public String toString() {
        return super.toString() + "AdBreak{"
                + "price=" + price
                + '}';
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        System.out.println(this.toString());
    }
}
