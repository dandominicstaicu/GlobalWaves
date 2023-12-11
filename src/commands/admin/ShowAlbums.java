package commands.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.playable.Album;
import entities.playable.audio_files.Song;
import entities.user.side.Artist;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ShowAlbums extends Command {
    @Override
    public String toString() {
        return super.toString() + "ShowAlbums{}";
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.SHOW_ALBUMS);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        Artist artist = library.getArtistWithUsername(getUsername());

        ArrayNode resultArray = out.putArray(Output.RESULT);
        for (Album album : artist.getAlbums()) {
            ObjectNode albumNode = resultArray.addObject();
            albumNode.put(Output.NAME, album.getName());

            ArrayNode songsArray = albumNode.putArray(Output.SONGS);
            for (Song song : album.getSongs()) {
                songsArray.add(song.getName());
            }
        }
    }

}
