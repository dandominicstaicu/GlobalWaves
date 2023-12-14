package app.entities.playable.audio_files;

import app.entities.playable.Album;
import app.entities.playable.Playable;
import app.entities.userside.UserPlayer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Represents a song, extending the AudioFile class and implementing the Playable interface.
 */
@Getter
@Setter
@NoArgsConstructor
public class Song extends AudioFile implements Playable {
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private Integer releaseYear;
    private String artist;
    private Integer likes;

    /**
     * Constructs a new Song with the given attributes.
     *
     * @param name        The name of the song.
     * @param duration    The duration of the song in seconds.
     * @param album       The album to which the song belongs.
     * @param tags        The list of tags associated with the song.
     * @param lyrics      The lyrics of the song.
     * @param genre       The genre of the song.
     * @param releaseYear The release year of the song.
     * @param artist      The artist who performed the song.
     */
    public Song(final String name, final Integer duration, final String album,
                final ArrayList<String> tags, final String lyrics, final String genre,
                final Integer releaseYear, final String artist) {
        super.setName(name);
        super.setDuration(duration);
        this.album = album;
        this.tags = tags;
        this.lyrics = lyrics;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.artist = artist;

        this.likes = 0;
    }

    /**
     * Creates a String representation of the song.
     *
     * @return a string representation of the song.
     */
    @Override
    public String toString() {
        return "Song{"
                + "album='" + album + '\''
                + ", tags=" + tags
                + ", lyrics='" + lyrics + '\''
                + ", genre='" + genre + '\''
                + ", releaseYear=" + releaseYear
                + ", artist='" + artist + '\''
                + ", likes=" + likes
                + '}';
    }

    /**
     * Indicates that a song is not empty.
     *
     * @return Always returns false, as a song is not considered empty.
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Loads the song to a user's audio queue for playback.
     *
     * @param userPlayer The UserPlayer to which the song will be loaded.
     */
    @Override
    public void loadToQueue(final UserPlayer userPlayer) {
        // clear the queue before loading
        userPlayer.getAudioQueue().clear();

        // add only one song to the queue of the userPlayer
        userPlayer.getAudioQueue().add(this);

        // set in the player a reference to what is loaded
        userPlayer.setLoadedContentReference(this);
    }

    /**
     * Indicates that this entity is not a playlist.
     *
     * @return Always returns false, as this represents a song, not a playlist.
     */
    @Override
    public boolean isPlaylist() {
        return false;
    }

    /**
     * Indicates that this entity is a song.
     *
     * @return Always returns true, as this represents a song.
     */
    @Override
    public boolean isSong() {
        return true;
    }


    /**
     * Checks if the song is loaded in a player associated with the given username.
     *
     * @param username The username to check for.
     * @return True if the song is loaded in the player with the specified username,
     * false otherwise.
     */
    @Override
    public boolean isLoadedInPlayer(final String username) {
        return getArtist().equals(username);
    }

    /**
     * Checks if the song is part of a specific album.
     *
     * @param searchedAlbum The album to check for.
     * @return True if the song is included in the given album, false otherwise.
     */
    @Override
    public boolean containsAlbum(final Album searchedAlbum) {
        for (Song albumSong : searchedAlbum.getSongs()) {
            if (albumSong.equals(this)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the song is owned by a user with the specified artist name.
     *
     * @param artistName The artist name to check.
     * @return True if the song is owned by the user with the specified artist name,
     * false otherwise.
     */
    @Override
    public boolean ownedByUser(final String artistName) {
        return this.getArtist().equals(artistName);
    }
}
