package app.commands.normaluser.pages;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.playable.Searchable;
import app.entities.userside.User;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.normaluser.UserPlayer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class LoadRecommendations extends Command {
    @Override
    public String toString() {
        return super.toString() + "LoadRecommendations{}";
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.LOAD_RECOMMENDATIONS);

        if (offline) {
            userIsOffline(out);
            return;
        }

        NormalUser user = library.getUserWithUsername(getUsername());
//        if (user.getPlaylistsRecommendations() == null && user.getSongRecommendations().isEmpty()) {
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
