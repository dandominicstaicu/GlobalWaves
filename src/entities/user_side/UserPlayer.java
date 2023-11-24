package entities.user_side;

import common.Constants;
import common.RepeatStates;
import entities.playable.Playable;
import entities.playable.audio_files.AudioFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents the Music Player for each individual user
 */
@Setter
@Getter
@AllArgsConstructor
public class UserPlayer {
    private int loadedTimestamp;
    private int lastCommandTimestamp = 0;
    private int timeElapsedSinceLastCommand = 0;
    private int timeLeftToPlay = 0;
    private int pauseStartTimeStamp = 0;
    private int initialStartTimestamp = 0;

    private boolean nextWasLastCommand = false;

    private SearchBar searchBar;

    private Boolean isPlayingPlaylist;
    private Boolean isPlaying;
    private Boolean isShuffled;
    private RepeatStates isRepeating;

    private int playingIndex = Constants.OUTBOUND;
    private int realIndex = Constants.OUTBOUND;

    private List<AudioFile> audioQueue;
    private List<Integer> shuffledIndexes;

    /**
     * Constructs a UserPlayer instance.
     */
    public UserPlayer() {
        this.searchBar = new SearchBar();
        this.isPlaying = false;
        this.loadedTimestamp = 0;
        this.isShuffled = false;
        this.isRepeating = RepeatStates.NO_REPEAT;
        this.audioQueue = new ArrayList<>();
        this.isPlayingPlaylist = false;
    }

    /**
     * Updates the player's state based on the current timestamp.
     *
     * @param currentTimestamp The current timestamp to update the player's state.
     */
    public void updateTime(final Integer currentTimestamp) {
        if (!playingIndexIsValid()) {
            lastCommandTimestamp = currentTimestamp;
            stop();
            return;
        }

        if (!isPlaying) {
            if (playingIndexIsValid()) {
                loadedTimestamp = initialStartTimestamp + (currentTimestamp - pauseStartTimeStamp);
            }
            if (nextWasLastCommand) {
                isPlaying = true;
            }
        } else {
            timeElapsedSinceLastCommand = currentTimestamp - lastCommandTimestamp;
            final int playedTime = audioQueue.get(playingIndex).getPlayedTime();
            int currentAudioDuration = audioQueue.get(playingIndex).getDuration() - playedTime;

            if (loadedTimestamp + currentAudioDuration < currentTimestamp) {
                loadedTimestamp = loadedTimestamp + currentAudioDuration;
                initialStartTimestamp = loadedTimestamp;

                handleRepeatCase(currentTimestamp);

                if (playingIndex >= audioQueue.size()) {
                    stop();
                }

                if (playingIndex < audioQueue.size()) {
                    currentAudioDuration = audioQueue.get(playingIndex).getDuration();
                    while (loadedTimestamp + currentAudioDuration < currentTimestamp) {
                        // Repeat logic
                        // Update the loaded timestamp to account for the duration of the
                        // current track
                        loadedTimestamp += currentAudioDuration;
                        initialStartTimestamp = loadedTimestamp;

                        // Handle the repeat and playlist logic for the next track
                        handleRepeatCase(currentTimestamp);

                        // Check if the playlist has ended
                        if (playingIndex >= audioQueue.size()) {
                            stop(); // Stop playing if the end of the playlist is reached
                            break; // Exit the while loop
                        }

                        // Update the duration for the new current track
                        currentAudioDuration = audioQueue.get(playingIndex).getDuration();
                    }

                    timeLeftToPlay = loadedTimestamp + currentAudioDuration - currentTimestamp;
                } else {
                    pause(currentTimestamp);
                }
            } else {
                timeLeftToPlay -= timeElapsedSinceLastCommand;
            }
        }

        if (nextWasLastCommand) {
            nextWasLastCommand = false;
        }
        lastCommandTimestamp = currentTimestamp;
    }

    /**
     * Handles repeat logic based on the current timestamp.
     *
     * @param currentTimestamp The current timestamp to manage repeating logic.
     */
    private void handleRepeatCase(final int currentTimestamp) {
        if (isRepeating == RepeatStates.REPEAT_ALL) {
            if (playingIndex + 1 < audioQueue.size()) {
                next(false, currentTimestamp);
            } else {
                playingIndex = 0;
                realIndex = 0;
            }
        } else if (isRepeating == RepeatStates.REPEAT_ONCE) {
            isRepeating = RepeatStates.NO_REPEAT;
        } else if (isRepeating == RepeatStates.NO_REPEAT) {
            next(false, currentTimestamp);
        }
    }

