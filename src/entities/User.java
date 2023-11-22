package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private String username;
	private int age;
	private String city;
	private UserPlayer player;
	private List<Song> favoriteSongs;
	private List<Playlist> followedPlaylists;

	public void setUsername(final String username) {
		this.username = username;
	}

	public void setAge(final int age) {
		this.age = age;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public boolean likeUnlikeSong(final Song song) {
		if (favoriteSongs.contains(song)) {
			favoriteSongs.remove(song);
			song.setLikes(song.getLikes() - 1);
			return false;
		} else {
			favoriteSongs.add(song);
			song.setLikes(song.getLikes() + 1);
			return true;
		}
	}

	public boolean followUnfollowPlaylist(final Playlist playlist) {
		if (followedPlaylists.contains(playlist)) {
			followedPlaylists.remove(playlist);
			playlist.setFollowers(playlist.getFollowers() - 1);

			return false;
		}

		followedPlaylists.add(playlist);
		playlist.setFollowers(playlist.getFollowers() + 1);

		return true;
	}

	public List<Playlist> getPlaylistsUserPerspective(List<Playlist> allPlaylists) {
		List<Playlist> userSeenPlaylists = new ArrayList<>();

		for (Playlist playlist : allPlaylists) {
			if (playlist.getOwner().equals(this.username) || playlist.getIsPublic()) {
				userSeenPlaylists.add(playlist);
			}
		}

		return userSeenPlaylists;
	}

	public List<Playlist> getPlaylistsOwnedByUser(List<Playlist> allPlaylists) {
		List<Playlist> userSeenPlaylists = new ArrayList<>();

		for (Playlist playlist : allPlaylists) {
			if (playlist.getOwner().equals(this.username)) {
				userSeenPlaylists.add(playlist);
			}
		}

		return userSeenPlaylists;
	}
}
