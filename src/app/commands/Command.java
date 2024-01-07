package app.commands;

import app.commands.admin.*;
import app.commands.generalstats.*;
import app.commands.normaluser.BuyMerch;
import app.commands.normaluser.GetNotifications;
import app.commands.normaluser.SeeMerch;
import app.commands.normaluser.pages.*;
import app.commands.normaluser.player.AddRemoveInPlaylist;
import app.commands.normaluser.player.Backward;
import app.commands.normaluser.player.Forward;
import app.commands.normaluser.player.Like;
import app.commands.normaluser.player.Load;
import app.commands.normaluser.player.Next;
import app.commands.normaluser.player.PlayPause;
import app.commands.normaluser.player.Prev;
import app.commands.normaluser.player.Repeat;
import app.commands.normaluser.player.Shuffle;
import app.commands.normaluser.player.Status;
import app.commands.premium.BuyPremium;
import app.commands.premium.CancelPremium;
import app.commands.specialusers.Subscribe;
import app.commands.specialusers.artist.AddAlbum;
import app.commands.specialusers.artist.AddEvent;
import app.commands.specialusers.artist.AddMerch;
import app.commands.specialusers.artist.RemoveAlbum;
import app.commands.specialusers.artist.RemoveEvent;
import app.entities.Library;
import app.commands.normaluser.SwitchConnectionStatus;
import app.commands.normaluser.playlist.CreatePlaylist;
import app.commands.normaluser.playlist.FollowPlaylist;
import app.commands.normaluser.playlist.ShowPlaylists;
import app.commands.normaluser.playlist.SwitchVisibility;
import app.commands.searchbar.Search;
import app.commands.searchbar.Select;
import app.commands.specialusers.host.AddAnnouncement;
import app.commands.specialusers.host.AddPodcast;
import app.commands.specialusers.host.RemoveAnnouncement;
import app.commands.specialusers.host.RemovePodcast;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.common.Output;
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
        @JsonSubTypes.Type(value = BuyPremium.class, name = "buyPremium"),
        @JsonSubTypes.Type(value = CancelPremium.class, name = "cancelPremium"),
        @JsonSubTypes.Type(value = Wrapped.class, name = "wrapped"),
        @JsonSubTypes.Type(value = UpdateRecommendations.class, name = "updateRecommendations"),
        @JsonSubTypes.Type(value = AdBreak.class, name = "adBreak"),
        @JsonSubTypes.Type(value = Subscribe.class, name = "subscribe"),
        @JsonSubTypes.Type(value = BuyMerch.class, name = "buyMerch"),
        @JsonSubTypes.Type(value = SeeMerch.class, name = "seeMerch"),
        @JsonSubTypes.Type(value = LoadRecommendations.class, name = "loadRecommendations"),
        @JsonSubTypes.Type(value = GetNotifications.class, name = "getNotifications"),
        @JsonSubTypes.Type(value = PreviousPage.class, name = "previousPage"),
        @JsonSubTypes.Type(value = NextPage.class, name = "nextPage"),
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

    /**
     * Indicates that the user associated with this command is offline.
     *
     * @param out The ObjectNode where the offline message should be added.
     */
    protected void userIsOffline(final ObjectNode out) {
        out.put(Output.MESSAGE, getUsername() + Output.IS_OFFLINE);
    }

    /**
     * Adds command information to the output node, including the command type, username and
     * timestamp.
     *
     * @param out     The ObjectNode where command information should be added.
     * @param command The type of command being executed.
     */
    public void printCommandInfo(final ObjectNode out, final String command) {
        out.put(Output.COMMAND, command);

        if (getUsername() != null) {
            out.put(Output.USER, getUsername());
        }

        out.put(Output.TIMESTAMP, getTimestamp());
    }
}

