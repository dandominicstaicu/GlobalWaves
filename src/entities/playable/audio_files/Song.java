package entities.playable.audio_files;

import entities.playable.Album;
import entities.playable.Playable;
import entities.user.side.UserPlayer;
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

    @Override
    public String toString() {
        return "Song{" +
                "album='" + album + '\'' +
                ", tags=" + tags +
                ", lyrics='" + lyrics + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseYear=" + releaseYear +
                ", artist='" + artist + '\'' +
                ", likes=" + likes +
                '}';
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

    @Override
    public boolean isLoadedInPlayer(String username) {
        return getArtist().equals(username);
    }

    @Override
    public boolean containsAlbum(Album album) {
        for (Song albumSong : album.getSongs()) {
            if (albumSong.equals(this)) {
                return true;
            }
        }

        return false;
    }
}
