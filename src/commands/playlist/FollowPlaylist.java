package commands.playlist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.*;
import entities.playable.Playable;
import entities.playable.Playlist;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FollowPlaylist extends Command {
	@Override
	public String toString() {
		return super.toString() +
				"FollowPlaylist{" +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, Library lib) {
		ObjectNode out = outputs.addObject();
		out.put("command", "follow");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());

		UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();
		Playable selected = userPlayer.getSearchBar().getSelectedResult();

		if (selected == null) {
			out.put("message", "Please select a source before following or unfollowing.");
		} else if (!selected.isPlaylist()) {
			out.put("message", "The selected source is not a playlist.");
		} else if (((Playlist) selected).getOwner().equals(getUsername())) {
			out.put("message", "You cannot follow or unfollow your own playlist.");
		} else {
			if (lib.getUserWithUsername(getUsername()).followUnfollowPlaylist((Playlist) selected)) {
				out.put("message", "Playlist followed successfully.");
			} else {
				out.put("message", "Playlist unfollowed successfully.");
			}
		}
	}
}
