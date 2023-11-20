package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AudioFile {
	private String name;
	private Integer duration;
	private Integer playedTime = 0;

	public abstract boolean isSong();
}


//if (isPlayingPlaylist && isRepeating.equals(1)) {
////							loadedTimestamp += currentAudioDuration;
//		audioQueue.add(audioQueue.element());
//		audioQueue.remove();
//		}