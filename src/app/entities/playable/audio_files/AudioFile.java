package app.entities.playable.audio_files;

import app.entities.Library;
import app.entities.userside.artist.Artist;
import app.entities.userside.normaluser.NormalUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AudioFile)) return false;
        AudioFile that = (AudioFile) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(playedTime, that.playedTime);
    }

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

    public abstract void editStats(Library lib, NormalUser user);

    public abstract String getFileOwner();
    public abstract String getSongGenre();
}
