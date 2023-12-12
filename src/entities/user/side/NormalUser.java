package entities.user.side;

import common.UserTypes;
import entities.Library;
import entities.playable.Playlist;
import entities.playable.audio_files.Song;
import entities.user.side.pages.HomePage;
import entities.user.side.pages.Page;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NormalUser extends User {
    private UserPlayer player;
    private ArrayList<Song> favoriteSongs;
    private ArrayList<Playlist> followedPlaylists;
    private boolean online;
    private Page currentPage;


    public NormalUser(final String username, final int age, final String city) {
        super(username, age, city, UserTypes.NORMAL_USER);
        this.player = new UserPlayer();
        this.favoriteSongs = new ArrayList<>();
        this.followedPlaylists = new ArrayList<>();
        this.online = true;
        this.currentPage = new HomePage();
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

    public void switchConnectionStatus() {
        this.online = !this.online;
    }

    public boolean getOnline() {
        return this.online;
    }

    public boolean switchOnline() {
        this.online = !this.online;
        return this.online;
    }

    @Override
    public void addUser(Library library) {
        library.getUsers().add(this);
    }

    @Override
    public boolean handleDeletion(Library library) {
        for (NormalUser user : library.getUsers()) {
            if (user.getPlayer().getLoadedContentReference() != null) {

                if (user.getPlayer().getLoadedContentReference().isLoadedInPlayer(this.getUsername())) {
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
}
