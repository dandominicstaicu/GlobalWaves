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

		switch (userPlayer.getIsRepeating()) {
			case NO_REPEAT -> stats.put("repeat", "No Repeat");
			case REPEAT_ONCE -> stats.put("repeat", "Repeat Once");
			case REPEAT_ALL -> stats.put("repeat", "Repeat All");
			case REPEAT_CURRENT_SONG -> stats.put("repeat", "Repeat Current Song");
			case REPEAT_INFINITE -> stats.put("repeat", "Repeat Infinite");
		}

		stats.put("shuffle", userPlayer.getIsShuffled());
		stats.put("paused", !userPlayer.getIsPlaying());
	}

}
