package entities.playable;

import entities.playable.audio_files.Song;
import entities.user.side.UserPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Album implements Playable {
    private String name;
    private int releaseYear;
    private String description;
    private ArrayList<Song> songs;
    private String owner;

    public Album(String name, int releaseYear, String description, ArrayList<Song> newSongs, final String owner) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = new ArrayList<>();
        for (Song song : newSongs) {
            Song newSong = new Song(song.getName(), song.getDuration(), song.getAlbum(), song.getTags(),
                    song.getLyrics(), song.getGenre(), song.getReleaseYear(), song.getArtist());

            this.songs.add(newSong);
        }
        this.owner = owner;
    }

    @Override
    public boolean isEmpty() {
        return songs.isEmpty();
    }

    @Override
    public void loadToQueue(UserPlayer userPlayer) {
        System.out.println("TODO implement load for album");
    }

    @Override
    public boolean isPlaylist() {
        return false;
    }
}
