package app.commands.normaluser.player;

import app.entities.Library;
import app.entities.userside.NormalUser;
import app.entities.userside.UserPlayer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.Setter;

/**
 * Represents a user command to skip to the next track.
 * Extends the Command class.
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Forward extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "Forward{"
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
        ObjectNode out = outputs.addObject();
        printCommandInfo(out, Output.FORWARD);

        if (offline) {
            userIsOffline(out);
            return;
        }

        NormalUser normalUser = lib.getUserWithUsername(getUsername());

        assert normalUser != null;
        UserPlayer userPlayer = normalUser.getPlayer();

        if (!userPlayer.playingIndexIsValid()) {
            out.put(Output.MESSAGE, Output.LOAD_FWD_ERR);
        } else if (userPlayer.getAudioQueue().get(userPlayer.getPlayingIndex()).isSong()) {
            out.put(Output.MESSAGE, Output.SOURCE_NOT_PODCAST);
        } else {
            userPlayer.forward();
            out.put(Output.MESSAGE, Output.FORWARD_SUCCESS);
        }

    }
}
