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

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PlayPause extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "PlayPause{"
                + '}';
    }

    /**
     * Executes the command to load a selected source for playback and adds the results to the
     * outputs.
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
        out.put(Output.COMMAND, Output.PLAY_PAUSE);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();

        if (userPlayer.getAudioQueue() != null && userPlayer.playingIndexIsValid()) {
            if (userPlayer.getIsPlaying()) {
                userPlayer.pause(getTimestamp());
                out.put(Output.MESSAGE, Output.PAUSE);
            } else {
                userPlayer.resume();
                out.put(Output.MESSAGE, Output.RESUME);
            }
        } else {
            out.put(Output.MESSAGE, Output.LOAD_PLAY_PAUSE_ERR);
        }

    }
}
