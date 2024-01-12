package app.commands.normaluser.pages;

import app.commands.Command;
import app.common.Output;
import app.common.UpdateRecommend;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.UPDATE_RECOMMENDATIONS);

        out.put(Output.MESSAGE, "The recommendations for user " + getUsername() + " have been updated successfully.");

        NormalUser user = library.getUserWithUsername(getUsername());

        user.updateRecommendations(transformType(getRecommendationType()));
    }

    private UpdateRecommend transformType(String type) {
        switch (type) {
            case "random_song" -> {
                return UpdateRecommend.RANDOM_SONG;
            }
            case "random_playlist" -> {
                return UpdateRecommend.RANDOM_PLAYLIST;
            }
            case "fans_playlist" -> {
                return UpdateRecommend.FANS_PLAYLIST;
            }
            default -> {
                return UpdateRecommend.ERROR;
            }
        }
    }
}
