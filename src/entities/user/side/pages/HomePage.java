package entities.user.side.pages;

import entities.playable.Playlist;
import entities.playable.audio_files.Song;
import entities.user.side.NormalUser;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@Getter
@Setter
public class HomePage implements Page {
    ArrayList<Song> top5Songs;
    ArrayList<Playlist> top5Playlists;

    public HomePage() {
        top5Songs = new ArrayList<>();
        top5Playlists = new ArrayList<>();
    }

    public int constructTopSongs(NormalUser user) {
        ArrayList<Song> songs = user.getFavoriteSongs();
        top5Songs = songs.stream()
                .sorted(Comparator.comparing(Song::getLikes).reversed())
                .limit(5)
                .collect(Collectors.toCollection(ArrayList::new));

        return top5Songs.size();
    }

    public int constructTopPlaylists(NormalUser user) {
        ArrayList<Playlist> playlists = user.getFollowedPlaylists();
        top5Playlists = playlists.stream()
                .sorted(Comparator.comparing(Playlist::getTotalLikes).reversed())
                .limit(5)
                .collect(Collectors.toCollection(ArrayList::new));

        return top5Playlists.size();
    }

    @Override
    public String printPage(NormalUser user) {
        constructTopPlaylists(user);
        constructTopSongs(user);

        StringBuilder pageContent = new StringBuilder();

        // Formatting the top 5 liked songs
        pageContent.append("Liked songs:\n\t");
        if (!top5Songs.isEmpty()) {
            String songNames = top5Songs.stream()
                    .map(Song::getName)  // Assuming Song has a getName() method
                    .collect(Collectors.joining(", "));
            pageContent.append("[").append(songNames).append("]");
        } else {
            pageContent.append("No liked songs");
        }

        pageContent.append("\n\n");

        // Formatting the top 5 followed playlists
        pageContent.append("Followed playlists:\n\t");
        if (!top5Playlists.isEmpty()) {
            String playlistInfo = top5Playlists.stream()
                    .map(playlist -> playlist.getName() + " - " + playlist.getOwner())  // Assuming Playlist has getName() and getOwner() methods
                    .collect(Collectors.joining(", "));
            pageContent.append("[").append(playlistInfo).append("]");
        } else {
            pageContent.append("[]");
        }

        return pageContent.toString();
    }
}
