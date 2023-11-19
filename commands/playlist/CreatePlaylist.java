package commands.playlist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.MainPlayer;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePlaylist extends Command {
//    private String username;
    private String playlistName;

    @Override
    public String toString() {
        return super.toString() +
                "CreatePlaylist{" +
                ", playlistName='" + playlistName + '\'' +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, MainPlayer player) {
//        System.out.println(this.toString());


    }
}
