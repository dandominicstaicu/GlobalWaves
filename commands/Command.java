package commands;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.player.*;
import commands.playlist.CreatePlaylist;
import commands.playlist.FollowPlaylist;
import commands.playlist.ShowPlaylist;
import commands.playlist.SwitchVisibility;
import commands.searchbar.Search;
import commands.searchbar.Select;
import commands.stats.GetTop5Playlists;
import commands.stats.GetTop5Songs;
import commands.stats.ShowPreferredSongs;
import entities.MainPlayer;
import lombok.*;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "command")
@JsonSubTypes({
		@JsonSubTypes.Type(value = Search.class, name = "search"),
		@JsonSubTypes.Type(value = Select.class, name = "select"),
		@JsonSubTypes.Type(value = CreatePlaylist.class, name = "createPlaylist"),
		@JsonSubTypes.Type(value = FollowPlaylist.class, name = "follow"),
		@JsonSubTypes.Type(value = ShowPlaylist.class, name = "showPlaylists"),
		@JsonSubTypes.Type(value = SwitchVisibility.class, name = "switchVisibility"),
		@JsonSubTypes.Type(value = Load.class, name = "load"),
		@JsonSubTypes.Type(value = PlayPause.class, name = "playPause"),
		@JsonSubTypes.Type(value = Repeat.class, name = "repeat"),
		@JsonSubTypes.Type(value = Shuffle.class, name = "shuffle"),
		@JsonSubTypes.Type(value = Forward.class, name = "forward"),
		@JsonSubTypes.Type(value = Backward.class, name = "backward"),
		@JsonSubTypes.Type(value = Like.class, name = "like"),
		@JsonSubTypes.Type(value = Next.class, name = "next"),
		@JsonSubTypes.Type(value = Prev.class, name = "prev"),
		@JsonSubTypes.Type(value = AddRemoveInPlaylist.class, name = "addRemoveInPlaylist"),
		@JsonSubTypes.Type(value = Status.class, name = "status"),
		@JsonSubTypes.Type(value = ShowPreferredSongs.class, name = "showPreferredSongs"),
		@JsonSubTypes.Type(value = GetTop5Songs.class, name = "getTop5Songs"),
		@JsonSubTypes.Type(value = GetTop5Playlists.class, name = "getTop5Playlists")
})

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
//@Builder
public abstract class Command {
	private String username;
	private Integer timestamp;

	@Override
	public String toString() {
		return "Command{" +
				"username='" + username + '\'' +
				", timestamp=" + timestamp +
				'}';
	}

	public abstract void execute(ArrayNode outputs, MainPlayer player);
}

// ChatGPT suggested using Command pattern or Visitor pattern
// I chose using Command pattern because it's easier to implement for this reqirements

//In the Command Pattern, you encapsulate a request as an object, thereby
//allowing parameterization of clients with different requests. Each command
//class can have an execute() method that contains the logic for that command.