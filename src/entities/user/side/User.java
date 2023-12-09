package entities.user.side;

import entities.playable.Playlist;
import entities.playable.audio_files.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;
    private int age;
    private String city;
    private UserPlayer player;
    private List<Song> favoriteSongs;
    private List<Playlist> followedPlaylists;

    /**
     * Toggles the 'like' status of a given song. If the song is already liked (present in
     * favoriteSongs) it is unliked (removed from favoriteSongs) and its like count is
     * decremented. If the song is not liked, it is added to favoriteSongs
     * and its like count is incremented.
     *
     * @param song The song to like or unlike.
     * @return true if the song was liked (added to favoriteSongs) as a result of this operation,
     * false if the song was unliked (removed from favoriteSongs).
     */
    public boolean likeUnlikeSong(final Song song) {
        if (favoriteSongs.contains(song)) {
            favoriteSongs.remove(song);
            song.setLikes(song.getLikes() - 1);
            return false;
        } else {
            favoriteSongs.add(song);
            song.setLikes(song.getLikes() + 1);
            return true;
        }
    }

    /**
     * Toggles the 'follow' status of a given playlist. If the playlist is already followed
     * (present in followedPlaylists), t is unfollowed (removed from followedPlaylists) and its
     * i follower count is decremented. If the playlist is not followed, it is added to
     * followedPlaylists and its follower count is incremented.
     *
     * @param playlist The playlist to follow or unfollow.
     * @return true if the playlist was followed (added to followedPlaylists) as a result of this
     * operation, false if the playlist was unfollowed (removed from followedPlaylists).
     */
    public boolean followUnfollowPlaylist(final Playlist playlist) {
        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.setFollowers(playlist.getFollowers() - 1);

            return false;
        }

        followedPlaylists.add(playlist);
        playlist.setFollowers(playlist.getFollowers() + 1);

        return true;
    }

    /**
     * Retrieves a list of playlists owned by the user. It filters through all provided playlists
     * and returns only those where the owner matches the current user's username.
     *
     * @param allPlaylists A list of all playlists to filter through.
     * @return A list of {@link Playlist} objects that are owned by the user.
     */
    public List<Playlist> getPlaylistsOwnedByUser(final List<Playlist> allPlaylists) {
        List<Playlist> userSeenPlaylists = new ArrayList<>();

        for (Playlist playlist : allPlaylists) {
            if (playlist.getOwner().equals(this.username)) {
                userSeenPlaylists.add(playlist);
            }
        }

        return userSeenPlaylists;
    }
}
