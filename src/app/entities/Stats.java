package app.entities;

import app.entities.playable.Album;
import app.entities.userside.NormalUser;
import app.common.Constants;
import app.entities.playable.Playlist;
import app.entities.playable.audio_files.Song;
import app.entities.userside.artist.Artist;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Stats {
    private Stats() {

    }

    /**
     * Returns the top 5 songs in the library, sorted by number of likes.
     *
     * @param lib The library to get the songs from.
     * @return The top 5 songs in the library, sorted by number of likes.
     */
    public static List<Song> top5Songs(final Library lib) {
        // chatGPT helped me optimise this part (getting the top5 songs)
        return lib.getSongs().stream()
                .sorted(Comparator.comparingInt(Song::getLikes).reversed()) // descending
                .limit(Constants.MAX_LIST_RETURN)
                .toList(); // convert to list
    }

    /**
     * Returns the top 5 albums in the library, sorted by total number of likes of all songs
     * in the album.
     *
     * @param lib The library to get the albums from.
     * @return The top 5 albums in the library, sorted by total number of likes of all songs in
     * the album.
     */
    public static List<Album> top5Albums(final Library lib) {
        return lib.getAlbums().stream()
                .sorted(Comparator.comparingInt((Album a) -> a.getSongs().stream()
                .mapToInt(Song::getLikes).sum()).reversed()
                .thenComparing(Album::getName))
                .limit(Constants.MAX_LIST_RETURN)
                .toList();
    }

    /**
     * Returns the top 5 artists in the library, sorted by total number of likes of all songs
     * in the albums of the artist.
     *
     * @param lib The library to get the artists from.
     * @return The top 5 artists in the library, sorted by total number of likes of all songs in
     * the albums of the artist.
     */
    public static List<Artist> top5Artists(final Library lib) {
        Map<String, Integer> artistLikes = new HashMap<>();

        // Calculate total likes for each artist
        for (Album album : lib.getAlbums()) {
            String artistName = album.getOwner();
            int albumLikes = album.getSongs().stream().mapToInt(Song::getLikes).sum();
            artistLikes.put(artistName, artistLikes.getOrDefault(artistName, 0)
                    + albumLikes);
        }

        // Sort artists by total likes and get the top 5
        return lib.getArtists().stream()
                .sorted(Comparator.comparing((Artist artist) -> artistLikes
                        .getOrDefault(artist.getName(), 0)).reversed())
                .limit(Constants.MAX_LIST_RETURN)
                .toList();
    }

    /**
     * Returns the top 5 playlists in the library, sorted by number of followers.
     *
     * @param lib The library to get the playlists from.
     * @return The top 5 playlists in the library, sorted by number of followers.
     */
    public static List<Playlist> top5Playlists(final Library lib) {
        // chatGPT helped me optimise this part (getting the top5 playlists)
        return lib.getPlaylists().stream()
                .filter(Playlist::getIsPublic) // filter only public playlists
                .sorted(Comparator.comparingInt(Playlist::getFollowers).reversed()) // descending
                .limit(Constants.MAX_LIST_RETURN) // get the top 5
                .toList(); // convert to list
    }

    /**
     * Returns the top 5 users in the library, sorted by total number of likes of all songs
     * in the playlists of the user.
     *
     * @param lib The library to get the users from.
     * @return The top 5 users in the library, sorted by total number of likes of all songs in
     * the playlists of the user.
     */
    public static List<NormalUser> getOnlineUsers(final Library lib) {
        // chatGPT helped me optimise this part (getting the online users)
        return lib.getUsers().stream()
                .filter(NormalUser::getOnline) // filter only online users
                .sorted(Comparator.comparing(NormalUser::getUsername)) // sort by username
                .toList(); // convert to list
    }
}
