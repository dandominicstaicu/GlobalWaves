package commands.artist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.Command;
import entities.Library;
import entities.playable.audio_files.Song;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddAlbum extends Command {
    private String name;
    private Integer releaseYear;
    private String description;
    private List<Song> songs;

    @Override
    public String toString() {
        return super.toString() + "AddAlbum{" +
                "name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                ", description='" + description + '\'' +
                ", songs=" + songs +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
        System.out.println(this.toString());
    }
}
