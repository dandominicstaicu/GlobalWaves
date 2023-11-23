package commands.stats;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.playable.audio_files.Song;
import lombok.*;

import java.util.List;

@Getter
@Setter

@AllArgsConstructor
@Builder
public class ShowPreferredSongs extends Command {
	@Override
	public String toString() {
		return super.toString() + "ShowPreferredSongs{" + '}';
	}

	@Override
	public void execute(ArrayNode outputs, Library lib) {
		ObjectNode out = outputs.addObject();

		out.put("command", "showPreferredSongs");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());

		List<Song> favoriteSongs = lib.getUserWithUsername(getUsername()).getFavoriteSongs();

		ArrayNode resultArray = out.putArray("result");
		for (Song song : favoriteSongs) {
			resultArray.add(song.getName());
		}

	}
}
