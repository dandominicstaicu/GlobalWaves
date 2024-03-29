package app.entities.playable.audio_files;

import app.entities.userside.artist.Monetization;
import app.entities.Library;
import app.entities.playable.Album;
import app.entities.playable.Searchable;
import app.entities.userside.artist.Artist;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.normaluser.UserPlayer;
import app.entities.userside.normaluser.WrappedStats;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a song, extending the AudioFile class and implementing the Playable interface.
 */
@Getter
@Setter
@NoArgsConstructor
public class Song extends AudioFile implements Searchable {
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
     * Compares this Song object with another object for equality.
     *
     * @param o The object to compare with this Song.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Song)) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        Song song = (Song) o;
        return Objects.equals(tags, song.tags)
                && Objects.equals(lyrics, song.lyrics)
                && Objects.equals(genre, song.genre)
                && Objects.equals(releaseYear, song.releaseYear)
                && Objects.equals(getName(), song.getName())
                && Objects.equals(artist, song.artist)
                && Objects.equals(getDuration(), song.getDuration());
    }

    /**
     * Generates a hash code for this Song object based on its attributes.
     *
     * @return The hash code for this Song object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), album, tags, lyrics, genre,
                releaseYear, artist, likes);
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
     * Edits statistics related to this AudioFile object after playback.
     * This method updates listen counts for the user, the artist, and monetization information.
     *
     * @param lib  The Library object containing song data.
     * @param user The NormalUser initiating the playback.
     */
    @Override
    public void editStats(final Library lib, final NormalUser user) {
        if (this.getName().equals("Ad Break")) {
            return;
        }

        WrappedStats stats = user.getWrappedStats();

        stats.registerStats();

        // stats for the user
        stats.addSongListenCount(this.getName());
        stats.addArtistListenCount(this.getArtist());
        stats.addGenreListenCount(this.getGenre());
        stats.addAlbumListenCount(this.getAlbum());

        // stats for the artist
        Artist artistInstance = lib.getArtistWithName(this.getArtist());
        WrappedStats artistStats = artistInstance.getWrappedStats();

        artistStats.registerStats();

        artistStats.addAlbumListenCount(this.getAlbum());
        artistStats.addSongListenCount(this.getName());
        artistStats.addListenerCount(user.getUsername());

        // monetization
        Monetization monetization = artistInstance.getMonetization();
        monetization.interact();

        // add the song in the history of the user
        if (user.getIsPremium()) {
            user.addPremiumHistory(this);
        } else {
            user.addRegularHistory(this);
        }
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

    /**
     * Retrieves the owner of the audio file, which is represented by the artist.
     *
     * @return The artist who owns the audio file as a String.
     */
    @Override
    public String getFileOwner() {
        return this.artist;
    }

    /**
     * Retrieves the genre of the song associated with this audio file.
     *
     * @return The genre of the song as a String.
     */
    @Override
    public String getSongGenre() {
        return this.genre;
    }
}
