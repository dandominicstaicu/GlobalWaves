package commands.searchbar;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.Playable;
import entities.Player;
import entities.Playlist;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Select extends Command {
	private String username;
	private Integer itemNumber;

	@Override
	public String toString() {
		return super.toString() +
				"Select{" +
				"username='" + username + '\'' +
				", itemNumber=" + itemNumber +
				'}';
	}


	@Override
	public void execute(ArrayNode outputs, Player player) {
//		System.out.println(this.toString());
		List<Playable> lastSearchResults = player.getSearchBar().getLastSearchResults();

		ObjectNode out = outputs.addObject();
		out.put("command", "select");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());

		if (lastSearchResults == null || lastSearchResults.isEmpty()) {
			out.put("message", "Please conduct a search before making a selection.");
		} else if (itemNumber < 1 || itemNumber > lastSearchResults.size()) {
			out.put("message", "The selected ID is too high.");
		} else {
			Playable selectedResult = lastSearchResults.get(itemNumber - 1); // Adjust for zero-based index
			out.put("message", "Successfully selected " + selectedResult.getName() + ".");
			player.getSearchBar().setSelectedResult(selectedResult);
		}
	}
}
