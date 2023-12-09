package entities.playable;

import entities.user.side.UserPlayer;

/**
 * An interface representing a playable entity, such as a Song or a Playlist.
 */
public interface Playable {
    /**
     * Checks if the playable entity is empty.
     *
     * @return True if the entity is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Retrieves the name of the playable entity.
     *
     * @return The name of the entity.
     */
    String getName();

    /**
     * Loads the playable entity to a user's player for playback.
     *
     * @param userPlayer The UserPlayer to which the entity will be loaded.
     */
    void loadToQueue(UserPlayer userPlayer);

    /**
     * Checks if the playable entity is a playlist.
     *
     * @return True if the entity is a playlist, false otherwise.
     */
    boolean isPlaylist();
}
