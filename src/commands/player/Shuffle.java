package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.MainPlayer;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shuffle extends Command {
//    private String username;
    private Integer seed;

    @Override
    public String toString() {
        return super.toString() +
                "Shuffle{" +
                ", seed=" + seed +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, MainPlayer player) {
        System.out.println(this.toString());
    }


}
