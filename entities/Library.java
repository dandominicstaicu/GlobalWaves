package entities;

import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Library {
    private List<Song> songs;
    private List<Playlist> playlists;
    private List<Podcast> podcasts;
    private List<User> users;

    public Library() {
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        podcasts = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public void removePlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }

    public void addPodcast(Podcast podcast) {
        podcasts.add(podcast);
    }

    public void removePodcast(Podcast podcast) {
        podcasts.remove(podcast);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public static Library initializeLibrary(LibraryInput libraryInput) {
        Library library = new Library();

        // Convert each SongInput to Song and add to library
        for (SongInput songInput : libraryInput.getSongs()) {
            Song song = new Song(/* parameters based on songInput */);
            library.addSong(song);
        }

        for (PodcastInput podcastInput : libraryInput.getPodcasts()) {
            Podcast podcast = new Podcast(/* parameters based on podcastInput */);
            library.addPodcast(podcast);
        }

        for (UserInput userInput : libraryInput.getUsers()) {
            User user = new User(/* parameters based on userInput */);
            library.addUser(user);
        }

        return library;
    }
}