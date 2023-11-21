package entities;

import fileio.input.EpisodeInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Queue;

@Getter
@Setter
@NoArgsConstructor
public class Podcast implements AudioFileCollection, Playable {
	private String name;
	private String owner;
	private ArrayList<Episode> episodes;

	public Podcast(final String name, final String owner, final ArrayList<Episode> episodes) {
		this.name = name;
		this.owner = owner;
		this.episodes = episodes;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public boolean isEmpty() {
		return episodes.isEmpty();
	}

	@Override
	public void loadToQueue(UserPlayer userPlayer) {
		// maybe clear the queue before adding
		userPlayer.getAudioQueue().clear();

		// add all episodes to the queue of the userPlayer
		userPlayer.getAudioQueue().addAll(episodes);
	}

	@Override
	public boolean isPlaylist() {
		return false;
	}

	public void setOwner(final String owner) {
		this.owner = owner;
	}

	public void setEpisodes(final ArrayList<Episode> episodes) {
		this.episodes = episodes;
	}
}
