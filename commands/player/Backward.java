package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.MainPlayer;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Backward extends Command {
//    private String username;
//
//    @Override
//    public String toString() {
//        return super.toString() +
//                "Backward{" +
//                "username='" + username + '\'' +
//                '}';
//    }

    @Override
    public void execute(ArrayNode outputs, MainPlayer player) {
        System.out.println(this.toString());
    }
}
