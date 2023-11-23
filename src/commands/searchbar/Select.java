package commands.searchbar;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.playable.Playable;
import entities.UserPlayer;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Select extends Command {
	private Integer itemNumber;

	@Override
	public String toString() {
		return super.toString() +
				"Select{" +
				", itemNumber=" + itemNumber +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, Library lib) {
		UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();
		List<Playable> lastSearchResults = userPlayer.getSearchBar().getLastSearchResults();

		ObjectNode out = outputs.addObject();
		out.put("command", "select");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());

		if (lastSearchResults == null) {
			out.put("message", "Please conduct a search before making a selection.");
		} else if (itemNumber < 1 || itemNumber > lastSearchResults.size()) {
			userPlayer.getSearchBar().setLastSearchResults(null);
			userPlayer.getSearchBar().setSelectedResult(null);
			out.put("message", "The selected ID is too high.");
		} else {
			Playable selectedResult = lastSearchResults.get(itemNumber - 1); // Adjust for zero-based index
			out.put("message", "Successfully selected " + selectedResult.getName() + ".");
			userPlayer.getSearchBar().setSelectedResultAndClear(selectedResult);
		}
	}
}
