package commands.playlist;

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
public class SwitchVisibility extends Command {
    private String username;
    private Integer playlistId;

    @Override
    public String toString() {
        return super.toString() +
                "SwitchVisibility{" +
                "username='" + username + '\'' +
                ", playlistId=" + playlistId +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Player player) {
        System.out.println(this.toString());
    }
}
