package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.UserPlayer;
import entities.playable.audio_files.Song;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Like extends Command {
	@Override
	public String toString() {
		return super.toString() +
				"Like{" +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, Library lib) {
		ObjectNode out = outputs.addObject();

		out.put("command", "like");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());

		UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();

		if (!userPlayer.playingIndexIsValid()) {
			out.put("message", "Please load a source before liking or unliking.");
		} else if (!userPlayer.getAudioQueue().get(userPlayer.getPlayingIndex()).isSong()) {
			out.put("message", "The loaded source is not a song.");
		} else {
			if (lib.getUserWithUsername(getUsername()).likeUnlikeSong((Song) userPlayer.getAudioQueue().get(userPlayer.getPlayingIndex()))) {
				out.put("message", "Like registered successfully.");
			} else {
				out.put("message", "Unlike registered successfully.");
			}
		}
	}
}
