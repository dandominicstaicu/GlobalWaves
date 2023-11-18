package commands.player;

import commands.Command;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Forward extends Command {
    private String username;
}
