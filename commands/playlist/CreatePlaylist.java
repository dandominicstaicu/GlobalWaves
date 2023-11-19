package commands.playlist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.MainPlayer;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePlaylist extends Command {
	//    private String username;
	private String playlistName;

	@Override
	public String toString() {
		return super.toString() +
				"CreatePlaylist{" +
				", playlistName='" + playlistName + '\'' +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, MainPlayer player) {
//        System.out.println(this.toString());

		ObjectNode out = outputs.addObject();
		out.put("command", "createPlaylist");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());

		boolean ret = player.getLibrary().createPlaylist(this.getPlaylistName(), this.getUsername());
		if (ret) {
			out.put("message", "Playlist created successfully.");
		} else {
			out.put("message", "A playlist with the same name already exists.");
		}
	}
}
