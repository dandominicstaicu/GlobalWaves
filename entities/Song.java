package entities;

import commands.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Song extends Playable implements AudioFile {
//    private String name;
    private Integer duration;
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private Integer releaseYear;
    private String artist;

    public Song(String name, Integer duration, String Album, ArrayList<String> tags,
                     String lyrics, String genre, Integer releaseYear, String artist) {
        this.name = name;
        this.duration = duration;
        this.album = Album;
        this.tags = tags;
        this.lyrics = lyrics;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.artist = artist;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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
}
