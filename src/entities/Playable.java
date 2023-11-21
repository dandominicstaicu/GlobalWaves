package entities;


import java.util.Queue;

public interface Playable {
	String name = null;

	boolean isEmpty();

	String getName();

	void loadToQueue(UserPlayer userPlayer);

	boolean isPlaylist();
}
