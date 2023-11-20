package entities;

import fileio.input.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class Library implements AudioFileCollection {
	private List<Song> songs;
	private List<Podcast> podcasts;
	private List<User> users;
	private List<Playlist> playlists;

	public Library() {
		songs = new ArrayList<>();
		podcasts = new ArrayList<>();
		users = new ArrayList<>();
		playlists = new ArrayList<>();
	}

	public void addSong(Song song) {
		songs.add(song);
	}

	public void removeSong(Song song) {
		songs.remove(song);
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
			Song song = new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(), songInput.getTags(), songInput.getLyrics(),
					songInput.getGenre(), songInput.getReleaseYear(), songInput.getArtist());
			library.addSong(song);
		}

		for (PodcastInput podcastInput : libraryInput.getPodcasts()) {
			ArrayList<Episode> episodes = new ArrayList<>();
			for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
				Episode episode = new Episode(episodeInput.getName(), episodeInput.getDuration(), episodeInput.getDescription());

				episodes.add(episode);
			}

			Podcast podcast = new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes);
			library.addPodcast(podcast);
		}

		for (UserInput userInput : libraryInput.getUsers()) {
			User user = new User(userInput.getUsername(), userInput.getAge(), userInput.getCity(), new UserPlayer(), new ArrayList<>());
			library.addUser(user);
		}

		return library;
	}

//	public List<Playlist> getAllPlaylists() {
//		List<Playlist> allPlaylists = new ArrayList<>();
//
//		for (User user : users) {
//			allPlaylists.addAll(user.getPlaylists());
//		}
//
//		return allPlaylists;
//	}

	public User getUserWithUsername(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}

		return null;
	}

	public boolean createPlaylist(String playlistName, String username) {
		if (returnPlaylistWithName(playlistName) != null)
			return false;

		Playlist playlist = new Playlist(playlistName, username, true, new ArrayList<>(), 0);
		playlists.add(playlist);

		return true;
	}

	public Playlist returnPlaylistWithName(String playlistName) {
		for (Playlist playlist : playlists) {
			if (playlist.getName().equals(playlistName)) {
				return playlist;
			}
		}

		return null;
	}

	public Song searchSongInPlaylist(String songName, Playlist playlist) {
//		Playlist playlist = returnPlaylistWithName(playlistName);
		if (playlist == null)
			return null;

		for (Song song : playlist.getSongs()) {
			if (song.getName().equals(songName)) {
				return song;
			}
		}

		return null;
	}

	public boolean decideAddRemove(Integer playlistID, Song song) {
		Playlist playlist = getPlaylists().get(playlistID - 1);
		Song isSongInPlaylist = searchSongInPlaylist(song.getName(), playlist);

		if (isSongInPlaylist != null) {
			playlist.getSongs().remove(isSongInPlaylist);
			return false;
		} else {
			playlist.getSongs().add(song);
			return true;
		}
	}
}