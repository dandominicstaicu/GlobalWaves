package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Player;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddRemoveInPlaylist extends Command {
    private String username;
    private Integer playlistId;

    @Override
    public String toString() {
        return super.toString() +
                "AddRemoveInPlaylist{" +
                "username='" + username + '\'' +
                ", playlistId=" + playlistId +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Player player) {
        System.out.println(this.toString());
    }
}
