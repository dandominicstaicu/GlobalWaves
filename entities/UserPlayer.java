package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.Queue;

@Setter
@Getter
@AllArgsConstructor
public class UserPlayer {
	//	private Song currentSong;
//	private Podcast currentPodcast;
	private Integer startTimestamp;
//	private Integer elapsedTimePlaying;

	private Integer lastCommandTimestamp = 0;
	private Integer timeElapsedSinceLastCommand = 0;
	private Integer timeLeftToPlay = 0;

	private SearchBar searchBar;

	private Boolean isPlaying;
	private Boolean isShuffled;
	private Boolean isRepeating;

	private Queue<AudioFile> audioQueue;


	public UserPlayer() {
		this.searchBar = new SearchBar();
		this.isPlaying = false;
		this.startTimestamp = 0;
		this.isShuffled = false;
		this.isRepeating = false;
//		this.currentPlaying = null;
//		this.setAudioQueue(new ArrayDeque<>());
		this.audioQueue = new ArrayDeque<>();
	}

	public void updateTime(Integer currentTimestamp) {
		if (isPlaying) {
			timeElapsedSinceLastCommand = currentTimestamp - lastCommandTimestamp;

			if (timeLeftToPlay - timeElapsedSinceLastCommand < 0 && !audioQueue.isEmpty()) {
				timeLeftToPlay = 0;

				audioQueue.remove();

				if (!audioQueue.isEmpty()) {
//				timeLeftToPlay = audioQueue.element().getDuration();
					this.setTimeLeftToPlay(audioQueue.element().getDuration());

					int started = Math.abs(timeLeftToPlay - timeElapsedSinceLastCommand);
					timeLeftToPlay -= started;
				} else {
					this.pause();
				}
			} else {
				timeLeftToPlay -= timeElapsedSinceLastCommand;
			}
		}
		lastCommandTimestamp = currentTimestamp;
	}

	public boolean loadSource(Playable playable, Integer startTimestamp) {
		if (playable == null) {
			return false;
		}

		if (playable.isEmpty()) {
			return false;
		}

		this.setIsPlaying(true);
		this.setStartTimestamp(startTimestamp);

		// Additional logic to load the AudioFiles from the Playable object
		playable.loadToQueue(this.audioQueue);
		this.setTimeLeftToPlay(audioQueue.element().getDuration());

		return true;
	}

	public void pause() {
		if (isPlaying) {
			this.isPlaying = false;
			// Additional logic to pause the current track or podcast
		}
	}

	public void resume() {
		if (!isPlaying) {
			this.isPlaying = true;
			// Additional logic to resume the current track or podcast
		}
	}

	public void stop() {
		this.isPlaying = false;
		if (!audioQueue.isEmpty())
			audioQueue.clear();

//		this.currentPlaying = null;
//		this.currentSong = null;
//		this.currentPodcast = null;
		// Additional logic to stop and reset the player
	}

	public Integer getRemainedTime() {
		if (audioQueue.isEmpty()) {
			return 0;
		}

		// TODO add remaining time logic
		return timeLeftToPlay;

//		return null;
	}
}
