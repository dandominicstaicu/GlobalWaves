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
		return songs.isEmpty();
	}



    @Override//Queue<AudioFile> audioQueue
    public void loadToQueue(UserPlayer userPlayer) {
        // set isPlayingPlaylist
        userPlayer.setIsPlayingPlaylist(true);

        // maybe clear the queue before adding
        userPlayer.getAudioQueue().clear();


        // add all songs to the queue of the userPlayer
        userPlayer.getAudioQueue().addAll(songs);
    }

    @Override
    public boolean isPlaylist() {
        return true;
    }

    public boolean switchVisibility() {
        isPublic = !isPublic;
        return isPublic;
    }

}
