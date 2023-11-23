package entities.playable.audio_files;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an abstract audio file, which can be a song or a podcast episode.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AudioFile {
    private String name;
    private Integer duration;
    private Integer playedTime = 0;

    /**
     * Checks if the audio file is a song.
     *
     * @return True if the audio file is a song, false otherwise.
     */
    public abstract boolean isSong();
}
