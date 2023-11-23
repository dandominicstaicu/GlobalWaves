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
public class Backward extends Command {
	@Override
	public String toString() {
		return super.toString() +
				"Backward{" +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, Library lib) {
		ObjectNode out = outputs.addObject();
		out.put("command", "backward");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());

		UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();

		if (!userPlayer.playingIndexIsValid()) {
			out.put("message", "Please select a source before rewinding.");
			// Please load a source before skipping forward.
		} else if (userPlayer.getAudioQueue().get(userPlayer.getPlayingIndex()).isSong()) {
			out.put("message", "The loaded source is not a podcast.");
		} else {
			userPlayer.backward();
			out.put("message", "Rewound successfully.");
		}

	}
}
