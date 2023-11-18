package commands.playlist;

import commands.Command;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowPlaylist extends Command {
	private String username;
}
