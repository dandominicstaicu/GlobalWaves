package commands.artist;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import common.UserTypes;
import entities.Library;
import entities.playable.Album;
import entities.playable.audio_files.Song;
import entities.user.side.User;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddAlbum extends Command {
    private String name;
    private Integer releaseYear;
    private String description;
    private ArrayList<Song> songs;

    @Override
    public String toString() {
        return super.toString() + "AddAlbum{" +
                "name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                ", description='" + description + '\'' +
                ", songs=" + songs +
                '}';
    }

    private boolean hasDuplicateSongNames() {
        Set<String> songNames = new HashSet<>();
        for (Song song : songs) {
            if (!songNames.add(song.getName())) {
                // If add() returns false, it means the name is already in the set
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute(ArrayNode outputs, Library library, boolean offline) {
//        System.out.println(this.toString());

        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.ADD_ALBUM);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        User user = library.searchAllUsersForUsername(getUsername());
        if (user == null) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + getUsername() + Output.DOESNT_EXIST);
            return;
        }

        if (user.getUserType() != UserTypes.ARTIST) {
            out.put(Output.MESSAGE, getUsername() + Output.NOT_ARTIST);
            return;
        }

        if (hasDuplicateSongNames()) {
            out.put(Output.MESSAGE, getUsername() + Output.DUPLICATE_SONG_NAMES);
            return;
        }

        if (library.getAlbums().stream().anyMatch( album ->album.getName().equals(getName()) && album.getOwner().equals(getUsername()))) {
            out.put(Output.MESSAGE, getUsername() + Output.SAME_NAME_ALBUM);
            return;
        }

        Album newAlbum = new Album(getName(), getReleaseYear(), getDescription(), getSongs(), getUsername());
        library.addAlbum(newAlbum);

        library.addSongsFromAlbum(newAlbum);
        out.put(Output.MESSAGE, getUsername() + Output.NEW_ALBUM_ADD);
    }
}



