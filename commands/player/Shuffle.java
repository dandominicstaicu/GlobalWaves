package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Library;
import entities.Player;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shuffle extends Command {
    private String username;
    private Integer seed;

    @Override
    public String toString() {
        return super.toString() +
                "Shuffle{" +
                "username='" + username + '\'' +
                ", seed=" + seed +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Player player) {
        System.out.println(this.toString());
    }


}
