package app.commands.normaluser;

import app.commands.Command;
import app.entities.Library;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BuyMerch extends Command {
    private String name;

    @Override
    public String toString() {
        return super.toString() + "BuyMerch{"
                + "name='" + name + '\''
                + '}';
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        System.out.println(this.toString());
    }
}
