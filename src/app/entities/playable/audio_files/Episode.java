package app.entities.playable.audio_files;

import app.entities.Library;
import app.entities.userside.host.Host;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.normaluser.WrappedStats;
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
    private String owner;

    /**
     * Returns a string representation of the episode.
     *
     * @return A string representation of the episode.
     */
    @Override
    public String toString() {
        return "Episode{"
                + "name=" + super.getName()
                + " description='" + description + '\''
                + '}';
    }

    /**
     * Constructs a new Episode with the given attributes.
     *
     * @param name        The name of the episode.
     * @param duration    The duration of the episode in seconds.
     * @param description The description of the episode.
     */
    public Episode(final String name, final Integer duration, final String description, final String owner) {
        super.setName(name);
        super.setDuration(duration);
        this.description = description;
        this.owner = owner;
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

    @Override
    public void editStats(final Library lib, final NormalUser user) {
//        System.out.println("Editing stats for episode" + super.getName());
        WrappedStats stats = user.getWrappedStats();

        // stats for the user
        stats.addEpisodeListenCount(this.getName());
        stats.registerStats();


        // stats for the host
        System.out.println("blyat HOST NAME: " + this.getOwner());
        Host host = lib.getHostWithName(this.getOwner());
        if (host == null) {
            System.out.println("CYKA" + this.getOwner());
        }
        if (host != null) {
            WrappedStats hostStats = host.getWrappedStats();

            hostStats.addEpisodeListenCount(this.getName());
            hostStats.addListenerCount(user.getUsername());

            hostStats.registerStats();
        }
    }
}
