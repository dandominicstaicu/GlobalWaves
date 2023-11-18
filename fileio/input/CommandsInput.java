package fileio.input;

import lombok.Getter;
import lombok.Setter;

import commands.searchbar.*;
import commands.player.*;
import commands.playlist.*;
import commands.stats.*;

import java.util.ArrayList;

@Getter
@Setter
public class CommandsInput {
    private ArrayList<Search> searches;
    private ArrayList<Select> selects;
    private ArrayList<Load> loads;
    private ArrayList<PlayPause> playPauses;
    private ArrayList<Repeat> repeats;
    private ArrayList<Shuffle> shuffles;
    private ArrayList<Forward> forwards;
    private ArrayList<Backward> backwards;
    private ArrayList<Like> likes;
    private ArrayList<Next> nexts;
    private ArrayList<Prev> prevs;
    private ArrayList<AddRemoveInPlaylist> AddRemoveInPlaylists;
    private ArrayList<Status> statuses;
    private ArrayList<CreatePlaylist> createPlaylists;
    private ArrayList<SwitchVisibility> switchVisibilities;
    private ArrayList<FollowPlaylist> followPlaylists;
    private ArrayList<ShowPlaylist> showPlaylists;
    private ArrayList<ShowPreferredSongs> showPreferredSongs;
    private ArrayList<GetTop5Songs> getTop5Songs;
    private ArrayList<GetTop5Playlists> getTop5Playlists;

    public CommandsInput() {
    }
}
