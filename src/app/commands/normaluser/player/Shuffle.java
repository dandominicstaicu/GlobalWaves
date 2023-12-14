package app.commands.normaluser.player;

import app.entities.Library;
import app.entities.userside.UserPlayer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a user command to skip to the next track.
 * Extends the Command class.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shuffle extends Command {
    private Integer seed;

    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "Shuffle{"
                + ", seed=" + seed
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

        printCommandInfo(out, Output.SHUFFLE);

        if (offline) {
            userIsOffline(out);
            return;
        }

        UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();

        if (!userPlayer.playingIndexIsValid()) {
            out.put(Output.MESSAGE, Output.LOAD_SHUFFLE_ERR);
        } else if (!userPlayer.getIsPlayingPlaylist()) {
            out.put(Output.MESSAGE, Output.SOURCE_NOT_PLAYLIST);
        } else {
            if (userPlayer.shufflePlaylist(this.getSeed())) {
                out.put(Output.MESSAGE, Output.SHUFFLE_ACTIVE);
            } else {
                out.put(Output.MESSAGE, Output.SHUFFLE_DEACTIVATE);
            }
        }
    }
}
