package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.playable.Playable;
import entities.UserPlayer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Load extends Command {
	@Override
	public String toString() {
		return super.toString() +
				"Load{" +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, Library lib) {
		ObjectNode out = outputs.addObject();
		out.put("command", "load");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());

		UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();
		Playable selectedResult = userPlayer.getSearchBar().getSelectedResult();




		if (selectedResult == null) {
			out.put("message", "Please select a source before attempting to load.");
		} else {
			// Assuming you have a method in Player to handle loading
			boolean loadSuccess = userPlayer.loadSource(selectedResult, this.getTimestamp(), userPlayer);

			if (loadSuccess) {
				out.put("message", "Playback loaded successfully.");
			} else {
				out.put("message", "You can't load an empty audio collection!");
			}
		}

	}
}
