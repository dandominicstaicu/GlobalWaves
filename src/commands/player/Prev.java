package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.AudioFile;
import entities.MainPlayer;
import entities.User;
import entities.UserPlayer;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prev extends Command {
    @Override
    public String toString() {
        return super.toString() +
                "Prev{" +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, MainPlayer player) {
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();
        out.put("command", "prev");
        out.put("user", getUsername());
        out.put("timestamp", getTimestamp());

        UserPlayer userPlayer = player.getLibrary().getUserWithUsername(getUsername()).getPlayer();

//        System.out.println(getTimestamp());
        if (!userPlayer.playingIndexIsValid()) {
            out.put("message", "Please load a source before returning to the previous track.");
        } else {
            AudioFile previous = userPlayer.prev();
            out.put("message", "Returned to previous track successfully. The current track is " + previous.getName() + ".");
        }
    }
}
