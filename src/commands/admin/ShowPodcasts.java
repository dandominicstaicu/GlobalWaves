package commands.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.playable.Podcast;
import entities.playable.audio_files.Episode;
import lombok.*;

import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShowPodcasts extends Command {
    @Override
    public String toString() {
        return super.toString() + "ShowPodcasts{}";
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.SHOW_PODCASTS);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

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
