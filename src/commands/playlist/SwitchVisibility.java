package commands.playlist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.playable.Playlist;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwitchVisibility extends Command {
	private Integer playlistId;

	@Override
	public String toString() {
		return super.toString() +
				"SwitchVisibility{" +
				", playlistId=" + playlistId +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, Library lib) {
		ObjectNode out = outputs.addObject();
		out.put("command", "switchVisibility");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());

		List<Playlist> userSeenPlaylists = lib.getUserWithUsername(getUsername()).getPlaylistsOwnedByUser(lib.getPlaylists());

		if (getPlaylistId() > userSeenPlaylists.size() || getPlaylistId() <= 0) {
			out.put("message", "The specified playlist ID is too high.");
		} else {
			Playlist playlist = userSeenPlaylists.get(getPlaylistId() - 1); // Adjust for zero-based index
			boolean visibility = playlist.switchVisibility();
			out.put("message", "Visibility status updated successfully to " + (visibility ? "public" : "private") + ".");
		}
	}
}
