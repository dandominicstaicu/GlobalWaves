package commands.searchbar;

import commands.Command;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Search extends Command {
    private String type;
    private Map<String, Object> filters;
}
