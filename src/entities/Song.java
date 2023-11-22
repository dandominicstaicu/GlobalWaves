package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Song extends AudioFile implements Playable {
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private Integer releaseYear;
    private String artist;
    private Integer likes;

    public Song(String name, Integer duration, String Album, ArrayList<String> tags,
                String lyrics, String genre, Integer releaseYear, String artist) {
        super.setName(name);
        super.setDuration(duration);
        this.album = Album;
        this.tags = tags;
        this.lyrics = lyrics;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.artist = artist;

        this.likes = 0;
    }

    public void setName(String name) {
        super.setName(name);
    }

    public void setDuration(Integer duration) {
        super.setDuration(duration);
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void loadToQueue(UserPlayer userPlayer) {
        // clear the queue before loading
        userPlayer.getAudioQueue().clear();

        // add only one song to the queue of the userPlayer
        userPlayer.getAudioQueue().add(this);
    }

    @Override
    public boolean isPlaylist() {
        return false;
    }

    @Override
    public boolean isSong() {
        return true;
    }
}
