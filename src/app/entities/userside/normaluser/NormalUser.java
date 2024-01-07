package app.entities.userside.normaluser;

import app.common.Output;
import app.common.UserTypes;
import app.entities.Library;
import app.entities.playable.Playlist;
import app.entities.playable.audio_files.Episode;
import app.entities.playable.audio_files.Song;
import app.entities.userside.User;
import app.entities.userside.pages.HomePage;
import app.entities.userside.pages.Page;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class NormalUser extends User {
    private UserPlayer player;
    private ArrayList<Song> favoriteSongs;
    private ArrayList<Playlist> followedPlaylists;
    private boolean online;
    private Page currentPage;

    private WrappedStats wrappedStats;

    public NormalUser(final String username, final int age, final String city) {
        super(username, age, city, UserTypes.NORMAL_USER);
        this.player = new UserPlayer();
        this.favoriteSongs = new ArrayList<>();
        this.followedPlaylists = new ArrayList<>();
        this.online = true;
        this.currentPage = new HomePage();
        this.wrappedStats = new WrappedStats(this);
    }

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
            if (playlist.getOwner().equals(getUsername())) {
                userSeenPlaylists.add(playlist);
            }
        }

        return userSeenPlaylists;
    }

    /**
     * Switches the online status of the user.
     *
     * @param currentTimestamp The current timestamp for updating pause start time.
     */
    public void switchConnectionStatus(final int currentTimestamp) {
        UserPlayer userPlayer = this.getPlayer();
        if (this.online) {
            userPlayer.setPauseStartTimeStamp(currentTimestamp);
            userPlayer.setIsOffline(true);
        } else {
            userPlayer.setPauseStartTimeStamp(0);
            userPlayer.setIsOffline(false);

        }

        this.online = !this.online;
    }

    /**
     * Gets the online status of the user.
     *
     * @return true if the user is online, false if the user is offline.
     */
    public boolean getOnline() {
        return this.online;
    }

    /**
     * Adds the user to the specified library. The implementation of this method depends on the
     * specific user type (e.g., NormalUser, Artist, Host) and their interactions with the library.
     *
     * @param library The library to which the user is added.
     */
    @Override
    public void addUser(final Library library) {
        library.getUsers().add(this);
    }

    /**
     * Handles the deletion of the user's account from the library. Checks for user interactions
     * with the user's content before deletion.
     *
     * @param library The library from which the user's account is deleted.
     * @return true if the user's account was successfully deleted, false otherwise.
     */
    @Override
    public boolean handleDeletion(final Library library) {
        for (NormalUser user : library.getUsers()) {
            if (user.getPlayer().getLoadedContentReference() != null) {
                if (user.getPlayer().getLoadedContentReference()
                        .isLoadedInPlayer(this.getUsername())) {
                    if (user.getPlayer().getIsPlaying()) {
                        return false;
                    }
                }
            }
        }

        List<Playlist> libPlaytlists = library.getPlaylists();
        List<Playlist> rmvPlaylists = new ArrayList<>();
        for (Playlist playlist : libPlaytlists) {
            if (playlist.getOwner().equals(this.getUsername())) {
                rmvPlaylists.add(playlist);
            }
        }

        for (Playlist followedPlaylist : followedPlaylists) {
            followedPlaylist.setFollowers(followedPlaylist.getFollowers() - 1);
        }

        library.getPlaylists().removeAll(rmvPlaylists);

        List<NormalUser> libUsers = library.getUsers();

        for (NormalUser user : libUsers) {
            List<Playlist> followedPlaylistsByUser = user.getFollowedPlaylists();
            rmvPlaylists = new ArrayList<>();
            for (Playlist playlist : followedPlaylistsByUser) {
                if (playlist.getOwner().equals(this.getUsername())) {
                    rmvPlaylists.add(playlist);
                }
            }

            user.getFollowedPlaylists().removeAll(rmvPlaylists);
        }

        library.removeUser(this);


        return true;
    }

    @Override
    public void printWrappedStats(final ObjectNode out) {
        ObjectNode result = out.putObject(Output.RESULT);

        List<Map.Entry<String, Integer>> topArtists = wrappedStats.top5Artists();
        ObjectNode artistsNode = result.putObject("topArtists");
        for (Map.Entry<String, Integer> entry : topArtists) {
            artistsNode.put(entry.getKey(), entry.getValue());
        }

        List<Map.Entry<String, Integer>> topGenres = wrappedStats.top5Genres();
        ObjectNode genresNode = result.putObject("topGenres");
        for (Map.Entry<String, Integer> entry : topGenres) {
            genresNode.put(entry.getKey(), entry.getValue());
        }

        List<Map.Entry<String, Integer>> topSongs = wrappedStats.top5Songs();
        ObjectNode songsNode = result.putObject("topSongs");
        for (Map.Entry<String, Integer> entry : topSongs) {
            songsNode.put(entry.getKey(), entry.getValue());
        }

        List<Map.Entry<String, Integer>> topAlbums = wrappedStats.top5Albums();
        ObjectNode albumsNode = result.putObject("topAlbums");
        for (Map.Entry<String, Integer> entry : topAlbums) {
            albumsNode.put(entry.getKey(), entry.getValue());
        }

        List<Map.Entry<String, Integer>> topEpisodes = wrappedStats.top5Episodes();
        ObjectNode episodesNode = result.putObject("topEpisodes");
        for (Map.Entry<String, Integer> entry : topEpisodes) {
            episodesNode.put(entry.getKey(), entry.getValue());
        }

    }
}
