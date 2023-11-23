package commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.player.AddRemoveInPlaylist;
import commands.player.Backward;
import commands.player.Forward;
import commands.player.Like;
import commands.player.Load;
import commands.player.Next;
import commands.player.PlayPause;
import commands.player.Prev;
import commands.player.Repeat;
import commands.player.Shuffle;
import commands.player.Status;
import commands.playlist.CreatePlaylist;
import commands.playlist.FollowPlaylist;
import commands.playlist.ShowPlaylists;
import commands.playlist.SwitchVisibility;
import commands.searchbar.Search;
import commands.searchbar.Select;
import commands.stats.GetTop5Playlists;
import commands.stats.GetTop5Songs;
import commands.stats.ShowPreferredSongs;
import entities.Library;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "command")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Search.class, name = "search"),
        @JsonSubTypes.Type(value = Select.class, name = "select"),
        @JsonSubTypes.Type(value = CreatePlaylist.class, name = "createPlaylist"),
        @JsonSubTypes.Type(value = FollowPlaylist.class, name = "follow"),
        @JsonSubTypes.Type(value = ShowPlaylists.class, name = "showPlaylists"),
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
public abstract class Command {
    private String username;
    private Integer timestamp;

    /**
     * Returns a string representation of the command including its username and timestamp.
     * Used for debugging.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return "Command{" + "username='" + username + '\'' + ", timestamp=" + timestamp + '}';
    }

    /**
     * Executes the command and updates the outputs and library as needed.
     * Part of the Command design pattern
     *
     * @param outputs  The ArrayNode to which command outputs are added.
     * @param library  The library on which the command operates.
     */
    public abstract void execute(ArrayNode outputs, Library library);
}

