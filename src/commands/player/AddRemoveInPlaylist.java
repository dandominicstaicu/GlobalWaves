package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.MainPlayer;
import entities.Song;
import entities.UserPlayer;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddRemoveInPlaylist extends Command {
	private Integer playlistId;

	@Override
	public String toString() {
		return super.toString() +
				"AddRemoveInPlaylist{" +
				", playlistId=" + playlistId +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, MainPlayer player) {
//		System.out.println(this.toString());
		ObjectNode out = outputs.addObject();

		out.put("command", "addRemoveInPlaylist");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());

		Library lib = player.getLibrary();
		UserPlayer userPlayer = player.getLibrary().getUserWithUsername(getUsername()).getPlayer();

		if (getPlaylistId() > lib.getPlaylists().size() || getPlaylistId() <= 0) {
			out.put("message", "The specified playlist does not exist.");
//			return;
		} else if (userPlayer.getAudioQueue().isEmpty()) {
			out.put("message", "Please load a source before adding to or removing from the playlist.");
		} else if (!userPlayer.getAudioQueue().element().isSong()) {
			out.put("message", "The loaded source is not a song.");
		} else {
			boolean ret = lib.decideAddRemove(getPlaylistId(), (Song) userPlayer.getAudioQueue().element());
			if (ret) {
				out.put("message", "Successfully added to playlist.");
			} else {
				out.put("message", "Successfully removed from playlist.");
			}
		}
	}
}
