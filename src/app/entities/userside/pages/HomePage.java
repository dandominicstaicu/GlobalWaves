package app.entities.userside.pages;

import app.common.Constants;
import app.common.PageTypes;
import app.entities.Library;
import app.entities.playable.Playlist;
import app.entities.playable.audio_files.Song;
import app.entities.userside.normaluser.NormalUser;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class HomePage implements Page {
    private ArrayList<Song> top5Songs;
    private ArrayList<Playlist> top5Playlists;
    private ArrayList<Song> songRecommendations;
    private Playlist playlistRecommendations;

    /**
     * Constructs a HomePage object. Initializes the top5Songs and top5Playlists lists.
     */
    public HomePage() {
        top5Songs = new ArrayList<>();
        top5Playlists = new ArrayList<>();
        songRecommendations = new ArrayList<>();
    }

    private void constructSongRecommendations(final NormalUser user) {
        songRecommendations = user.getSongRecommendations();
    }

    private void constructPlaylistRecommendations(final NormalUser user) {
        playlistRecommendations = user.getPlaylistsRecommendations();
    }

    /**
     * Constructs a list of the user's top liked songs and stores it in the top5Songs list.
     *
     * @param user The normal user for whom the top songs are being constructed.
     */
    private void constructTopSongs(final NormalUser user) {
        ArrayList<Song> songs = user.getFavoriteSongs();
        top5Songs = songs.stream()
                .sorted(Comparator.comparing(Song::getLikes).reversed())
                .limit(Constants.MAX_LIST_RETURN)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Constructs a list of the user's top followed playlists and stores it in the top5Playlists
     * list.
     *
     * @param user The normal user for whom the top playlists are being constructed.
     */
    private void constructTopPlaylists(final NormalUser user) {
        ArrayList<Playlist> playlists = user.getFollowedPlaylists();
        top5Playlists = playlists.stream()
                .sorted(Comparator.comparing(Playlist::getTotalLikes).reversed())
                .limit(Constants.MAX_LIST_RETURN)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Generates and returns the content of the user's home page.
     * The content includes information about the user's top liked songs and followed playlists.
     *
     * @param lib  The library containing relevant information.
     * @param user The normal user for whom the home page is being generated.
     * @return A string containing the formatted content of the home page.
     */
    @Override
    public String printPage(final Library lib, final NormalUser user) {
        constructTopPlaylists(user);
        constructTopSongs(user);
        constructSongRecommendations(user);
        constructPlaylistRecommendations(user);

        StringBuilder pageContent = new StringBuilder();

        // Formatting the top 5 liked songs
        pageContent.append("Liked songs:\n\t");
        buildPageContent(pageContent, top5Songs.isEmpty(), top5Songs.stream()
                .map(Song::getName));

        // Formatting the top 5 followed playlists
        pageContent.append("Followed playlists:\n\t");
        buildPageContent(pageContent, top5Playlists.isEmpty(), top5Playlists.stream()
                .map(Playlist::getName));

        // Append Song Recommendations
        pageContent.append("Song recommendations:\n\t");
        if (!songRecommendations.isEmpty()) {
            String songRecs = songRecommendations.stream()
                    .map(Song::getName)
                    .collect(Collectors.joining(", "));
            pageContent.append("[").append(songRecs).append("]");
        } else {
            pageContent.append("[]");
        }

        pageContent.append("\n\n");

        // Append Playlist Recommendations
        pageContent.append("Playlists recommendations:\n\t");
        if (playlistRecommendations != null) {
            String playlistRecs = playlistRecommendations.getName();
            pageContent.append("[").append(playlistRecs).append("]");
        } else {
            pageContent.append("[]");
        }

        return pageContent.toString();
    }

    private void buildPageContent(final StringBuilder pageContent, final boolean empty,
                                  final Stream<String> stringStream) {
        if (!empty) {
            String songNames = stringStream
                    .collect(Collectors.joining(", "));
            pageContent.append("[").append(songNames).append("]");
        } else {
            pageContent.append("[]");
        }

        pageContent.append("\n\n");
    }

    /**
     * Gets the type of page associated with this object.
     *
     * @return The PageTypes enum value representing the type of page.
     */
    @Override
    public PageTypes getPageType() {
        return PageTypes.HOME_PAGE;
    }
}
