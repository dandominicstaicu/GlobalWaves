package entities;

import fileio.input.EpisodeInput;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Podcast {
    private String name;
    private String owner;
    private ArrayList<EpisodeInput> episodes;

    public Podcast() {
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public void setEpisodes(final ArrayList<EpisodeInput> episodes) {
        this.episodes = episodes;
    }
}
