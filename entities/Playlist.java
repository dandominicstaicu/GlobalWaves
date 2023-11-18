package entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Playlist {
    private String name;
    private boolean isPublic;
    private List<Song> songs;


}
