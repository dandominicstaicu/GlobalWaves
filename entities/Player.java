package entities;

import lombok.Getter;
import lombok.Setter;

import fileio.input.LibraryInput;

@Getter
@Setter
public class Player {
	private Song currentSong;
	private Podcast currentPodcast;
	private boolean isPlaying;

	private SearchBar searchBar;
	private Library library;


	public Player(LibraryInput libraryInput) {
		this.currentSong = null;
		this.currentPodcast = null;
		this.isPlaying = false;

		this.searchBar = new SearchBar();
		this.library = Library.initializeLibrary(libraryInput);
	}

	public void playSong(Song song) {
		this.currentSong = song;
		this.currentPodcast = null; // Ensuring only one type of media is active
		this.isPlaying = true;
		// Additional logic to start playing the song
	}

	public void playPodcast(Podcast podcast) {
		this.currentPodcast = podcast;
		this.currentSong = null; // Ensuring only one type of media is active
		this.isPlaying = true;
		// Additional logic to start playing the podcast
	}

	public void pause() {
		if (isPlaying) {
			this.isPlaying = false;
			// Additional logic to pause the current track or podcast
		}
	}

	public void stop() {
		this.isPlaying = false;
		this.currentSong = null;
		this.currentPodcast = null;
		// Additional logic to stop and reset the player
	}

	// You can add more methods as needed, like skip, rewind, etc.
}
