package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.playable.Playable;
import entities.user.side.UserPlayer;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Represents a user command to get the top 5 public playlists by followers.
 * Extends the Command class.
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Load extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "Load{"
                + '}';
    }

    /**
     * Executes the command to get the top 5 public playlists by followers and adds the
     * results to the outputs.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib, boolean offline) {
        ObjectNode out = outputs.addObject();
        out.put(Output.COMMAND, Output.LOAD);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();
        Playable selectedResult = userPlayer.getSearchBar().getSelectedResult();

        if (selectedResult == null) {
            out.put(Output.MESSAGE, Output.NO_SELECT);
        } else {
            // Assuming you have a method in Player to handle loading
            int timestamp = getTimestamp();
            boolean loadSuccess = userPlayer.loadSource(selectedResult, timestamp, userPlayer);

            if (loadSuccess) {
                out.put(Output.MESSAGE, Output.LOAD_SUCCESS);
            } else {
                out.put(Output.MESSAGE, Output.EMPTY_COLLECTION);
            }
        }
    }
}
