package commands.playlist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Player;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowPlaylist extends Command {
	private String username;

	@Override
	public String toString() {
		return super.toString() +
				"FollowPlaylist{" +
				"username='" + username + '\'' +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, Player player) {
		System.out.println(this.toString());
	}
}
