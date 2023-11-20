package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.MainPlayer;
import entities.UserPlayer;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like extends Command {
    @Override
    public String toString() {
        return super.toString() +
                "Like{" +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, MainPlayer player) {
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();

        out.put("command", "like");
        out.put("user", getUsername());
        out.put("timestamp", getTimestamp());

        Library lib = player.getLibrary();
        UserPlayer userPlayer = player.getLibrary().getUserWithUsername(getUsername()).getPlayer();

        if (userPlayer.getAudioQueue().isEmpty()) {
            out.put("message", "Please load a source before liking or unliking.");
        } else if (!userPlayer.getAudioQueue().element().isSong()) {
            out.put("message", "The loaded source is not a song.");
        } else {
            if (lib.getUserWithUsername(getUsername()).likeUnlikeSong((entities.Song) userPlayer.getAudioQueue().element())) {
                out.put("message", "Like registered successfully.");
            } else {
                out.put("message", "Unlike registered successfully.");
            }
        }
    }
}
