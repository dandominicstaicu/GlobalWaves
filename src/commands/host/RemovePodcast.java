package commands.host;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import common.UserTypes;
import entities.Library;
import entities.playable.Podcast;
import entities.user.side.User;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemovePodcast extends Command {
    private String name;

    @Override
    public String toString() {
        return "RemovePodcast{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
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
