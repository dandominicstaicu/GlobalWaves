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
	private Integer loadedTimestamp;

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

	private boolean nextWasLastCommand = false;

	private int pauseStartTimeStamp = 0;

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
		if (!playingIndexIsValid()) {
			lastCommandTimestamp = currentTimestamp;
			stop();
			return;
		}

		if (!isPlaying) {
//			lastCommandTimestamp = currentTimestamp;
//			return;
			if (playingIndexIsValid())
				loadedTimestamp += (currentTimestamp - pauseStartTimeStamp);
			if (nextWasLastCommand)
				isPlaying = true;
		} else {


			timeElapsedSinceLastCommand = currentTimestamp - lastCommandTimestamp;
			int currentAudioDuration = audioQueue.get(playingIndex).getDuration() - audioQueue.get(playingIndex).getPlayedTime();

			if (loadedTimestamp + currentAudioDuration < currentTimestamp) {
				loadedTimestamp = loadedTimestamp + currentAudioDuration;

				handleRepeatCase(currentTimestamp);

				if (playingIndex >= audioQueue.size()) {
					if (isPlayingPlaylist && isRepeating.equals(1)) {
						playingIndex = 0;
						realIndex = 0;
					} else {
						stop();
					}
				}

				if (playingIndex < audioQueue.size()) {
					currentAudioDuration = audioQueue.get(playingIndex).getDuration();
					while (loadedTimestamp + currentAudioDuration < currentTimestamp) {
						// repeat logic
						// Update the loaded timestamp to account for the duration of the current track
						loadedTimestamp += currentAudioDuration;

						// Handle the repeat and playlist logic for the next track
						handleRepeatCase(currentTimestamp);

						// Check if the playlist has ended
						if (playingIndex >= audioQueue.size()) {
							if (isPlayingPlaylist && isRepeating.equals(1)) {
								playingIndex = 0; // Start from the beginning if repeating is enabled
								realIndex = 0;
							} else {
								stop(); // Stop playing if the end of the playlist is reached
								break; // Exit the while loop
							}
						}

						// Update the duration for the new current track
						if (playingIndex < audioQueue.size()) {
							currentAudioDuration = audioQueue.get(playingIndex).getDuration();
						} else {
							break; // Exit the while loop if there are no more tracks
						}

					}

					timeLeftToPlay = loadedTimestamp + currentAudioDuration - currentTimestamp;
				} else {
					pause(currentTimestamp);
				}

			} else {
//			if (isPlaying)
				timeLeftToPlay -= timeElapsedSinceLastCommand;
			}
		}

		if (nextWasLastCommand)
			nextWasLastCommand = false;
		lastCommandTimestamp = currentTimestamp;
	}

	private void handleRepeatCase(int currentTimestamp) {
		if (isPlayingPlaylist && isRepeating.equals(1)) {
			if (playingIndex + 1 < audioQueue.size()) {
				next(false, currentTimestamp);
			} else {
				playingIndex = 0;
				realIndex = 0;
			}
		} else if (!isPlayingPlaylist && isRepeating.equals(1)) {
			isRepeating = 0;
		} else if (isRepeating.equals(0)) {
			next(false, currentTimestamp);
		}
	}

	public void next(boolean isCommand, int currentTimestamp) {
		if (!isShuffled || !(isRepeating == 2))
			handleShuffle();


		if (isCommand && playingIndex < audioQueue.size()) {
			timeLeftToPlay = audioQueue.get(playingIndex).getDuration();


			// Reset played time of the new track
			loadedTimestamp = currentTimestamp;
			if (!isPlaying)
				isPlaying = true;


			audioQueue.get(playingIndex).setPlayedTime(0);
		} else if (isCommand && playingIndex >= audioQueue.size()) {
			if (isPlayingPlaylist && isRepeating == 1) {
				playingIndex = 0;
				realIndex = 0;

				timeLeftToPlay = audioQueue.get(playingIndex).getDuration();

				loadedTimestamp = currentTimestamp;
				if (!isPlaying)
					isPlaying = true;

				audioQueue.get(playingIndex).setPlayedTime(0);
			}
		}

		if (isCommand) {
			nextWasLastCommand = true;
		}

	}

	void handleShuffle() {
		if (isShuffled && realIndex + 1 < shuffledIndexes.size()) {
			realIndex++;
			playingIndex = shuffledIndexes.get(realIndex);
		} else if (isShuffled && realIndex + 1 >= shuffledIndexes.size() && isRepeating == 1 && isPlayingPlaylist) {
			realIndex = 0;
			playingIndex = shuffledIndexes.get(realIndex);
		} else if (isShuffled && realIndex + 1 >= shuffledIndexes.size()) {
			realIndex++;
			playingIndex = realIndex;
		} else {
			playingIndex++;
			realIndex = playingIndex;
		}
	}

	public AudioFile prev(int currentTimestamp) {
		if (audioQueue.isEmpty() || playingIndex < 0) {
			return null;
		}

		int currentSecond = audioQueue.get(playingIndex).getDuration() - timeLeftToPlay;

		if (currentSecond > 1) {
			timeLeftToPlay = audioQueue.get(playingIndex).getDuration();

			if (!isPlaying) {
				isPlaying = true;
				loadedTimestamp = currentTimestamp;
			}

			loadedTimestamp = currentTimestamp;
			return audioQueue.get(playingIndex);
		} else {
			if (isShuffled && realIndex > 0) {
				realIndex--;
				playingIndex = shuffledIndexes.get(realIndex);

				if (!isPlaying) {
					isPlaying = true;
					loadedTimestamp = currentTimestamp;
				}
			} else if (!isShuffled && playingIndex > 0) {
				playingIndex--;
				realIndex = playingIndex;

				if (!isPlaying) {
					isPlaying = true;
					loadedTimestamp = currentTimestamp;
				}
			} else {
				timeLeftToPlay = audioQueue.get(playingIndex).getDuration();

				if (!isPlaying) {
					isPlaying = true;
					loadedTimestamp = currentTimestamp;
				}
				return audioQueue.get(playingIndex);
			}

			loadedTimestamp = currentTimestamp;

			timeLeftToPlay = audioQueue.get(playingIndex).getDuration();
			return audioQueue.get(playingIndex);
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
		userPlayer.setRealIndex(0);

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

	public void pause(int currentTimeStamp) {
		if (isPlaying) {
			this.isPlaying = false;
			// Additional logic to pause the current track or podcast

			pauseStartTimeStamp = currentTimeStamp;
		}
	}

	public void resume() {
		if (!isPlaying) {
			this.isPlaying = true;
			// Additional logic to resume the current track or podcast
			pauseStartTimeStamp = 0;
		}
	}

	public void stop() {
		this.isPlaying = false;
		isShuffled = false;

		if (audioQueue != null && playingIndexIsValid()) {
			int currentSecond = audioQueue.get(playingIndex).getDuration() - timeLeftToPlay;
			if (!audioQueue.get(playingIndex).isSong())
				audioQueue.get(playingIndex).setPlayedTime(currentSecond);

			audioQueue.clear();
			playingIndex = -1;
			realIndex = -1;
			isRepeating = 0;

		}

	}

	// SealedClasses
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

	public boolean nextPlayingIndexIsValid() {
		return playingIndex + 1 >= 0 && playingIndex + 1 < audioQueue.size();
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

	public void forward() {
		if (audioQueue.isEmpty() || playingIndex < 0 || playingIndex >= audioQueue.size()) {
			return; // No source loaded or index invalid
		}

		AudioFile currentAudio = audioQueue.get(playingIndex);
		int remainingTime = currentAudio.getDuration() - currentAudio.getPlayedTime();

		// Check if the current audio is a podcast
		if (!currentAudio.isSong()) {
			if (remainingTime > 90) {
				// Advance by 90 seconds
				currentAudio.setPlayedTime(currentAudio.getPlayedTime() + 90);
				timeLeftToPlay -= 90;
			} else {
				// Move to the next episode
				if (playingIndex + 1 < audioQueue.size()) {
					playingIndex++;
					realIndex = isShuffled ? shuffledIndexes.indexOf(playingIndex) : playingIndex;
					timeLeftToPlay = audioQueue.get(playingIndex).getDuration();
				} else {
					// Last episode in the queue, stop or handle according to your requirements
					stop();
				}
			}
		}
	}

	public void backward() {
		if (audioQueue.isEmpty() || playingIndex < 0 || playingIndex >= audioQueue.size()) {
			return; // No source loaded or index invalid
		}

		AudioFile currentAudio = audioQueue.get(playingIndex);

		// Check if the current audio is a podcast
		if (!currentAudio.isSong()) {
			int currentPlayedTime = currentAudio.getPlayedTime();

			if (currentPlayedTime > 90) {
				// Rewind by 90 seconds
				currentAudio.setPlayedTime(currentPlayedTime - 90);
				timeLeftToPlay += 90;
			} else {
				// Reset to the start of the current episode
				currentAudio.setPlayedTime(0);
				timeLeftToPlay = currentAudio.getDuration();
			}
		}
	}
}
