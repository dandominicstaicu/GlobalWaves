package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.MainPlayer;
import entities.UserPlayer;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Forward extends Command {
    @Override
    public String toString() {
        return super.toString() +
                "Forward{" +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, MainPlayer player) {
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();
        out.put("command", "forward");
        out.put("user", getUsername());
        out.put("timestamp", getTimestamp());

        UserPlayer userPlayer = player.getLibrary().getUserWithUsername(getUsername()).getPlayer();

        if (!userPlayer.playingIndexIsValid()) {
            out.put("message", "Please load a source before attempting to forward.");
        } else if (userPlayer.getAudioQueue().get(userPlayer.getPlayingIndex()).isSong()) {
            out.put("message", "The loaded source is not a podcast.");
        } else {
            userPlayer.forward();
            out.put("message", "Skipped forward successfully.");
        }

    }
}
