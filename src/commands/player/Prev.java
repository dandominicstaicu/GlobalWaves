package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.playable.audio_files.AudioFile;
import entities.Library;
import entities.UserPlayer;
import lombok.*;

@Getter
@Setter
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
    public void execute(ArrayNode outputs, Library lib) {
        ObjectNode out = outputs.addObject();
        out.put("command", "prev");
        out.put("user", getUsername());
        out.put("timestamp", getTimestamp());

        UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();

        if (!userPlayer.playingIndexIsValid()) {
            out.put("message", "Please load a source before returning to the previous track.");
        } else {
            AudioFile previous = userPlayer.prev(getTimestamp());
            out.put("message", "Returned to previous track successfully. The current track is " + previous.getName() + ".");
        }
    }
}
