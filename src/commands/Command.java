package commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.admin.AddUser;
import commands.admin.DeleteUser;
import commands.admin.ShowAlbums;
import commands.admin.ShowPodcasts;
import commands.artist.*;
import commands.general.stats.*;
import commands.host.AddAnnouncement;
import commands.host.AddPodcast;
import commands.host.RemoveAnnouncement;
import commands.host.RemovePodcast;
import commands.normal.user.SwitchConnectionStatus;
import commands.normal.user.pages.ChangePage;
import commands.normal.user.pages.PrintCurrentPage;
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
import common.Constants;
import common.Output;
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
        @JsonSubTypes.Type(value = GetTop5Playlists.class, name = "getTop5Playlists"),
        @JsonSubTypes.Type(value = ChangePage.class, name = "changePage"),
        @JsonSubTypes.Type(value = PrintCurrentPage.class, name = "printCurrentPage"),
        @JsonSubTypes.Type(value = AddUser.class, name = "addUser"),
        @JsonSubTypes.Type(value = DeleteUser.class, name = "deleteUser"),
        @JsonSubTypes.Type(value = ShowAlbums.class, name = "showAlbums"),
        @JsonSubTypes.Type(value = ShowPodcasts.class, name = "showPodcasts"),
        @JsonSubTypes.Type(value = AddAlbum.class, name = "addAlbum"),
        @JsonSubTypes.Type(value = RemoveAlbum.class, name = "removeAlbum"),
        @JsonSubTypes.Type(value = AddEvent.class, name = "addEvent"),
        @JsonSubTypes.Type(value = RemoveEvent.class, name = "removeEvent"),
        @JsonSubTypes.Type(value = AddMerch.class, name = "addMerch"),
        @JsonSubTypes.Type(value = AddPodcast.class, name = "addPodcast"),
        @JsonSubTypes.Type(value = RemovePodcast.class, name = "removePodcast"),
        @JsonSubTypes.Type(value = AddAnnouncement.class, name = "addAnnouncement"),
        @JsonSubTypes.Type(value = RemoveAnnouncement.class, name = "removeAnnouncement"),
        @JsonSubTypes.Type(value = SwitchConnectionStatus.class, name = "switchConnectionStatus"),
        @JsonSubTypes.Type(value = GetTop5Albums.class, name = "getTop5Albums"),
        @JsonSubTypes.Type(value = GetTop5Artists.class, name = "getTop5Artists"),
        @JsonSubTypes.Type(value = GetAllUsers.class, name = "getAllUsers"),
        @JsonSubTypes.Type(value = GetOnlineUsers.class, name = "getOnlineUsers"),

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
     * @param outputs The ArrayNode to which command outputs are added.
     * @param library The library on which the command operates.
     */
    public abstract void execute(ArrayNode outputs, Library library, boolean offline);

    public void userIsOffline(final ObjectNode out) {
        out.put(Output.MESSAGE, getUsername() + Output.IS_OFFLINE);
    }

    public void printCommandInfo(final ObjectNode out, final String command) {
        out.put(Output.COMMAND, command);

        if (getUsername() != null) {
            out.put(Output.USER, getUsername());
        }

        out.put(Output.TIMESTAMP, getTimestamp());
    }
}

