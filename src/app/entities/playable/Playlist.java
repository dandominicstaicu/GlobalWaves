package app.entities.playable;

import app.entities.userside.normaluser.UserPlayer;
import app.entities.playable.audio_files.Song;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents a playlist containing songs that can be played.
 */
@Getter
@Setter
@NoArgsConstructor
public class Playlist implements Searchable {
    private String name;
    private String owner;
    private Boolean isPublic;
    private List<Song> songs;
    private Integer followers;

    /**
     * Constructs a new Playlist with the given attributes.
     *
     * @param name      The name of the playlist.
     * @param owner     The username of the user who owns the playlist.
     * @param isPublic  Indicates whether the playlist is public or private.
     * @param songs     The list of songs in the playlist.
     * @param followers The number of followers of the playlist.
     */
    public Playlist(final String name, final String owner, final Boolean isPublic,
                    final List<Song> songs, final Integer followers) {
        this.name = name;
        this.owner = owner;
        this.isPublic = isPublic;
        this.songs = songs;
        this.followers = followers;
    }


    /**
     * Checks if the playlist is empty (contains no songs).
     *
     * @return True if the playlist is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return songs.isEmpty();
    }


    /**
     * Loads all songs in the playlist to a user's audio queue for playback.
     *
     * @param userPlayer The UserPlayer to which the songs will be loaded.
     */
    @Override
    public void loadToQueue(final UserPlayer userPlayer) {
        // set isPlayingPlaylist
        userPlayer.setIsPlayingPlaylist(true);

        // maybe clear the queue before adding
        userPlayer.getAudioQueue().clear();

        // add all songs to the queue of the userPlayer
        userPlayer.getAudioQueue().addAll(songs);

        // set in the player a reference to what is loaded
        userPlayer.setLoadedContentReference(this);
    }

    /**
     * Checks if the entity is a playlist.
     *
     * @return Always returns true since this represents a playlist.
     */
    @Override
    public boolean isPlaylist() {
        return true;
    }

    /**
     * Switches the visibility of the playlist between public and private.
     *
     * @return The new visibility status (true for public, false for private).
     */
    public boolean switchVisibility() {
        isPublic = !isPublic;
        return isPublic;
    }

    /**
     * Calculates the total number of likes for all songs in the playlist.
     *
     * @return The total number of likes for all songs in the playlist.
     */
    public int getTotalLikes() {
        int totalLikes = 0;
        for (Song song : songs) {
            totalLikes += song.getLikes();
        }
        return totalLikes;
    }

    /**
     * Checks if the playlist contains any songs from a specific album.
     *
     * @param album The album to check for.
     * @return True if the playlist contains at least one song from the album, false otherwise.
     */
    @Override
    public boolean containsAlbum(final Album album) {
        for (Song albumSong : album.getSongs()) {
            if (this.songs.contains(albumSong)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the playlist is owned by a user with the given username.
     *
     * @param userName The username to check.
     * @return True if the playlist is owned by the user with the specified username,
     * false otherwise.
     */
    @Override
    public boolean ownedByUser(final String userName) {
        return this.getOwner().equals(userName);
    }

    /**
     * Checks if the playlist is loaded in a player with the given username.
     *
     * @param username The username of the player.
     * @return True if the playlist is loaded in the player, false otherwise.
     */
    @Override
    public boolean isLoadedInPlayer(final String username) {
        return getOwner().equals(username);
    }
}
