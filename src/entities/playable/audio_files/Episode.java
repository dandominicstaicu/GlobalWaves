package entities.playable.audio_files;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an episode of a podcast, extending the AudioFile class.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Episode extends AudioFile {
    private String description;

    @Override
    public String toString() {
        return "Episode{"
                + "name=" + super.getName()
                + " description='" + description + '\''
                +
                '}';
    }

    /**
     * Constructs a new Episode with the given attributes.
     *
     * @param name        The name of the episode.
     * @param duration    The duration of the episode in seconds.
     * @param description The description of the episode.
     */
    public Episode(final String name, final Integer duration, final String description) {
        super.setName(name);
        super.setDuration(duration);
        this.description = description;
    }

    /**
     * Indicates that this audio file is not a song.
     *
     * @return Always returns false, as this represents an episode, not a song.
     */
    @Override
    public boolean isSong() {
        return false;
    }
}
