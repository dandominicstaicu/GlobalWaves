package entities;

import commands.playlist.CreatePlaylist;
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
			return false;
		} else {
			favoriteSongs.add(song);
			return true;
		}
	}
}
