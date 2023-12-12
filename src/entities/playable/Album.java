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
        // clear the queue before adding
        userPlayer.getAudioQueue().clear();

        // add all songs to the queue of the userPlayer
        userPlayer.getAudioQueue().addAll(songs);

        // set in the player a reference to what is loaded
        userPlayer.setLoadedContentReference(this);
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean isPlaylist() {
        return false;
    }

    @Override
    public boolean isLoadedInPlayer(String username) {
        return getOwner().equals(username);
    }

    @Override
    public boolean containsAlbum(Album album) {
        return album.equals(this);
    }
}
