package app.entities.playable;

import app.entities.userside.UserPlayer;
import app.entities.playable.audio_files.Episode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Represents a podcast, implementing the Playable interface.
 */
@Getter
@Setter
@NoArgsConstructor
public class Podcast implements Playable {
    private String name;
    private String owner;
    private ArrayList<Episode> episodes;

    /**
     * Constructs a new Podcast with the given attributes.
     *
     * @param name     The name of the podcast.
     * @param owner    The owner or creator of the podcast.
     * @param episodes The list of episodes in the podcast.
     */
    public Podcast(final String name, final String owner, final ArrayList<Episode> episodes) {
        this.name = name;
        this.owner = owner;
        this.episodes = episodes;
    }

    /**
     * Checks if the podcast is empty (contains no episodes).
     *
     * @return True if the podcast is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return episodes.isEmpty();
    }

    /**
     * Loads all episodes of the podcast to a user's audio queue for playback.
     *
     * @param userPlayer The UserPlayer to which the episodes will be loaded.
     */
    @Override
    public void loadToQueue(final UserPlayer userPlayer) {
        // maybe clear the queue before adding
        userPlayer.getAudioQueue().clear();

        // add all episodes to the queue of the userPlayer
        userPlayer.getAudioQueue().addAll(episodes);

        // set in the player a reference to what is loaded
        userPlayer.setLoadedContentReference(this);
    }

    /**
     * Indicates that this entity is not a playlist.
     *
     * @return Always returns false, as this represents a podcast, not a playlist.
     */
    @Override
    public boolean isPlaylist() {
        return false;
    }

    @Override
    public boolean isLoadedInPlayer(String username) {
        return getOwner().equals(username);
    }
}
