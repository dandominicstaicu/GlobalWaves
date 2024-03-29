package app.commands.normaluser.player;

import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.normaluser.UserPlayer;
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
public class Next extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "Next{"
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
    public void execute(final ArrayNode outputs, final Library lib, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.NEXT);

        if (offline) {
            userIsOffline(out);
            return;
        }

        NormalUser user = lib.getUserWithUsername(getUsername());
        UserPlayer userPlayer = user.getPlayer();

        if (userPlayer.playingIndexIsValid()) {
            userPlayer.next(true, getTimestamp(), lib, user);
        }

        if (!userPlayer.playingIndexIsValid()) {
            out.put(Output.MESSAGE, Output.LOAD_NEXT_ERR);
        } else {
            final int index = userPlayer.getPlayingIndex();
            final String name = userPlayer.getAudioQueue().get(index).getName();
            out.put(Output.MESSAGE, Output.NEXT_SUCCESS + name + ".");
        }


    }
}
