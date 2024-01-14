package app.commands.normaluser.pages;

import app.commands.Command;
import app.common.Output;
import app.common.UpdateRecommend;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateRecommendations extends Command {
    private String recommendationType;

    /**
     * Returns a string representation of this UpdateRecommendations object,
     * including the superclass
     * string representation.
     *
     * @return A string representation of this UpdateRecommendations object.
     */
    @Override
    public String toString() {
        return super.toString() + "UpdateRecommendations{"
                + "recommendationType='" + recommendationType + '\''
                + '}';
    }

    /**
     * Executes the UpdateRecommendations command, updating recommendations for the user.
     * This method verifies the user's existence, updates recommendations based on the
     * recommendation type, and provides appropriate feedback.
     *
     * @param outputs  The ArrayNode where the output will be added.
     * @param library  The Library object containing user data.
     * @param offline  A boolean indicating whether the user is offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.UPDATE_RECOMMENDATIONS);

        NormalUser user = library.getUserWithUsername(getUsername());
        if (user == null) {
            out.put(Output.MESSAGE, "The username " + getUsername() + " doesn't exist.");
            return;
        }

        boolean result = user.updateRecommendations(transformType(getRecommendationType()));

        if (result) {
            out.put(Output.MESSAGE, "The recommendations for user " + getUsername()
                    + " have been updated successfully.");
        } else {
            out.put(Output.MESSAGE, Output.NO_NEW_RECOMMENDATION);
        }
    }

    private UpdateRecommend transformType(final String type) {
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
