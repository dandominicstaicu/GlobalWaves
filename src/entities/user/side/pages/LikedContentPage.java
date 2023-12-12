package entities.user.side.pages;

import entities.Library;
import entities.playable.Playlist;
import entities.playable.audio_files.Song;
import entities.user.side.NormalUser;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LikedContentPage implements Page {
    ArrayList<Song> likedSongs;
    ArrayList<Playlist> followedPlaylists;

    public LikedContentPage() {
        this.likedSongs = new ArrayList<>();
        this.followedPlaylists = new ArrayList<>();
    }

    public int constructLikedSongs(NormalUser user) {
        this.likedSongs = user.getFavoriteSongs();
        return this.likedSongs.size();
    }

    public int constructFollowedPlaylists(NormalUser user) {
        this.followedPlaylists = user.getFollowedPlaylists();
        return this.followedPlaylists.size();
    }

    @Override
    public String printPage(final Library lib, final NormalUser user) {
        constructFollowedPlaylists(user);
        constructLikedSongs(user);

        StringBuilder pageContent = new StringBuilder();

        // Formatting the top 5 liked songs
        pageContent.append("Liked songs:\n\t");
        if (!likedSongs.isEmpty()) {
            String songNames = likedSongs.stream()
                    .map(Song::getName)  // Assuming Song has a getName() method
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
                    .map(playlist -> playlist.getName() + " - " + playlist.getOwner())  // Assuming Playlist has getName() and getOwner() methods
                    .collect(Collectors.joining(", "));
            pageContent.append("[").append(playlistInfo).append("]");
        } else {
            pageContent.append("[]");
        }

        return pageContent.toString();
    }
}
