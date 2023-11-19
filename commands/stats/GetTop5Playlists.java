package commands.stats;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Library;
import entities.Player;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetTop5Playlists extends Command {
	@Override
	public String toString() {
		return super.toString() +
				"GetTop5Playlists{}";
	}

	@Override
	public void execute(ArrayNode outputs, Player player) {
		System.out.println(this.toString());
	}
}
