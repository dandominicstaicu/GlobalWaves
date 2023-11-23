package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.UserPlayer;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status extends Command {
	@Override
	public String toString() {
		return super.toString() +
				"Status{" +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, Library lib) {
		ObjectNode out = outputs.addObject();
		out.put("command", "status");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());

		UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();
		ObjectNode stats = out.putObject("stats");

		if (userPlayer.getAudioQueue() != null && userPlayer.playingIndexIsValid()) {
			stats.put("name", userPlayer.getAudioQueue().get(userPlayer.getPlayingIndex()).getName()); // element is peek() but doesn not return null
			stats.put("remainedTime", userPlayer.getRemainedTime());
		} else {
			stats.put("name", "");
			stats.put("remainedTime", 0);
		}

		if (userPlayer.getIsPlayingPlaylist()) {
			switch (userPlayer.getIsRepeating()) {
				case 0:
					stats.put("repeat", "No Repeat");
					break;
				case 1:
					stats.put("repeat", "Repeat All");
					break;
				case 2:
					stats.put("repeat", "Repeat Current Song");
					break;
				default:
					break;
			}
		} else {
			switch (userPlayer.getIsRepeating()) {
				case 0:
					stats.put("repeat", "No Repeat");
					break;
				case 1:
					stats.put("repeat", "Repeat Once");
					break;
				case 2:
					stats.put("repeat", "Repeat Infinite");
					break;
				default:
					break;
			}
		}

		stats.put("shuffle", userPlayer.getIsShuffled());
		stats.put("paused", !userPlayer.getIsPlaying());
	}

}
