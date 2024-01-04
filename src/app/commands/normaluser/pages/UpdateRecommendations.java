package app.commands.normaluser.pages;

import app.commands.Command;
import app.entities.Library;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateRecommendations extends Command {
    private String recommendationType;

    @Override
    public String toString() {
        return super.toString() + "UpdateRecommendations{"
                + "recommendationType='" + recommendationType + '\''
                + '}';
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        System.out.println(this.toString());
    }
}
