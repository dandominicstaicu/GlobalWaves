package entities;

import fileio.input.EpisodeInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Podcast extends Playable implements AudioFileCollection {
//    private String name;
    private String owner;
    private ArrayList<Episode> episodes;

    public Podcast(final String name, final String owner, final ArrayList<Episode> episodes) {
        this.name = name;
        this.owner = owner;
        this.episodes = episodes;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public void setEpisodes(final ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }
}
