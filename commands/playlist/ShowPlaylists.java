package commands.playlist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.*;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowPlaylists extends Command {
//	private String username;

	@Override
	public String toString() {
		return super.toString() +
				"ShowPlaylist{" +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, MainPlayer player) {
//		System.out.println(this.toString());
		ObjectNode out = outputs.addObject();

		out.put("command", "showPlaylists");
		out.put("user", getUsername());
		out.put("timestamp", getTimestamp());

		Library lib = player.getLibrary();

		// chatGPT helped me write this part (the output of JSON)
		ArrayNode resultArray = out.putArray("result");
		for (Playlist playlist : lib.getPlaylists()) {
			ObjectNode playlistJson = resultArray.addObject();
			playlistJson.put("name", playlist.getName());

			ArrayNode songsArray = playlistJson.putArray("songs");
			for (Song song : playlist.getSongs()) {
				songsArray.add(song.getName());
			}

			playlistJson.put("visibility", playlist.getIsPublic() ? "public" : "private");
			playlistJson.put("followers", playlist.getFollowers());


		}


	}
}
