package commands.player;

import commands.Command;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shuffle extends Command {
    private String username;
    private Integer seed;
}
