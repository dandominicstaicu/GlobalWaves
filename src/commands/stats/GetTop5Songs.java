package commands.stats;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.playable.audio_files.Song;
import lombok.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetTop5Songs extends Command {
	@Override
	public String toString() {
		return super.toString() +
				"GetTop5Songs{}";
	}

	@Override
	public void execute(ArrayNode outputs, Library lib) {
		ObjectNode out = outputs.addObject();
		out.put("command", "getTop5Songs");
		out.put("timestamp", getTimestamp());


		// chatGPT helped me optimise this part (getting the top5 songs)
		List<Song> sortedSongs = lib.getSongs().stream()
				.sorted(Comparator.comparingInt(Song::getLikes).reversed()) // sort descending by likes
				.limit(5)
				.collect(Collectors.toList()); // convert to list

		ArrayNode resultArray = out.putArray("result");
		for (Song song : sortedSongs)
			resultArray.add(song.getName());
	}

}