    /**
     * Processes the next track in the playlist or shuffled list.
     *
     * @param isCommand        Indicates if this method is triggered by a command.
     * @param currentTimestamp The current timestamp for updating playback.
     */
    public void next(final boolean isCommand, final int currentTimestamp) {
        if (isRepeating == RepeatStates.REPEAT_CURRENT_SONG
                || isRepeating == RepeatStates.REPEAT_INFINITE) {
            audioQueue.get(playingIndex).setPlayedTime(Constants.START_OF_SONG);
            loadedTimestamp = currentTimestamp;
            initialStartTimestamp = loadedTimestamp;
            timeLeftToPlay = audioQueue.get(playingIndex).getDuration();
            return;
        }

        handleShuffle();

        if (isCommand && playingIndex < audioQueue.size()) {
            timeLeftToPlay = audioQueue.get(playingIndex).getDuration();

            // Reset played time of the new track
            loadedTimestamp = currentTimestamp;
            initialStartTimestamp = loadedTimestamp;
            if (!isPlaying) {
                isPlaying = true;
            }

            audioQueue.get(playingIndex).setPlayedTime(0);
        } else if (isCommand) { //  && playingIndex >= audioQueue.size() which is always true
            if (isPlayingPlaylist && isRepeating == RepeatStates.REPEAT_ALL) {
                playingIndex = 0;
                realIndex = 0;
                timeLeftToPlay = audioQueue.get(playingIndex).getDuration();
                loadedTimestamp = currentTimestamp;
                initialStartTimestamp = loadedTimestamp;

                if (!isPlaying) {
                    isPlaying = true;
                }

                audioQueue.get(playingIndex).setPlayedTime(0);
            }
        }

        if (isCommand) {
            nextWasLastCommand = true;
        }
    }

