package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.MainPlayer;
import entities.User;
import entities.UserPlayer;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Next extends Command {
    @Override
    public String toString() {
        return super.toString() +
                "Next{" +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, MainPlayer player) {
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();
        out.put("command", "next");
        out.put("user", getUsername());
        out.put("timestamp", getTimestamp());

        UserPlayer userPlayer = player.getLibrary().getUserWithUsername(getUsername()).getPlayer();

        if (!userPlayer.playingIndexIsValid()) {
            out.put("message", "Please load a source before skipping to the next track.");
        } else {
            userPlayer.next(true);
            out.put("message", "Skipped to next track successfully. The current track is " + userPlayer.getAudioQueue().get(userPlayer.getPlayingIndex()).getName() + ".");
        }


    }
}
