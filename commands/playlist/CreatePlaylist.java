package commands.playlist;

import commands.Command;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePlaylist extends Command {
    private String username;
    private String playlistName;
}
