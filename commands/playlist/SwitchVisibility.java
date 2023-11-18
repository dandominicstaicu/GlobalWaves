package commands.playlist;

import commands.Command;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwitchVisibility extends Command {
    private String username;
    private Integer playlistId;
}
