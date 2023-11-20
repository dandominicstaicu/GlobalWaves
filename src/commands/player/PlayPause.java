package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.MainPlayer;
import entities.UserPlayer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PlayPause extends Command {
    @Override
    public String toString() {
        return super.toString() +
                "PlayPause{" +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, MainPlayer player) {
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();
        out.put("command", "playPause");
        out.put("user", getUsername());
        out.put("timestamp", getTimestamp());

        UserPlayer userPlayer = player.getLibrary().getUserWithUsername(getUsername()).getPlayer();

        if (userPlayer.getAudioQueue() != null && !userPlayer.getAudioQueue().isEmpty()) {
            if (userPlayer.getIsPlaying()) {
                userPlayer.pause();
                out.put("message", "Playback paused successfully.");
            } else {
                userPlayer.resume();
                out.put("message", "Playback resumed successfully.");
            }

        } else {
            out.put("message", "Please load a source before attempting to pause or resume playback.");
        }

    }
}
