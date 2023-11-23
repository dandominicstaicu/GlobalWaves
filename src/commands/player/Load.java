package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.playable.Playable;
import entities.UserPlayer;
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
    public void execute(final ArrayNode outputs, final Library lib) {
        ObjectNode out = outputs.addObject();
        out.put("command", "load");
        out.put("user", getUsername());
        out.put("timestamp", getTimestamp());

        UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();
        Playable selectedResult = userPlayer.getSearchBar().getSelectedResult();

        if (selectedResult == null) {
            out.put("message", "Please select a source before attempting to load.");
        } else {
            // Assuming you have a method in Player to handle loading
            int timestamp = getTimestamp();
            boolean loadSuccess = userPlayer.loadSource(selectedResult, timestamp, userPlayer);

            if (loadSuccess) {
                out.put("message", "Playback loaded successfully.");
            } else {
                out.put("message", "You can't load an empty audio collection!");
            }
        }
    }
}
