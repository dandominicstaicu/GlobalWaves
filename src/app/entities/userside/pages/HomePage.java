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

@Getter
@Setter
public class HomePage implements Page {
    private ArrayList<Song> top5Songs;
    private ArrayList<Playlist> top5Playlists;

    /**
     * Constructs a HomePage object. Initializes the top5Songs and top5Playlists lists.
     */
    public HomePage() {
        top5Songs = new ArrayList<>();
        top5Playlists = new ArrayList<>();
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

        StringBuilder pageContent = new StringBuilder();

        // Formatting the top 5 liked songs
        pageContent.append("Liked songs:\n\t");
        if (!top5Songs.isEmpty()) {
            String songNames = top5Songs.stream()
                    .map(Song::getName)
                    .collect(Collectors.joining(", "));
            pageContent.append("[").append(songNames).append("]");
        } else {
            pageContent.append("[]");
        }

        pageContent.append("\n\n");

        // Formatting the top 5 followed playlists
        pageContent.append("Followed playlists:\n\t");
        if (!top5Playlists.isEmpty()) {
            String playlistInfo = top5Playlists.stream()
                    .map(Playlist::getName)
                    .collect(Collectors.joining(", "));
            pageContent.append("[").append(playlistInfo).append("]");
        } else {
            pageContent.append("[]");
        }

        return pageContent.toString();
    }

    @Override
    public PageTypes getPageType() {
        return PageTypes.HOME_PAGE;
    }
}
