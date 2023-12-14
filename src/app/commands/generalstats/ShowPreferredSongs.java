package app.commands.generalstats;

import app.entities.Library;
import app.entities.userside.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.entities.playable.audio_files.Song;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ShowPreferredSongs extends Command {
    /**
     * Returns a string representation of the command.
     * Used for debugging.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString() + "ShowPreferredSongs{" + '}';
    }

    /**
     * Executes the command to show preferred songs and adds the results to the outputs.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.PREFERRED_SONGS);

        NormalUser normalUser = lib.getUserWithUsername(getUsername());
        List<Song> favoriteSongs = Objects.requireNonNull(normalUser).getFavoriteSongs();

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (Song song : favoriteSongs) {
            resultArray.add(song.getName());
        }

    }
}
