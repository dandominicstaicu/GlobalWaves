package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.user.side.UserPlayer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a user command to skip to the next track.
 * Extends the Command class.
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class
Backward extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "Backward{"
                + '}';
    }

    /**
     * Executes the command to skip to the next track and adds the results to
     * the outputs.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib, boolean offline) {
        if (offline) {
            userIsOffline(outputs);
            return;
        }

        ObjectNode out = outputs.addObject();
        out.put(Output.COMMAND, Output.BACKWARD);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();

        if (!userPlayer.playingIndexIsValid()) {
            out.put(Output.MESSAGE, Output.LOAD_BACKWARD_ERR);
            // Please load a source before skipping forward.
        } else if (userPlayer.getAudioQueue().get(userPlayer.getPlayingIndex()).isSong()) {
            out.put(Output.MESSAGE, Output.SOURCE_NOT_PODCAST);
        } else {
            userPlayer.backward();
            out.put(Output.MESSAGE, Output.BACKWARD_SUCCESS);
        }

    }
}
