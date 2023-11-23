package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.UserPlayer;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shuffle extends Command {
	private Integer seed;

	@Override
	public String toString() {
		return super.toString() +
				"Shuffle{" +
				", seed=" + seed +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, Library lib) {
		ObjectNode out = outputs.addObject();
		out.put("command", "shuffle");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());

		UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();

		if (!userPlayer.playingIndexIsValid()) {
			out.put("message", "Please load a source before using the shuffle function.");
		} else if (!userPlayer.getIsPlayingPlaylist()) {
			out.put("message", "The loaded source is not a playlist.");
		} else {
			if (userPlayer.shufflePlaylist(this.getSeed())) {
				out.put("message", "Shuffle function activated successfully.");
			} else {
				out.put("message", "Shuffle function deactivated successfully.");
			}
		}

	}


}
