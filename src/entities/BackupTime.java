//package entities;
//
//public class BackupTime {
//	public void updateTime(Integer currentTimestamp) {
//		if (isPlaying) {
//			timeElapsedSinceLastCommand = currentTimestamp - lastCommandTimestamp;
//			int lastSongLeftToPlay = timeLeftToPlay;
//
//			if (timeLeftToPlay - timeElapsedSinceLastCommand < 0 && !audioQueue.isEmpty()) {
//				timeLeftToPlay = 0;
//
//				int durationOfCurrentAudio = audioQueue.element().getDuration();
//
//				if (isPlayingPlaylist && isRepeating.equals(1)) {
//					audioQueue.add(audioQueue.element());
//					audioQueue.remove();
//				} else if (isPlayingPlaylist && isRepeating.equals(2)) {
//					; // do nothing because I don't want to remove the song nor go to the next
//				} else if (isPlayingPlaylist && isRepeating.equals(0)) {
//					audioQueue.remove();
//				} else if (!isPlayingPlaylist && isRepeating.equals(1)) {
//					// don't remove it now, but set repeat to 0, so it won't do it infinitely
//					isRepeating = 0;
//				} else if (!isPlayingPlaylist && isRepeating.equals(2)) {
//					; // do nothing because I don't want to remove the song nor go to the next
//				} else if (!isPlayingPlaylist && isRepeating.equals(0)) {
//					audioQueue.remove();
//				}
//
//				if (!audioQueue.isEmpty()) {
//					// TODO  watch order of these steps
//					int playedTimeFromThisSong = timeElapsedSinceLastCommand - lastSongLeftToPlay;
//
////					timeLeftToPlay = audioQueue.element().getDuration();
//
////					int started = Math.abs(timeLeftToPlay - timeElapsedSinceLastCommand);
//
////					int newTimeLeftToPlay = audioQueue.element().getDuration() - playedTimeFromThisSong;
//					int newTimeLeftToPlay = durationOfCurrentAudio - playedTimeFromThisSong;
//
//					if (!isPlayingPlaylist && isRepeating == 2 && newTimeLeftToPlay < 0) {
//						while (newTimeLeftToPlay < 0) {
//							newTimeLeftToPlay += audioQueue.element().getDuration();
//						}
//					}
//
//					if (isPlayingPlaylist && isRepeating == 1 && newTimeLeftToPlay < 0) {
////						audioQueue.add(audioQueue.element());
////						audioQueue.remove();
//
//						while (newTimeLeftToPlay < 0) {
//							newTimeLeftToPlay += audioQueue.element().getDuration();
//							audioQueue.add(audioQueue.element());
//							audioQueue.remove();
//						}
//					}
//
//
//					this.setTimeLeftToPlay(newTimeLeftToPlay);
////					timeLeftToPlay -= started;
////					timeLeftToPlay =
//				} else {
//					this.pause();
//				}
//			} else {
//				timeLeftToPlay -= timeElapsedSinceLastCommand;
//			}
//		}
//		lastCommandTimestamp = currentTimestamp;
//	}
//}
