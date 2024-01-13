package app.entities;

import app.commands.normaluser.Notification;
import app.common.Output;
import app.entities.playable.Album;
import app.entities.playable.Playlist;
import app.entities.playable.Podcast;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.User;
import app.entities.userside.normaluser.UserPlayer;
import app.common.UserTypes;
import app.entities.playable.audio_files.Episode;
import app.entities.playable.audio_files.Song;
import app.entities.userside.artist.Artist;
import app.entities.userside.host.Host;
import fileio.input.PodcastInput;
import fileio.input.EpisodeInput;
import fileio.input.LibraryInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public final class Library {
    // Singleton instance for the Library
    private static Library libraryInstance = null;

    // playable arrays
    private List<Song> songs;
    private List<Podcast> podcasts;
    private List<Playlist> playlists;
    private List<Album> albums;

    // user arrays
    private List<NormalUser> users;
    private List<Artist> artists;
    private List<Host> hosts;

    private Library() {
        this.songs = new ArrayList<>();
        this.podcasts = new ArrayList<>();
        this.users = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.artists = new ArrayList<>();
        this.hosts = new ArrayList<>();
        this.albums = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of the Library class.
     * If the instance does not exist, it creates a new one.
     * If it already exists, it returns the existing instance.
     *
     * @return The singleton instance of the Library class.
     */
    public static synchronized Library getInstance() {
        if (libraryInstance == null) {
            libraryInstance = new Library();
        }

        return libraryInstance;
    }

    /**
     * Gets artists with interacted set to true and sorts them by total revenue.
     *
     * @return A list of artists sorted by total revenue in decreasing order.
     */
    public List<Artist> getMonetizedArtists() {
        return artists.stream()
                .filter(artist -> artist.getMonetization().getInteracted())
                .sorted(Comparator.comparing((Artist artist) -> {
                            return artist.getMonetization().getSongRevenue()
                                    + artist.getMonetization().getMerchRevenue();
                        }).reversed() // Sort in descending order by total revenue
                        .thenComparing(Artist::getUsername)) // Sort alphabetically by artist's name
                .collect(Collectors.toList());
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
     * Adds a podcast to the collection at the init of the library.
     *
     * @param podcast The podcast to be added.
     */
    private void initPodcasts(final Podcast podcast) {
        podcasts.add(podcast);
    }

    /**
     * Adds a podcast to the collection.
     *
     * @param podcast The podcast to be added.
     */
    public void addPodcast(final Podcast podcast) {
        podcasts.add(podcast);

        Host host = getHostWithName(podcast.getOwner());
        String description = Output.NEW_PODCAST + Output.FROM + host.getName() + ".";
        Notification notification = new Notification(Output.NEW_PODCAST, description);

        host.sendNotification(notification);
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
    public void addUser(final NormalUser user) {
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
        if (libraryInstance == null) {
            libraryInstance = new Library();
        }

        // Convert each SongInput to Song and add to library
        for (SongInput songInput : libraryInput.getSongs()) {
            Song song = new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist());
            libraryInstance.addSong(song);
        }

        // Convert each PodcastInput to Podcast and add to library
        for (PodcastInput podcastInput : libraryInput.getPodcasts()) {
            ArrayList<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                Episode episode = new Episode(episodeInput.getName(), episodeInput.getDuration(),
                        episodeInput.getDescription(), podcastInput.getOwner());
                episodes.add(episode);
            }

            Podcast podcast = new Podcast(
                    podcastInput.getName(),
                    podcastInput.getOwner(),
                    episodes);

            libraryInstance.initPodcasts(podcast);
        }

        // Convert each UserInput to User and add to library
        for (UserInput userInput : libraryInput.getUsers()) {
            NormalUser normalUser = new NormalUser(userInput.getUsername(), userInput.getAge(),
                    userInput.getCity());
            libraryInstance.addUser(normalUser);
        }

        return libraryInstance;
    }

    /**
     * Retrieves a NormalUser object with the specified username from the library's collection
     * of users.
     *
     * @param username The username of the user to retrieve.
     * @return The User object with the specified username, or null if not found.
     */
    public NormalUser getUserWithUsername(final String username) {
        return users.stream()
                .filter(user -> user.getUserType() == UserTypes.NORMAL_USER
                        && user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves an Artist object with the specified username from the library's collection
     * of artists.
     *
     * @param username The username of the artist to retrieve.
     * @return The Artist object with the specified username, or null if not found.
     */
    public Artist getArtistWithName(final String username) {
        return artists.stream()
                .filter(artistPage -> artistPage.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves a Host object with the specified username from the library's collection
     * of hosts.
     *
     * @param username The username of the host to retrieve.
     * @return The Host object with the specified username, or null if not found.
     */
    public Host getHostWithName(final String username) {
        return hosts.stream()
                .filter(hostPage -> hostPage.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves a user by their username from the combined list of all users, artists, and hosts.
     * This method searches through all users, artists, and hosts in the system and returns the
     * first user found with the specified username. If no user is found with the given username,
     * the method returns null. This method uses Java Streams to efficiently search across
     * multiple collections.
     *
     * @param username The username of the user to be retrieved.
     * @return A {@link User} object if a user with the specified username is found, otherwise null
     */
    public User getFromAllUsers(final String username) {
        return Stream.concat(users.stream(), Stream.concat(artists.stream(), hosts.stream()))
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
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
     * @param songName The name of the song to search for.
     * @param playlist The playlist in which to search for the song.
     * @return The Song object with the specified name if found in the playlist, or null if not
     * found or if the provided playlist is null.
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
     * playlist.
     */
    public boolean decideAddRemove(final Integer playlistID, final Song song,
                                   final String username) {
        NormalUser normalUser = getUserWithUsername(username);
        List<Playlist> playlistsSeenByUser = normalUser.getPlaylistsOwnedByUser(playlists);

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

    /**
     * Resets the singleton instance of the Library class, allowing for the creation
     * of a new instance.
     * This method is typically used for cleanup and resetting the library instance
     * between tests or when needed.
     */
    public static void resetInstance() {
        if (libraryInstance != null) {
            // Perform any cleanup if necessary
            // For example, closing file streams or clearing collections
            // this has to be done because the there are multiple tests are called from main
            // that call the Main.action method

            // Set the singleton instance to null
            libraryInstance = null;
        }
    }

    /**
     * Adds songs from an album to the library.
     *
     * @param album the album to be added
     */
    public void addSongsFromAlbum(final Album album) {
        songs.addAll(album.getSongs());
    }


    /**
     * Retrieves the existence of a user in the library
     *
     * @return the user if found, null otherwise
     */
    public User searchAllUsersForUsername(final String username) {
        for (NormalUser user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        for (Artist artist : artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }

        for (Host host : hosts) {
            if (host.getUsername().equals(username)) {
                return host;
            }
        }

        return null;
    }

    /**
     * Adds a new album to the library
     *
     * @param album The album to be added
     */
    public void addAlbum(final Album album) {
        album.setAdditionOrder(albums.size());
        albums.add(album);

        Artist artist = getArtistWithName(album.getOwner());

        String description = Output.NEW_ALBUM + Output.FROM + artist.getName() + ".";
        Notification notification = new Notification(Output.NEW_ALBUM, description);

        artist.sendNotification(notification);
    }

    /**
     * Removes an album from the library
     *
     * @param album The album to be removed
     */
    public void removeAlbum(final Album album) {
        List<Song> albumSongs = album.getSongs();

        songs.removeIf(albumSongs::contains);

        albums.remove(album);
    }

    /**
     * Retrieves a list of all albums with the given artist name from the library.
     *
     * @param artistsName The name of the artist to search for.
     * @return A list of all matching albums
     */
    public ArrayList<Album> getArtistsAlbums(final String artistsName) {
        ArrayList<Album> result = new ArrayList<>();
        for (Album album : albums) {
            if (album.getOwner().equals(artistsName)) {
                result.add(album);
            }
        }

        return result;
    }

    /**
     * Retrieves a list of all podcasts with the given host name from the library.
     *
     * @param hostsName The name of the host to search for.
     * @return A list of all matching podcasts in the library.
     */
    public ArrayList<Podcast> getHostsPodcasts(final String hostsName) {
        ArrayList<Podcast> result = new ArrayList<>();

        for (Podcast podcast : podcasts) {
            if (podcast.getOwner().equals(hostsName)) {
                result.add(podcast);
            }
        }

        return result;
    }

    /**
     * Retrieves a list of all users from the library.
     *
     * @return A list of all users in the library.
     */
    public List<User> getAllUsers() {
        ArrayList<User> result = new ArrayList<>();

        result.addAll(users);
        result.addAll(artists);
        result.addAll(hosts);

        return result;
    }

    /**
     * Retrieves the existence of an album in the library
     *
     * @param username  The name of the owner to search for.
     * @param albumName The name of the album to search for.
     * @return A list of all matching playlists in the library.
     */
    public boolean hasAlbumWithGivenName(final String username, final String albumName) {
        for (Album album : albums) {
            if (album.getOwner().equals(username)) {
                if (album.getName().equals(albumName)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if a user with a given username owns a podcast with a specific name.
     * This method iterates through all podcasts and checks if there is a podcast
     * owned by the specified user with the given podcast name. It returns true if
     * such a podcast is found, otherwise false.
     *
     * @param username    The username of the user who might own the podcast.
     * @param podcastName The name of the podcast to search for.
     * @return true if a podcast with the given name and owned by the specified user exists,
     * false otherwise.
     */
    public boolean hasPodcastWithGivenName(final String username, final String podcastName) {
        for (Podcast podcast : podcasts) {
            if (podcast.getOwner().equals(username)) {
                if (podcast.getName().equals(podcastName)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Retrieves a list of all albums with the given owner name from the library.
     *
     * @param artistsName The name of the owner to search for.
     * @param albumName   The name of the album to search for.
     * @return A list of all matching playlists in the library.
     */
    public Album getAlbumOfUserWithName(final String artistsName, final String albumName) {
        ArrayList<Album> artistsAlbums = getArtistsAlbums(artistsName);

        for (Album album : artistsAlbums) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }

        return null;
    }

    /**
     * Retrieves a list of all podcasts with the given owner name from the library.
     *
     * @param hostName    The name of the owner to search for.
     * @param podcastName The name of the podcast to search for.
     * @return A list of all matching podcasts in the library.
     */
    public Podcast getPodcastOfHostWithName(final String hostName, final String podcastName) {
        ArrayList<Podcast> hostsPodcasts = getHostsPodcasts(hostName);

        for (Podcast podcast : hostsPodcasts) {
            if (podcast.getName().equals(podcastName)) {
                return podcast;
            }
        }

        return null;
    }

    /**
     * Determines whether an album can be deleted based on its usage by users and its inclusion in
     * playlists.
     * <p>
     * This method checks two conditions to decide whether an album should be deleted:
     * 1. If any user's player is currently loaded with content that contains the album,
     * indicating that the album is in use.
     * 2. If any of the songs from the album are included in any playlists.
     * If either condition is met, the album should not be deleted.
     *
     * @param album The album to be evaluated for deletion.
     * @return true if the album is currently in use and should not be deleted, false otherwise.
     */
    public boolean decideNotDeleteAlbum(final Album album) {
        for (NormalUser user : users) {
            UserPlayer player = user.getPlayer();
            if (player.getLoadedContentReference() != null) {
                if (player.getLoadedContentReference().containsAlbum(album)) {
                    return true;
                }
            }

            if (user.getPlaylistsRecommendations() != null) {
                List<Song> recommendedPlaylist = user.getPlaylistsRecommendations().getSongs();
                for (Song albumSong : album.getSongs()) {
                    if (recommendedPlaylist.contains(albumSong)) {
                        return true;
                    }
                }
            }

            if (user.getSongRecommendations() != null) {
                List<Song> songRecommendations = user.getSongRecommendations();
                for (Song albumSong : album.getSongs()) {
                    if (songRecommendations.contains(albumSong)) {
                        return true;
                    }
                }
            }
        }

        for (Playlist playlist : playlists) {
            for (Song albumSong : album.getSongs()) {
                if (playlist.getSongs().contains(albumSong)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determines whether a podcast can be deleted based on its usage by users.
     * This method checks if the podcast is currently loaded in any user's player,
     * indicating that it is in use. If the podcast is in use, it should not be deleted.
     *
     * @param podcast The podcast to be evaluated for deletion.
     * @return true if the podcast is currently in use and should not be deleted, false otherwise.
     */
    public boolean decideDeletePodcast(final Podcast podcast) {
        for (NormalUser user : users) {
            UserPlayer player = user.getPlayer();
            if (player.getLoadedContentReference() != null) {
                if (player.getLoadedContentReference().equals(podcast)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Retrieves the ad content song from the library.
     *
     * @return The Song object representing the ad content, or null if not found.
     */
    public Song getAdContent() {
        for (Song song : songs) {
            if (song.getName().equals("Ad Break")) {
                return song;
            }
        }

        return null;
    }

    /**
     * Retrieves a song with the specified name from the library.
     *
     * @param name The name of the song to be retrieved.
     * @return The Song object with the specified name, or null if not found.
     */
    public Song getSongWithName(final String name) {
        for (Song song : songs) {
            if (song.getName().equals(name)) {
                return song;
            }
        }

        return null;
    }

    /**
     * Retrieves and returns a sorted list of songs with the specified genre.
     * The list is sorted by the number of likes in descending order.
     *
     * @param genre The genre to filter songs by.
     * @return A sorted ArrayList of Song objects with the specified genre.
     */
    public ArrayList<Song> getSongsWithGenreSorted(final String genre) {
        return songs.stream()
                .filter(song -> genre.equals(song.getGenre()))
                .sorted(Comparator.comparingInt(Song::getLikes).reversed()) // In descending order
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Retrieves and returns a list of songs with the specified genre.
     *
     * @param genre The genre to filter songs by.
     * @return An ArrayList of Song objects with the specified genre.
     */
    public ArrayList<Song> getSongsWithGenre(final String genre) {
        return songs.stream()
                .filter(song -> genre.equals(song.getGenre()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
