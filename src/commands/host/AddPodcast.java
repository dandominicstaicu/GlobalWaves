package commands.host;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Library;
import entities.playable.audio_files.Episode;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddPodcast extends Command {
    private String name;
    private List<Episode> episodes;

    @Override
    public String toString() {
        return super.toString() + "AddPodcast{" +
                "name='" + name + '\'' +
                ", episodes=" + episodes +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Library library) {
        System.out.println(this.toString());
    }
}
