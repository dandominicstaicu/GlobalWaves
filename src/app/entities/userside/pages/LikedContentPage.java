package app.entities.userside.pages;

import app.common.PageTypes;
import app.entities.Library;
import app.entities.playable.Playlist;
import app.entities.playable.audio_files.Song;
import app.entities.userside.normaluser.NormalUser;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LikedContentPage implements Page {
    private ArrayList<Song> likedSongs;
    private ArrayList<Playlist> followedPlaylists;

    /**
     * Constructs a new LikedContentPage with empty lists for liked songs and followed playlists.
     */
    public LikedContentPage() {
        this.likedSongs = new ArrayList<>();
        this.followedPlaylists = new ArrayList<>();
    }

    /**
     * Constructs the list of liked songs based on the given user's favorite songs.
     *
     * @param user The user whose liked songs are to be displayed.
     */
    private void constructLikedSongs(final NormalUser user) {
        this.likedSongs = user.getFavoriteSongs();
    }

    /**
     * Populates the followedPlaylists list with playlists followed by the specified user.
     *
     * @param user The user whose followed playlists are to be added to the followedPlaylists list.
     */
    private void constructFollowedPlaylists(final NormalUser user) {
        this.followedPlaylists = user.getFollowedPlaylists();
    }

    /**
     * Generates and returns a string representation of the Liked Content page, including lists
     * of liked songs and followed playlists, formatted for display.
     *
     * @param lib  The library context, not used in the current implementation.
     * @param user The user whose liked content is being displayed.
     * @return A string representing the formatted content of the Liked Content page.
     */
    @Override
    public String printPage(final Library lib, final NormalUser user) {
        constructFollowedPlaylists(user);
        constructLikedSongs(user);

        StringBuilder pageContent = new StringBuilder();

        // Formatting the top 5 liked songs
        pageContent.append("Liked songs:\n\t");
        if (!likedSongs.isEmpty()) {
            String songNames = likedSongs.stream()
                    .map(song -> song.getName() + " - " + song.getArtist())
                    .collect(Collectors.joining(", "));
            pageContent.append("[").append(songNames).append("]");
        } else {
            pageContent.append("[]");
        }

        pageContent.append("\n\n");

        // Formatting the top 5 followed playlists
        pageContent.append("Followed playlists:\n\t");
        if (!followedPlaylists.isEmpty()) {
            String playlistInfo = followedPlaylists.stream()
                    .map(playlist -> playlist.getName() + " - " + playlist.getOwner())
                    .collect(Collectors.joining(", "));
            pageContent.append("[").append(playlistInfo).append("]");
        } else {
            pageContent.append("[]");
        }

        return pageContent.toString();
    }

    @Override
    public PageTypes getPageType() {
        return PageTypes.LIKED_CONTENT_PAGE;
    }
}
