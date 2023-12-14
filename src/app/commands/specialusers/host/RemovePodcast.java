package app.commands.specialusers.host;

import app.entities.Library;
import app.entities.playable.Podcast;
import app.entities.userside.User;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.common.UserTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemovePodcast extends Command {
    private String name;

    /**
     * Returns a string representation of the RemovePodcast command.
     *
     * @return A string describing the RemovePodcast command.
     */
    @Override
    public String toString() {
        return "RemovePodcast{"
                + "name='" + name + '\''
                + '}';
    }

    /**
     * Executes the RemovePodcast command to remove a podcast from the host's profile.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param library The Library where podcast and host data is stored.
     * @param offline A boolean flag indicating if the command is executed offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.REMOVE_PODCAST);

        User user = library.getFromAllUsers(getUsername());

        if (user == null) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.DOESNT_EXIST);
            return;
        }

        if (user.getUserType() != UserTypes.HOST) {
            out.put(Output.MESSAGE, getUsername() + Output.NOT_HOST);
            return;
        }

        if (!library.hasPodcastWithGivenName(getUsername(), getName())) {
            out.put(Output.MESSAGE, getUsername() + Output.NO_PODCAST_WITH_NAME);
            return;
        }

        Podcast podcast = library.getPodcastOfHostWithName(getUsername(), getName());

        if (library.decideDeletePodcast(podcast)) {
            out.put(Output.MESSAGE, getUsername() + Output.PODCAST_DELETE_FAIL);
            return;
        }

        library.removePodcast(podcast);

        out.put(Output.MESSAGE, getUsername() + Output.DELETE_PODCAST_SUCCESS);
    }
}