    /**
     * Handles the logic for determining the next track in shuffled mode.
     */
    void handleShuffle() {
        if (isShuffled && realIndex + 1 < shuffledIndexes.size()) {
            realIndex++;
            playingIndex = shuffledIndexes.get(realIndex);
        } else if (isShuffled
                && realIndex + 1 >= shuffledIndexes.size()
                && isRepeating == RepeatStates.REPEAT_ALL) {
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

    /**
     * Processes the previous track in the playlist or shuffled list.
     *
     * @param currentTimestamp The current timestamp for updating playback.
     * @return The previous audio file in the playlist or shuffled list.
     */
    public AudioFile prev(final int currentTimestamp) {
        if (audioQueue.isEmpty() || playingIndex < Constants.LOWER_BOUND) {
            return null;
        }

        int currentSecond = audioQueue.get(playingIndex).getDuration() - timeLeftToPlay;

        if (currentSecond > 1) {
            timeLeftToPlay = audioQueue.get(playingIndex).getDuration();

            if (!isPlaying) {
                isPlaying = true;
            }

            loadedTimestamp = currentTimestamp;
            initialStartTimestamp = loadedTimestamp;
            return audioQueue.get(playingIndex);
        }

        if (isShuffled && realIndex > Constants.LOWER_BOUND) {
            realIndex--;
            playingIndex = shuffledIndexes.get(realIndex);

            if (!isPlaying) {
                isPlaying = true;
            }
        } else if (!isShuffled && playingIndex > Constants.LOWER_BOUND) {
            playingIndex--;
            realIndex = playingIndex;

            if (!isPlaying) {
                isPlaying = true;
            }
        } else {
            timeLeftToPlay = audioQueue.get(playingIndex).getDuration();

            if (!isPlaying) {
                isPlaying = true;
                loadedTimestamp = currentTimestamp;
                initialStartTimestamp = loadedTimestamp;
            }
            return audioQueue.get(playingIndex);
        }

        loadedTimestamp = currentTimestamp;
        initialStartTimestamp = loadedTimestamp;
        timeLeftToPlay = audioQueue.get(playingIndex).getDuration();

        return audioQueue.get(playingIndex);

    }

    /**
     * Toggles and manages the shuffled playlist state.
     *
     * @param seed The seed used for randomizing the shuffled playlist.
     * @return The updated shuffled state.
     */
    public Boolean shufflePlaylist(final Integer seed) {
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

    /**
     * Loads a playable source into the player.
     *
     * @param playable       The playable item to load.
     * @param startTimestamp The start timestamp for the playable item.
     * @param userPlayer     The UserPlayer instance.
     * @return True if the source is successfully loaded, false otherwise.
     */
    public boolean loadSource(final Playable playable,
                              final Integer startTimestamp,
                              final UserPlayer userPlayer) {
        if (playable == null) {
            return false;
        }

        this.setIsPlaying(true);
        this.setLoadedTimestamp(startTimestamp);
        this.setInitialStartTimestamp(startTimestamp);

        // Additional logic to load the AudioFiles from the Playable object
        userPlayer.setPlayingIndex(Constants.START_OF_SONG);
        userPlayer.setRealIndex(Constants.START_OF_SONG);

        playable.loadToQueue(this);

        // in case it was an empty collection added
        if (playable.isEmpty()) {
            return false;
        }

        int playedTime = audioQueue.get(playingIndex).getPlayedTime();
        this.setTimeLeftToPlay(audioQueue.get(playingIndex).getDuration() - playedTime);

        userPlayer.getSearchBar().setSelectedResult(null);

        this.isRepeating = RepeatStates.NO_REPEAT;
        this.isShuffled = false;

        return true;
    }

    /**
     * Pauses the current playback.
     *
     * @param currentTimeStamp The timestamp when the pause is initiated.
     */
    public void pause(final int currentTimeStamp) {
        if (isPlaying) {
            this.isPlaying = false;

            // Additional logic to pause the current track or podcast
            pauseStartTimeStamp = currentTimeStamp;
        }
    }

    /**
     * Resumes playback from a paused state.
     */
    public void resume() {
        if (!isPlaying) {
            this.isPlaying = true;

            // Additional logic to resume the current track or podcast
            pauseStartTimeStamp = 0;
        }
    }

    /**
     * Stops the current playback and resets the player state.
     */
    public void stop() {
        this.isPlaying = false;
        isShuffled = false;

        if (audioQueue != null && playingIndexIsValid()) {
            int currentSecond = audioQueue.get(playingIndex).getDuration() - timeLeftToPlay;
            if (!audioQueue.get(playingIndex).isSong()) {
                audioQueue.get(playingIndex).setPlayedTime(currentSecond);
            }

            audioQueue.clear();
            playingIndex = Constants.OUTBOUND;
            realIndex = Constants.OUTBOUND;
            isRepeating = RepeatStates.NO_REPEAT;
        }
    }

    /**
     * Retrieves the remaining play time of the current audio file.
     *
     * @return Remaining play time in seconds, or 0 if the audio queue is empty.
     */
    public Integer getRemainedTime() {
        if (audioQueue.isEmpty()) {
            return 0;
        }

        return timeLeftToPlay;
    }

    /**
     * Changes the repeat state of the player.
     *
     * @return The new repeat state.
     */
    public RepeatStates changeRepeatState() {
        if (isRepeating == RepeatStates.NO_REPEAT) {
            if (isPlayingPlaylist) {
                isRepeating = RepeatStates.REPEAT_ALL;
            } else {
                isRepeating = RepeatStates.REPEAT_ONCE;
            }
        } else if (isRepeating == RepeatStates.REPEAT_ONCE
                || isRepeating == RepeatStates.REPEAT_ALL) {
            if (isPlayingPlaylist) {
                isRepeating = RepeatStates.REPEAT_CURRENT_SONG;
            } else {
                isRepeating = RepeatStates.REPEAT_INFINITE;
            }
        } else {
            isRepeating = RepeatStates.NO_REPEAT;
        }

        return isRepeating;
    }

    /**
     * Checks if the current playing index is valid.
     *
     * @return True if the index is within the bounds of the audio queue.
     */
    public boolean playingIndexIsValid() {
        return playingIndex >= 0 && playingIndex < audioQueue.size();
    }

    /**
     * Randomizes indexes for shuffle functionality.
     *
     * @param size The number of indexes to randomize.
     * @param seed The seed for the random number generator.
     * @return A list of randomized indexes.
     */
    public static List<Integer> randomiseIndexes(final int size, final Integer seed) {
        Random random = new Random(seed);
        // Use a lambda expression with Streams API to generate the list of indexes
        List<Integer> tmpList = IntStream.range(0, size)  // Create a stream of int from 0 to size-1
                .boxed()         // Box each int to an Integer object
                .collect(Collectors.toList()); // Collect into a list

        // Shuffle the list using Collections.shuffle
        Collections.shuffle(tmpList, random);

        return tmpList;
    }

    /**
     * Advances the playback by 90 seconds if the current audio file is a podcast.
     */
    public void forward() {
        if (audioQueue.isEmpty() || playingIndex < 0 || playingIndex >= audioQueue.size()) {
            return; // No source loaded or index invalid
        }

        // get the value from constants and use a shorter variable name
        int skipThreshold = Constants.SKIP_PODCAST_THRESHOLD;

        AudioFile currentAudio = audioQueue.get(playingIndex);
        int remainingTime = currentAudio.getDuration() - currentAudio.getPlayedTime();

        // Check if the current audio is a podcast
        if (!currentAudio.isSong()) {
            if (remainingTime > skipThreshold) {
                // Advance by 90 seconds
                currentAudio.setPlayedTime(currentAudio.getPlayedTime() + skipThreshold);
                timeLeftToPlay -= skipThreshold;
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

    /**
     * Rewinds the playback by 90 seconds if the current audio file is a podcast,
     * or resets to the start of the current episode.
     */
    public void backward() {
        if (audioQueue.isEmpty() || playingIndex < Constants.LOWER_BOUND
                || playingIndex >= audioQueue.size()) {
            return; // No source loaded or index invalid
        }

        AudioFile currentAudio = audioQueue.get(playingIndex);

        // get the value from constants and use a shorter variable name
        int skipThreshold = Constants.SKIP_PODCAST_THRESHOLD;

        // Check if the current audio is a podcast
        if (!currentAudio.isSong()) {
            int currentPlayedTime = currentAudio.getPlayedTime();

            if (currentPlayedTime > skipThreshold) {
                // Rewind by 90 seconds
                currentAudio.setPlayedTime(currentPlayedTime - skipThreshold);
                timeLeftToPlay += skipThreshold;
            } else {
                // Reset to the start of the current episode
                currentAudio.setPlayedTime(Constants.START_OF_SONG);
                timeLeftToPlay = currentAudio.getDuration();
            }
        }
    }
}
