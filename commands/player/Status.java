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
public class Status extends Command {
//    private String username;

//    @Override
//    public String toString() {
//        return super.toString() +
//                "Status{" +
//                "username='" + username + '\'' +
//                '}';
//    }

    @Override
    public void execute(ArrayNode outputs, MainPlayer player) {
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();
        out.put("command", "status");
        out.put("user", getUsername());
        out.put("timestamp", getTimestamp());

        UserPlayer userPlayer = player.getLibrary().getUserWithUsername(getUsername()).getPlayer();
        ObjectNode stats = out.putObject("stats");

        if (userPlayer.getAudioQueue() != null && !userPlayer.getAudioQueue().isEmpty()) {
            //if (!userPlayer.getAudioQueue().isEmpty())
            stats.put("name", userPlayer.getAudioQueue().element().getName()); // element is peek() but doesn not return null
            stats.put("remainedTime", userPlayer.getRemainedTime());
        } else {
            stats.put("name", "");
            stats.put("remainedTime", 0);
        }

        stats.put("repeat", userPlayer.getIsRepeating() ? "Repeat" : "No Repeat");
        stats.put("shuffle", userPlayer.getIsShuffled());
        stats.put("paused", !userPlayer.getIsPlaying());

    }
}
