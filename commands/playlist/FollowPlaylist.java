package commands.playlist;

import commands.Command;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowPlaylist extends Command {
	private String username;
}
