package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.UserPlayer;
import entities.playable.audio_files.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Like extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "Like{"
                + '}';
    }

    /**
     * Executes the command to change the repeat mode of the playback and adds the
     * results to the outputs.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib) {
        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.LIKE);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();

        if (!userPlayer.playingIndexIsValid()) {
            out.put(Output.MESSAGE, Output.LOAD_LIKE_ERR);
        } else if (!userPlayer.getAudioQueue().get(userPlayer.getPlayingIndex()).isSong()) {
            out.put(Output.MESSAGE, Output.LOAD_NOT_SONG_ERR);
        } else {
            final Song song = (Song) userPlayer.getAudioQueue().get(userPlayer.getPlayingIndex());
            if (lib.getUserWithUsername(getUsername()).likeUnlikeSong(song)) {
                out.put(Output.MESSAGE, Output.LIKED);
            } else {
                out.put(Output.MESSAGE, Output.UNLIKED);
            }
        }
    }
}
