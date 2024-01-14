package app.commands.normaluser.pages;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.playable.Searchable;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.normaluser.UserPlayer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class LoadRecommendations extends Command {
    /**
     * Returns a string representation of this LoadRecommendations object, including the superclass
     * string representation.
     *
     * @return A string representation of this LoadRecommendations object.
     */
    @Override
    public String toString() {
        return super.toString() + "LoadRecommendations{}";
    }

    /**
     * Executes the LoadRecommendations command, loading the last recommended content for the user.
     * This method checks if the user is offline, retrieves the last recommendation for the user,
     * and loads it if available.
     *
     * @param outputs  The ArrayNode where the output will be added.
     * @param library  The Library object containing user and content data.
     * @param offline  A boolean indicating whether the user is offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.LOAD_RECOMMENDATIONS);

        if (offline) {
            userIsOffline(out);
            return;
        }

        NormalUser user = library.getUserWithUsername(getUsername());
        if (user.getLastRecommendation() == null) {
            out.put(Output.MESSAGE, Output.NO_RECOMMENDATIONS);
            return;
        }

        UserPlayer userPlayer = user.getPlayer();
        Searchable lastRecommendation = user.getLastRecommendation();

        boolean loadSuccess = userPlayer.loadSource(lastRecommendation, getTimestamp(), userPlayer,
                library, user);

        if (loadSuccess) {
            out.put(Output.MESSAGE, Output.RECOMMEND_SUCCESS);
        } else {
            System.out.println("should not happen this at LoadRecommend");
        }
    }
}
