package commands.normal.user.pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Library;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePage extends Command {
    private String nextPage;

    @Override
    public String toString() {
        return "ChangePage{"
                + "nextPage='" + nextPage + '\''
                + '}';
    }

    @Override
    public void execute(final ArrayNode outputs, final Library lib) {
        System.out.println(this.toString());
    }
}
