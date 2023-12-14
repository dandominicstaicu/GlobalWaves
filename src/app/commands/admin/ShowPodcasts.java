package app.commands.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.playable.Podcast;
import app.entities.playable.audio_files.Episode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShowPodcasts extends Command {
    /**
     * Returns a string representation of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String toString() {
        return super.toString() + "ShowPodcasts{}";
    }

    /**
     * Executes the command to show the podcasts associated with a host user and adds the
     * result to the specified output.
     *
     * @param outputs  The array node where the command output should be added.
     * @param library  The library containing user data, podcasts, and episodes.
     * @param offline  A boolean indicating whether the command is executed in offline mode.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.SHOW_PODCASTS);

        ArrayList<Podcast> hostsPodcasts = library.getHostsPodcasts(getUsername());

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (Podcast podcast : hostsPodcasts) {
            ObjectNode podcastNode = resultArray.addObject();
            podcastNode.put(Output.NAME, podcast.getName());

            ArrayNode episodesArray = podcastNode.putArray(Output.EPISODES);
            for (Episode episode : podcast.getEpisodes()) {
                episodesArray.add(episode.getName());
            }
        }

    }
}
