package entities;

import common.Constants;
import entities.playable.Album;
import entities.playable.Playlist;
import entities.playable.audio_files.Song;
import entities.user.side.NormalUser;

import java.util.Comparator;
import java.util.List;

public class Stats {
    public static List<Song> top5Songs(final Library lib) {
        // chatGPT helped me optimise this part (getting the top5 songs)
        return lib.getSongs().stream()
                .sorted(Comparator.comparingInt(Song::getLikes).reversed()) // descending
                .limit(Constants.MAX_LIST_RETURN)
                .toList(); // convert to list
    }

    public static List<Album> top5Albums(final Library lib) {
        return lib.getAlbums().stream()
                .sorted(Comparator.comparingInt((Album a) -> a.getSongs().stream().mapToInt(Song::getLikes).sum()).reversed()
                        .thenComparing(Album::getName))
                .limit(Constants.MAX_LIST_RETURN)
                .toList();
    }

    public static List<Playlist> top5Playlists(final Library lib) {
        // chatGPT helped me optimise this part (getting the top5 playlists)
        return lib.getPlaylists().stream()
                .filter(Playlist::getIsPublic) // filter only public playlists
                .sorted(Comparator.comparingInt(Playlist::getFollowers).reversed()) // descending
                .limit(Constants.MAX_LIST_RETURN) // get the top 5
                .toList(); // convert to list
    }

    public static List<NormalUser> getOnlineUsers(final Library lib) {
        // chatGPT helped me optimise this part (getting the online users)
        return lib.getUsers().stream()
                .filter(NormalUser::getOnline) // filter only online users
                .sorted(Comparator.comparing(NormalUser::getUsername)) // sort by username
                .toList(); // convert to list
    }

}
