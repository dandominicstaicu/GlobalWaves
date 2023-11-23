package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.UserPlayer;
import lombok.*;

@Getter
@Setter
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
    public void execute(ArrayNode outputs, Library lib) {
        ObjectNode out = outputs.addObject();
        out.put("command", "next");
        out.put("user", getUsername());
        out.put("timestamp", getTimestamp());

        UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();

        if (userPlayer.playingIndexIsValid()) {
            userPlayer.next(true, getTimestamp());
        }

        if (!userPlayer.playingIndexIsValid()) {
            out.put("message", "Please load a source before skipping to the next track.");
        } else {
            out.put("message", "Skipped to next track successfully. The current track is " + userPlayer.getAudioQueue().get(userPlayer.getPlayingIndex()).getName() + ".");
        }


    }
}