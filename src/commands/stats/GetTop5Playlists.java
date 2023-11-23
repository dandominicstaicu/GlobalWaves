package commands.stats;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.playable.Playlist;
import lombok.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetTop5Playlists extends Command {
	@Override
	public String toString() {
		return super.toString() +
				"GetTop5Playlists{}";
	}

	@Override
	public void execute(ArrayNode outputs, Library lib) {
		ObjectNode out = outputs.addObject();
		out.put("command", "getTop5Playlists");
		out.put("timestamp", getTimestamp());

		// chatGPT helped me optimise this part (getting the top5 playlists)
		List<Playlist> sortedPlaylists = lib.getPlaylists().stream()
				.filter(Playlist::getIsPublic) // filter only public playlists
				.sorted(Comparator.comparingInt(Playlist::getFollowers).reversed()) // sort descending by followers
				.limit(5) // get the top 5
				.collect(Collectors.toList()); // convert to list

		ArrayNode resultArray = out.putArray("result");
		for (Playlist playlist : sortedPlaylists)
			resultArray.add(playlist.getName());

	}
}
