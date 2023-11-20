package commands.playlist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.MainPlayer;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwitchVisibility extends Command {
//    private String username;
    private Integer playlistId;

    @Override
    public String toString() {
        return super.toString() +
                "SwitchVisibility{" +
                ", playlistId=" + playlistId +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, MainPlayer player) {
        System.out.println(this.toString());
    }
}
