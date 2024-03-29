package app.commands.normaluser.player;

import app.entities.Library;
import app.entities.userside.normaluser.UserPlayer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
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
    public void execute(final ArrayNode outputs, final Library lib, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.PLAY_PAUSE);

        if (offline) {
            userIsOffline(out);
            return;
        }

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
