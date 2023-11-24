package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.user_side.UserPlayer;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Repeat extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "Repeat{"
                + '}';
    }

    /**
     * Executes the command to change the repeat mode of the playback and adds the results to
     * the outputs.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib) {
        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.REPEAT);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();

        if (!userPlayer.playingIndexIsValid()) {
            out.put(Output.MESSAGE, Output.LOAD_REPEAT_ERR);
        } else {
            switch (userPlayer.changeRepeatState()) {
                case NO_REPEAT -> out.put(Output.MESSAGE, Output.CHANGE_NO_REPEAT);
                case REPEAT_ONCE -> out.put(Output.MESSAGE, Output.CHANGE_REPEAT_ONCE);
                case REPEAT_ALL -> out.put(Output.MESSAGE, Output.CHANGE_REPEAT_ALL);
                case REPEAT_CURRENT_SONG -> out.put(Output.MESSAGE, Output.REPEAT_CURRENT);
                case REPEAT_INFINITE -> out.put(Output.MESSAGE, Output.CHANGE_REPEAT_INFINITE);
                default -> {

                }
            }
        }
    }
}
