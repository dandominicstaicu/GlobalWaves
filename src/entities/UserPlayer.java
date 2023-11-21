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

	private int playingIndex = -1;
	private int realIndex = -1;

	private List<AudioFile> audioQueue;
	private List<Integer> shuffledIndexes;

	public UserPlayer() {
		this.searchBar = new SearchBar();
		this.isPlaying = false;
		this.loadedTimestamp = 0;
		this.isShuffled = false;
		this.isRepeating = 0;
//		this.currentPlaying = null;
//		this.setAudioQueue(new ArrayDeque<>());
		this.audioQueue = new ArrayList<>();
//		this.shuffledIndexesArray = new ArrayDeque<>();
		this.isPlayingPlaylist = false;
	}

	public void updateTime(Integer currentTimestamp) {
		if (isPlaying && !audioQueue.isEmpty()) {
			timeElapsedSinceLastCommand = currentTimestamp - lastCommandTimestamp;

//			System.out.println("timestamp: " + currentTimestamp);
			int currentAudioDuration = audioQueue.get(playingIndex).getDuration() - audioQueue.get(playingIndex).getPlayedTime();
			if (loadedTimestamp + currentAudioDuration < currentTimestamp) {
				loadedTimestamp = loadedTimestamp + currentAudioDuration;

				if (isPlayingPlaylist && isRepeating.equals(1)) { // repeat all
//					audioQueue.add(audioQueue.get(playingIndex));
//
					if (playingIndex + 1 < audioQueue.size())
//						playingIndex++; // get to the next index in the queue
						 next();

					else
						playingIndex = 0;
				} else if (!isPlayingPlaylist && isRepeating.equals(1)) { // repeat once
					isRepeating = 0;
				} else if (isRepeating.equals(0)) { // no repeat both cases
//					playingIndex++;
					 next();
				}
				// repeat infinite for both cases means just not removing the current element in the queue

				if (playingIndex >= audioQueue.size() && isPlayingPlaylist && isRepeating.equals(1)) {
					playingIndex = 0;
					realIndex = 0;
				} else if (playingIndex >= audioQueue.size()) {
					stop();
				}

				if (playingIndex < audioQueue.size()) {
					currentAudioDuration = audioQueue.get(playingIndex).getDuration();
					while (loadedTimestamp + currentAudioDuration < currentTimestamp) {
						loadedTimestamp += currentAudioDuration;
						if (isPlayingPlaylist && isRepeating.equals(1)) {
							if (playingIndex + 1 < audioQueue.size()) {
//								playingIndex++;
								next();
							} else {
								playingIndex = 0;
								realIndex = 0;
							}
						} else if (playingIndex < audioQueue.size() && !isRepeating.equals(2))
//							playingIndex++;
						 	next();

						if (playingIndex < audioQueue.size()) {
							currentAudioDuration = audioQueue.get(playingIndex).getDuration();
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

	public void next() {
//		if (isShuffled && realIndex + 1 < shuffledIndexes.size()) {
//			// Move to next index in shuffled list
//			realIndex++;
//			playingIndex = shuffledIndexes.get(realIndex);
//		} else if (!isShuffled && playingIndex + 1 < audioQueue.size()) {
//			// Move to next index in regular order
//			playingIndex++;
//			realIndex = playingIndex;
//		}
		if (isShuffled && realIndex + 1 < shuffledIndexes.size()) {
			realIndex++;
			playingIndex = shuffledIndexes.get(realIndex);
		} else {
			playingIndex++;
			realIndex = playingIndex;
		}
	}

	public Boolean shufflePlaylist(Integer seed) {
		if (isShuffled) {
			isShuffled = false;
			realIndex = playingIndex;
		} else {
			isShuffled = true;
			shuffledIndexes = randomiseIndexes(audioQueue.size(), seed);
			realIndex = shuffledIndexes.indexOf(playingIndex);
		}

		return isShuffled;
	}

	public boolean loadSource(Playable playable, Integer startTimestamp, UserPlayer userPlayer) {
		if (playable == null) {
			return false;
		}

//		if (playable.isEmpty()) {
//			return false;
//		}

		this.setIsPlaying(true);
		this.setLoadedTimestamp(startTimestamp);

		// Additional logic to load the AudioFiles from the Playable object
		userPlayer.setPlayingIndex(0);

		playable.loadToQueue(this);

		// in case it was an empty collection added
		if (playable.isEmpty())
			return false;

		this.setTimeLeftToPlay(audioQueue.get(playingIndex).getDuration() - audioQueue.get(playingIndex).getPlayedTime());

		userPlayer.getSearchBar().setSelectedResult(null);

		this.isRepeating = 0;
		this.isShuffled = false;

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

		if (audioQueue != null && playingIndexIsValid()) {
			int currentSecond = audioQueue.get(playingIndex).getDuration() - timeLeftToPlay;
			if (!audioQueue.get(playingIndex).isSong())
				audioQueue.get(playingIndex).setPlayedTime(currentSecond);

			audioQueue.clear();
			playingIndex = -1;
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
		} else if (isRepeating == 1)
			isRepeating = 2;
		else
			isRepeating = 0;

		return isRepeating;
	}

	public boolean playingIndexIsValid() {
		return playingIndex >= 0 && playingIndex < audioQueue.size();
	}

	public static List<Integer> randomiseIndexes(int n, Integer seed) {
		Random random = new Random(seed);
		List<Integer> tmpList = new ArrayList<>();

		for (int i = 0; i < n; ++i) {
			tmpList.add(i);
		}

		Collections.shuffle(tmpList, random);

		return new ArrayList<>(tmpList);
	}


}
