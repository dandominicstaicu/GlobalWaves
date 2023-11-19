package commands.playlist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.MainPlayer;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowPlaylist extends Command {
//	private String username;

	@Override
	public String toString() {
		return super.toString() +
				"ShowPlaylist{" +
				'}';
	}

	@Override
	public void execute(ArrayNode outputs, MainPlayer player) {
		System.out.println(this.toString());
	}
}
