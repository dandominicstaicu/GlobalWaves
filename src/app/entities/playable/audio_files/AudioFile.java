package app.entities.playable.audio_files;

import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Objects;

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
     * Compares this AudioFile object with another object for equality.
     *
     * @param o The object to compare with this AudioFile.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof AudioFile that)) {
            return false;
        }

        return Objects.equals(name, that.name)
                && Objects.equals(duration, that.duration)
                && Objects.equals(playedTime, that.playedTime);
    }

    /**
     * Generates a hash code for this AudioFile object based on its attributes.
     *
     * @return The hash code for this AudioFile object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, duration, playedTime);
    }

    /**
     * Checks if the audio file is a song.
     *
     * @return True if the audio file is a song, false otherwise.
     */
    public abstract boolean isSong();

    /**
     * Edits statistics related to this object, typically performed after playback.
     *
     * @param lib  The Library object containing song data.
     * @param user The NormalUser initiating the playback.
     */
    public abstract void editStats(Library lib, NormalUser user);

    /**
     * Retrieves the file owner associated with this object.
     *
     * @return The owner of the file as a String.
     */
    public abstract String getFileOwner();

    /**
     * Retrieves the genre of the song associated with this object.
     *
     * @return The genre of the song as a String.
     */
    public abstract String getSongGenre();
}
