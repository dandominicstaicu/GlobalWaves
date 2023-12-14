package app.commands.specialusers.host;

import app.entities.Library;
import app.entities.playable.Podcast;
import app.entities.userside.User;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.common.UserTypes;
import app.entities.playable.audio_files.Episode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddPodcast extends Command {
    private String name;
    private ArrayList<Episode> episodes;

    /**
     * Returns a string representation of the AddPodcast command.
     *
     * @return A string describing the AddPodcast command.
     */
    @Override
    public String toString() {
        return super.toString() + "AddPodcast{"
                + "name='" + name + '\''
                + ", episodes=" + episodes
                + '}';
    }

    /**
     * Checks if the list of episodes contains duplicate episode names.
     *
     * @return {@code true} if there are duplicate episode names, {@code false} otherwise.
     */
    private boolean hasDuplicatePodcast() {
        Set<String> episodeNames = new HashSet<>();
        for (Episode episode : episodes) {
            if (!episodeNames.add(episode.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Executes the AddPodcast command to add a new podcast to the library.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param library The Library where podcast and user data is stored.
     * @param offline A boolean flag indicating if the command is executed offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.ADD_PODCAST);

        User user = library.searchAllUsersForUsername(getUsername());
        if (user == null) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.DOESNT_EXIST);
            return;
        }

        if (user.getUserType() != UserTypes.HOST) {
            out.put(Output.MESSAGE, getUsername() + Output.NOT_HOST);
            return;
        }

        if (library.getPodcasts().stream().anyMatch(podcast -> podcast.getName()
                .equals(getName()))) {
            out.put(Output.MESSAGE, getUsername() + Output.SAME_NAME_PODCAST);
            return;
        }

        if (hasDuplicatePodcast()) {
            out.put(Output.MESSAGE, getUsername() + Output.DUPLICATE_EPISODE);
            return;
        }

        Podcast newPodcast = new Podcast(getName(), getUsername(), getEpisodes());
        library.addPodcast(newPodcast);

        out.put(Output.MESSAGE, getUsername() + Output.NEW_PODCAST_ADD);
    }


}
