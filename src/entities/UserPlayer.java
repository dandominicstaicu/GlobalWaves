package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.Collections;

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
	private Queue<AudioFile> cloneQueueRepeat;
	private Queue<AudioFile> cloneQueueShuffle;
	private Queue<Integer> shuffledIndexesArray;

	public UserPlayer() {
		this.searchBar = new SearchBar();
		this.isPlaying = false;
		this.loadedTimestamp = 0;
		this.isShuffled = false;
		this.isRepeating = 0;
//		this.currentPlaying = null;
//		this.setAudioQueue(new ArrayDeque<>());
		this.audioQueue = new ArrayDeque<>();
		this.cloneQueueRepeat = new ArrayDeque<>();
		this.cloneQueueShuffle = new ArrayDeque<>();
//		this.shuffledIndexesArray = new ArrayDeque<>();
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

				if (audioQueue.isEmpty() && isPlayingPlaylist && isRepeating.equals(1) && cloneQueueRepeat != null) {
					audioQueue.addAll(cloneQueueRepeat);
				}

				if (!audioQueue.isEmpty()) {
					currentAudioDuration = audioQueue.element().getDuration();
					while (loadedTimestamp + currentAudioDuration < currentTimestamp) {
						loadedTimestamp += currentAudioDuration;
						if (isPlayingPlaylist && isRepeating.equals(1)) {
							audioQueue.remove();
						}

						if (!audioQueue.isEmpty()) {
							currentAudioDuration = audioQueue.element().getDuration();
						} else {
							stop();
						}
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
			isRepeating = 0;
			isShuffled = false;
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
				cloneQueueRepeat.clear();
				cloneQueueRepeat.addAll(audioQueue);
			}
		} else if (isRepeating == 1)
			isRepeating = 2;
		else
			isRepeating = 0;

		return isRepeating;
	}

	public Boolean shufflePlaylist(Integer seed) {
		if (isShuffled) {
			isShuffled = false;

			String playingFileName = audioQueue.element().getName();
			int playingIndex = getIndexOfFile(cloneQueueShuffle, playingFileName);

			// remove the first k elements
			for (int i = 0; i < playingIndex; ++i) {
				cloneQueueShuffle.remove();
			}

			// keep in queue only the songs after k
			audioQueue.clear();
			audioQueue.addAll(cloneQueueShuffle);

			return false;
		}

		isShuffled = true;

		shuffledIndexesArray = generateRandomQueue(audioQueue.size(), seed);

		cloneQueueShuffle.addAll(audioQueue);

		// In the main audioQueue generate the shuffled queue
		audioQueue.clear();
		for (int index : shuffledIndexesArray) {
			audioQueue.add(getNthElement(index, cloneQueueShuffle));
		}


		return true;
	}

	public static int getIndexOfFile(Queue<AudioFile> queue, String fileName) {
		int index = 0;
		for (AudioFile audioFile : queue) {
			if (audioFile.getName().equals(fileName)) {
				return index;
			}

			++index;
		}

		return -1;
	}

	public static Queue<Integer> generateRandomQueue(int n, Integer seed) {
		Random random = new Random(seed);
		List<Integer> tmpList = new ArrayList<>();

		for (int i = 0; i < n; ++i) {
			tmpList.add(i);
		}

		Collections.shuffle(tmpList, random);

		return new ArrayDeque<>(tmpList);
	}

	// chatGPT helped me write this method
	public AudioFile getNthElement(int n, Queue<AudioFile> queue) {
		if (n >= 0 && n < queue.size()) {
			List<AudioFile> list = new ArrayList<>(queue);
			return list.get(n);
		}

		// n is out of bounds
		return null;
	}

}
