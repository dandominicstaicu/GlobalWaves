package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Queue;

@Getter
@Setter
@NoArgsConstructor
public class Playlist implements AudioFileCollection, Playable {
    private String name;
    private String owner;
    private Boolean isPublic;
    private List<Song> songs;
    private Integer followers;

    public Playlist(final String name, final String owner, final Boolean isPublic,
                    final List<Song> songs, final Integer followers) {
        this.name = name;
        this.owner = owner;
        this.isPublic = isPublic;
        this.songs = songs;
        this.followers = followers;
    }

    @Override
    public boolean isEmpty() {
        if (songs.isEmpty())
            return true;

        return false;
    }

    @Override
    public Integer getDuration() {
        // TODO remove
        return null;
    }

    @Override
    public void loadToQueue(Queue<AudioFile> audioQueue) {
        // maybe clear the queue before adding

        // add all songs to the queue of the userPlayer
        audioQueue.addAll(songs);
    }
}
