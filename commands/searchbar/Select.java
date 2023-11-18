package commands.searchbar;

import commands.Command;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Select extends Command {
    private String username;
    private Integer itemNumber;
}
