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
	private Integer loadedTimestamp;
//	private Integer elapsedTimePlaying;

	private Integer lastCommandTimestamp = 0;
	private Integer timeElapsedSinceLastCommand = 0;
	private Integer timeLeftToPlay = 0;

	private SearchBar searchBar;

	private Boolean isPlayingPlaylist;
	private Boolean isPlaying;
	private Boolean isShuffled;
	private Integer isRepeating;

	private Queue<AudioFile> audioQueue;
	private Queue<AudioFile> cloneQueue;

	public UserPlayer() {
		this.searchBar = new SearchBar();
		this.isPlaying = false;
		this.loadedTimestamp = 0;
		this.isShuffled = false;
		this.isRepeating = 0;
//		this.currentPlaying = null;
//		this.setAudioQueue(new ArrayDeque<>());
		this.audioQueue = new ArrayDeque<>();
		this.cloneQueue = new ArrayDeque<>();
		this.isPlayingPlaylist = false;
	}

	public void updateTime(Integer currentTimestamp) {
		if (isPlaying) {
			timeElapsedSinceLastCommand = currentTimestamp - lastCommandTimestamp;

			int currentAudioDuration = audioQueue.element().getDuration() - audioQueue.element().getPlayedTime();
			if (loadedTimestamp + currentAudioDuration < currentTimestamp) {
				loadedTimestamp = loadedTimestamp + currentAudioDuration;

				if (isPlayingPlaylist && isRepeating.equals(1)) { // repeat all
					audioQueue.add(audioQueue.element());
					audioQueue.remove();
				} else if (!isPlayingPlaylist && isRepeating.equals(1)) { // repeat once
					isRepeating = 0;
				} else if (isRepeating.equals(0)) { // no repeat both cases
					audioQueue.remove();
				}
				// repeat infinite for both cases means just not removing the current element in the queue

				if (audioQueue.isEmpty() && isPlayingPlaylist && isRepeating.equals(1) && cloneQueue != null) {
					audioQueue.addAll(cloneQueue);
				}

				if (!audioQueue.isEmpty()) {
					currentAudioDuration = audioQueue.element().getDuration();
					while (loadedTimestamp + currentAudioDuration < currentTimestamp) {
						loadedTimestamp += currentAudioDuration;
						if (isPlayingPlaylist && isRepeating.equals(1)) {
							audioQueue.remove();
						}

						currentAudioDuration = audioQueue.element().getDuration();
					}

					timeLeftToPlay = loadedTimestamp + currentAudioDuration - currentTimestamp;
				} else {
					this.pause();
				}

			} else {
				timeLeftToPlay -= timeElapsedSinceLastCommand;
			}
		}

		lastCommandTimestamp = currentTimestamp;
	}

	public boolean loadSource(Playable playable, Integer startTimestamp, UserPlayer userPlayer) {
		if (playable == null) {
			return false;
		}

		if (playable.isEmpty()) {
			return false;
		}

		this.setIsPlaying(true);
		this.setLoadedTimestamp(startTimestamp);

		// Additional logic to load the AudioFiles from the Playable object
		playable.loadToQueue(this);
		this.setTimeLeftToPlay(audioQueue.element().getDuration() - audioQueue.element().getPlayedTime());

		userPlayer.getSearchBar().setSelectedResult(null);
		this.isRepeating = 0;

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

		if (audioQueue != null && !audioQueue.isEmpty()) {
			int currentSecond = audioQueue.element().getDuration() - timeLeftToPlay;
			if (!audioQueue.element().isSong())
				audioQueue.element().setPlayedTime(currentSecond);

			audioQueue.clear();
		}

//		if (!audioQueue.isEmpty())


//		this.currentPlaying = null;
//		this.currentSong = null;
//		this.currentPodcast = null;
		// Additional logic to stop and reset the player
	}

	public Integer getRemainedTime() {
		if (audioQueue.isEmpty()) {
			return 0;
		}

		return timeLeftToPlay;
	}

	public Integer changeRepeatState() {
		if (isRepeating == 0) {
			isRepeating = 1;
			if (isPlayingPlaylist && audioQueue != null) {
				cloneQueue.clear();
				cloneQueue.addAll(audioQueue);
			}
		} else if (isRepeating == 1)
			isRepeating = 2;
		else
			isRepeating = 0;

		return isRepeating;
	}

//	public Integer changeRepeatStateAudioFile() {
//
//		return -1;
//	}
}
