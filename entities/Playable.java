package entities;


import java.util.Queue;

public interface Playable {
	String name = null;

	boolean isEmpty();

	String getName();

	Integer getDuration(); // TODO remove

	void loadToQueue(Queue<AudioFile> audioQueue);
}
