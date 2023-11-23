package commands.playlist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePlaylist extends Command {
	private String playlistName;

	@Override
	public String toString() {
		return super.toString() +
				"CreatePlaylist{" +
				", playlistName='" + playlistName + '\'' +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, Library lib) {
		ObjectNode out = outputs.addObject();
		out.put("command", "createPlaylist");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());

		boolean ret = lib.createPlaylist(this.getPlaylistName(), this.getUsername());
		if (ret) {
			out.put("message", "Playlist created successfully.");
		} else {
			out.put("message", "A playlist with the same name already exists.");
		}
	}
}
