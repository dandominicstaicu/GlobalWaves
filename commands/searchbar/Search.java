package commands.searchbar;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import commands.Command;
import entities.Playable;
import entities.Player;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Search extends Command {
	private String username;
	private String type;
	private Map<String, Object> filters;

	@Override
	public String toString() {
		return super.toString() +
				"Search{" +
				"username='" + username + '\'' +
				", type='" + type + '\'' +
				", filters=" + filters +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, Player player) {
//        System.out.println(this.toString());
		// chatGPT suggested I write the search function as a method of the Library class
		// my final decision was to write it as a method of the Search class, keeping in mind there might
		// be multiple types of searches in the future
		List<Playable> searchResult = player.getSearchBar().search(player.getLibrary(), this.type, this.filters, getUsername());

//		library.getPlayer().stop();
		player.stop();

		ObjectNode out = outputs.addObject();
//        out.putPOJO("search", searchResult);
		out.put("command", "search");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());
		out.put("message", "Search returned " + searchResult.size() + " results");

		ArrayNode resultsNode = out.putArray("results");
		for (Playable result : searchResult) {
			resultsNode.add(result.getName());
		}
	}
}
