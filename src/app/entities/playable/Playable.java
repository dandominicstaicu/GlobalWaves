package app.entities.playable;

import app.entities.userside.NormalUser;
import app.entities.userside.SearchBar;
import app.entities.userside.UserPlayer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.common.Output;


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
    default void loadToQueue(UserPlayer userPlayer) {

    }

    /**
     * Checks if the playable entity is a playlist.
     *
     * @return True if the entity is a playlist, false otherwise.
     */
    boolean isPlaylist();

    /**
     * Checks if the playable entity is loaded in a player with the given username.
     *
     * @param username The username of the player.
     * @return True if the entity is loaded in the player, false otherwise.
     */
    default boolean isLoadedInPlayer(String username) {
        return false;
    }

    /**
     * Handles the selection of the playable entity in a search bar.
     *
     * @param searchBar The search bar in which the entity is selected.
     * @param user      The user performing the selection.
     * @param out       The ObjectNode for output messages.
     */
    default void handleSelect(final SearchBar searchBar, final NormalUser user, ObjectNode out) {
        out.put(Output.MESSAGE, "Successfully selected " + this.getName() + ".");
        searchBar.setSelectedResult(this);
        searchBar.setLastSearchResults(null);

    }

    /**
     * Checks if the playable entity contains a specific album.
     *
     * @param album The album to check for.
     * @return True if the entity contains the album, false otherwise.
     */
    default boolean containsAlbum(Album album) {
        return false;
    }

    /**
     * Checks if the playable entity is owned by a user with the given artist name.
     *
     * @param artistName The name of the artist.
     * @return True if the entity is owned by the user with the artist name, false otherwise.
     */
    default boolean ownedByUser(final String artistName) {
        return false;
    }

}
