package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Constants;
import common.Output;
import entities.Library;
import entities.user.side.NormalUser;
import entities.user.side.User;
import entities.user.side.UserPlayer;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Represents a user command to load a selected source for playback. Extends the Command class.
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Status extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "Status{"
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
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.STATUS);

        NormalUser normalUser = lib.getUserWithUsername(getUsername());

        assert normalUser != null;
        UserPlayer userPlayer = normalUser.getPlayer();
        ObjectNode stats = out.putObject("stats");

        if (userPlayer.getAudioQueue() != null && userPlayer.playingIndexIsValid()) {
            int playingIndex = userPlayer.getPlayingIndex();
            String songName = userPlayer.getAudioQueue().get(playingIndex).getName();

            stats.put(Output.NAME, songName);
            stats.put(Output.REMAINED_TIME, userPlayer.getRemainedTime());
        } else {
            stats.put(Output.NAME, "");
            stats.put(Output.REMAINED_TIME, Constants.START_OF_SONG);
        }

        switch (userPlayer.getIsRepeating()) {
            case NO_REPEAT -> stats.put(Output.REPEAT, Output.NO_REPEAT);
            case REPEAT_ONCE -> stats.put(Output.REPEAT, Output.REPEAT_ONCE);
            case REPEAT_ALL -> stats.put(Output.REPEAT, Output.REPEAT_ALL);
            case REPEAT_CURRENT_SONG -> stats.put(Output.REPEAT, Output.REPEAT_CURRENT_SONG);
            case REPEAT_INFINITE -> stats.put(Output.REPEAT, Output.REPEAT_INFINITE);
            default -> {
            }
        }

        stats.put(Output.SHUFFLE, userPlayer.getIsShuffled());
        stats.put(Output.PAUSED, !userPlayer.getIsPlaying());
    }
}
