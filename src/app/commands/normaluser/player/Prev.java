package app.commands.normaluser.player;

import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.normaluser.UserPlayer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.entities.playable.audio_files.AudioFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Prev extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "Prev{"
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

        printCommandInfo(out, Output.PREV);

        if (offline) {
            userIsOffline(out);
            return;
        }

        NormalUser user = lib.getUserWithUsername(getUsername());
        UserPlayer userPlayer = user.getPlayer();

        if (!userPlayer.playingIndexIsValid()) {
            out.put(Output.MESSAGE, Output.LOAD_PREV_ERR);
        } else {
            AudioFile previous = userPlayer.prev(getTimestamp(), lib, user);
            out.put(Output.MESSAGE, Output.PREV_SUCCESS + previous.getName() + ".");
        }
    }
}
