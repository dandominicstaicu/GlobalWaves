package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Playlist extends Playable implements AudioFileCollection {
//    private String name;
    private String owner;
    private Boolean isPublic;
    private List<Song> songs;

    public Playlist(final String name, final String owner, final Boolean isPublic,
                    final List<Song> songs) {
        this.name = name;
        this.owner = owner;
        this.isPublic = isPublic;
        this.songs = songs;
    }
}
