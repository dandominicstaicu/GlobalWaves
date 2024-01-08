package app.entities.playable;

import app.entities.playable.audio_files.Song;
import app.entities.userside.normaluser.UserPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Album implements Searchable {
    private String name;
    private int releaseYear;
    private String description;
    private ArrayList<Song> songs;
    private String owner;

    private Integer additionOrder;

    /**
     * Constructs a new Album with the given attributes.
     *
     * @param name        The name of the album.
     * @param releaseYear The release year of the album.
     * @param description The description of the album.
     * @param newSongs    The list of songs in the album.
     * @param owner       The username of the user who owns the album.
     */
    public Album(final String name, final int releaseYear, final String description,
                 final ArrayList<Song> newSongs, final String owner) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = new ArrayList<>();
        for (Song song : newSongs) {
            Song newSong = new Song(song.getName(), song.getDuration(), song.getAlbum(),
                    song.getTags(), song.getLyrics(), song.getGenre(), song.getReleaseYear(),
                    song.getArtist());

            this.songs.add(newSong);
        }
        this.owner = owner;
    }

    /**
     * Checks if the album is empty (contains no songs).
     *
     * @return True if the album is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return songs.isEmpty();
    }

    /**
     * Loads all songs on the album to a user's audio queue for playback.
     *
     * @param userPlayer The UserPlayer to which the songs will be loaded.
     */
    @Override
    public void loadToQueue(final UserPlayer userPlayer) {
        userPlayer.setIsPlayingPlaylist(true);

        // clear the queue before adding
        userPlayer.getAudioQueue().clear();

        // add all songs to the queue of the userPlayer
        userPlayer.getAudioQueue().addAll(songs);

        // set in the player a reference to what is loaded
        userPlayer.setLoadedContentReference(this);
    }

    /**
     * Retrieves a string representation of the album.
     *
     * @return A string representation of the album.
     */
    @Override
    public String toString() {
        return "Album{"
                + "name='" + name + '\''
                + '}';
    }

    /**
     * Checks if the entity is a playlist.
     *
     * @return Always returns false since this represents an album.
     */
    @Override
    public boolean isPlaylist() {
        return false;
    }

    /**
     * Checks if the album is loaded in a player associated with the given username.
     *
     * @param username The username to check for.
     * @return True if the album is loaded in the player with the specified username,
     * false otherwise.
     */
    @Override
    public boolean isLoadedInPlayer(final String username) {
        return getOwner().equals(username);
    }

    /**
     * Checks if the album contains a specific album (itself).
     *
     * @param album The album to check for.
     * @return True if the album is the same as the specified album, false otherwise.
     */

    @Override
    public boolean containsAlbum(final Album album) {
        return album.equals(this);
    }

    /**
     * Checks if the album is owned by a user with the specified artist name.
     *
     * @param artistName The artist name to check.
     * @return True if the album is owned by the user with the specified artist name,
     * false otherwise.
     */
    @Override
    public boolean ownedByUser(final String artistName) {
        return this.getOwner().equals(artistName);
    }
}
