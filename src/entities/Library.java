package entities;

import entities.playable.audio_files.Episode;
import entities.playable.Playlist;
import entities.playable.Podcast;
import entities.playable.audio_files.Song;
import entities.user_side.User;
import entities.user_side.UserPlayer;
import fileio.input.PodcastInput;
import fileio.input.EpisodeInput;
import fileio.input.LibraryInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Library {
    // Singleton instance for the Library
    private static Library library_instance = null;

    private List<Song> songs;
    private List<Podcast> podcasts;
    private List<User> users;
    private List<Playlist> playlists;

    private Library() {
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        users = new ArrayList<>();
        playlists = new ArrayList<>();
    }

    /**
     * Adds a song to the collection.
     *
     * @param song The song to be added.
     */
    public void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * Removes a song from the collection.
     *
     * @param song The song to be removed.
     */
    public void removeSong(final Song song) {
        songs.remove(song);
    }

    /**
     * Adds a podcast to the collection.
     *
     * @param podcast The podcast to be added.
     */
    public void addPodcast(final Podcast podcast) {
        podcasts.add(podcast);
    }

    /**
     * Removes a podcast from the collection.
     *
     * @param podcast The podcast to be removed.
     */
    public void removePodcast(final Podcast podcast) {
        podcasts.remove(podcast);
    }

    /**
     * Adds a user to the collection.
     *
     * @param user The user to be added.
     */
    public void addUser(final User user) {
        users.add(user);
    }

    /**
     * Removes a user from the collection.
     *
     * @param user The user to be removed.
     */
    public void removeUser(final User user) {
        users.remove(user);
    }

    /**
     * Initializes a new Library object based on the provided LibraryInput.
     *
     * @param libraryInput The input data used to initialize the Library.
     * @return A new Library object populated with Songs, Podcasts, and Users
     * based on the provided LibraryInput.
     */
    public static Library initializeLibrary(final LibraryInput libraryInput) {
        library_instance = new Library();

        // Convert each SongInput to Song and add to library
        for (SongInput songInput : libraryInput.getSongs()) {
            Song song = new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist());
            library_instance.addSong(song);
        }

        // Convert each PodcastInput to Podcast and add to library
        for (PodcastInput podcastInput : libraryInput.getPodcasts()) {
            ArrayList<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                Episode episode = new Episode(episodeInput.getName(), episodeInput.getDuration(),
                        episodeInput.getDescription());
                episodes.add(episode);
            }

            Podcast podcast = new Podcast(
                    podcastInput.getName(),
                    podcastInput.getOwner(),
                    episodes);

            library_instance.addPodcast(podcast);
        }

        // Convert each UserInput to User and add to library
        for (UserInput userInput : libraryInput.getUsers()) {
            User user = new User(userInput.getUsername(), userInput.getAge(), userInput.getCity(),
                    new UserPlayer(), new ArrayList<>(), new ArrayList<>());
            library_instance.addUser(user);
        }

        return library_instance;
    }

    /**
     * Retrieves a User object with the specified username from the library's collection of users.
     *
     * @param username The username of the user to retrieve.
     * @return The User object with the specified username, or null if not found.
     */
    public User getUserWithUsername(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Creates a new playlist with the given name and associates it with a user.
     *
     * @param playlistName The name of the playlist to create.
     * @param username     The username of the user who owns the playlist.
     * @return True if the playlist is successfully created, false if a playlist with the same name
     * already exists.
     */
    public boolean createPlaylist(final String playlistName, final String username) {
        if (returnPlaylistWithName(playlistName) != null) {
            return false;
        }

        Playlist playlist = new Playlist(playlistName, username, true, new ArrayList<>(), 0);
        playlists.add(playlist);

        return true;
    }

    /**
     * Retrieves a Playlist object with the specified name from the library's collection
     * of playlists.
     *
     * @param playlistName The name of the playlist to retrieve.
     * @return The Playlist object with the specified name, or null if not found.
     */
    public Playlist returnPlaylistWithName(final String playlistName) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(playlistName)) {
                return playlist;
            }
        }

        return null;
    }

    /**
     * Searches for a song with the specified name within a given playlist.
     *
     * @param songName  The name of the song to search for.
     * @param playlist  The playlist in which to search for the song.
     * @return The Song object with the specified name if found in the playlist, or null if not
     *         found or if the provided playlist is null.
     */
    public Song searchSongInPlaylist(final String songName, final Playlist playlist) {
        if (playlist == null) {
            return null;
        }

        for (Song song : playlist.getSongs()) {
            if (song.getName().equals(songName)) {
                return song;
            }
        }

        return null;
    }

    /**
     * Decides whether to add or remove a song from a user's playlist based on the song's presence.
     *
     * @param playlistID The ID of the playlist within the user's owned playlists.
     * @param song       The song to add or remove from the playlist.
     * @param username   The username of the user who owns the playlist.
     * @return True if the song was added to the playlist, false if the song was removed from the
     *          playlist.
     */
    public boolean decideAddRemove(final Integer playlistID, final Song song,
                                   final String username) {
        User user = getUserWithUsername(username);
        List<Playlist> playlistsSeenByUser = user.getPlaylistsOwnedByUser(playlists);

        Playlist playlist = playlistsSeenByUser.get(playlistID - 1);
        Song isSongInPlaylist = searchSongInPlaylist(song.getName(), playlist);

        if (isSongInPlaylist != null) {
            playlist.getSongs().remove(isSongInPlaylist);
            return false;
        } else {
            playlist.getSongs().add(song);
            return true;
        }
    }
}
