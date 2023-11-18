package commands.stats;

import commands.Command;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowPreferredSongs extends Command {
	private String username;
}
