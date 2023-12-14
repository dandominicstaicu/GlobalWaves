package app.common;

public final class Output {
    private Output() {
    }

    public static final String COMMAND = "command";
    public static final String USER = "user";
    public static final String TIMESTAMP = "timestamp";
    public static final String LOAD_REPEAT_ERR = "Please load a source before setting the "
            + "repeat status.";
    public static final String MESSAGE = "message";
    public static final String REPEAT = "repeat";
    public static final String REPEAT_CURRENT = "Repeat mode changed to repeat current song.";
    public static final String CHANGE_REPEAT_ALL = "Repeat mode changed to repeat all.";
    public static final String CHANGE_REPEAT_ONCE = "Repeat mode changed to repeat once.";
    public static final String CHANGE_NO_REPEAT = "Repeat mode changed to no repeat.";
    public static final String CHANGE_REPEAT_INFINITE = "Repeat mode changed to repeat infinite.";
    public static final String LIKE = "like";
    public static final String LIKED = "Like registered successfully.";
    public static final String UNLIKED = "Unlike registered successfully.";
    public static final String LOAD_LIKE_ERR = "Please load a source before liking or unliking.";
    public static final String LOAD_NEXT_ERR = "Please load a source before skipping to the "
            + "next track.";
    public static final String NEXT = "next";
    public static final String NEXT_SUCCESS = "Skipped to next track successfully. "
            + "The current track is ";
    public static final String LOAD_NOT_SONG_ERR = "The loaded source is not a song.";
    public static final String FORWARD = "forward";
    public static final String ADD_REMOVE_IN_PLAYLIST = "addRemoveInPlaylist";
    public static final String FORWARD_SUCCESS = "Skipped forward successfully.";
    public static final String SOURCE_NOT_PODCAST = "The loaded source is not a podcast.";
    public static final String LOAD_FWD_ERR = "Please load a source before attempting to forward.";
    public static final String ADDED_TO_PLAYLIST = "Successfully added to playlist.";
    public static final String REMOVED_FROM_PLAYLIST = "Successfully removed from playlist.";
    public static final String PLAYLIST_NOT_EXIST = "The specified playlist does not exist.";
    public static final String LOAD_ADD_RMV_ERR = "Please load a source before adding to or "
            + "removing from the playlist.";
    public static final String SHUFFLE = "shuffle";
    public static final String SHUFFLE_ACTIVE = "Shuffle function activated successfully.";
    public static final String SHUFFLE_DEACTIVATE = "Shuffle function deactivated successfully.";
    public static final String SOURCE_NOT_PLAYLIST = "The loaded source is not a playlist or "
            + "an album.";
    public static final String LOAD_SHUFFLE_ERR = "Please load a source before using the shuffle"
            + " function.";
    public static final String BACKWARD = "backward";
    public static final String BACKWARD_SUCCESS = "Rewound successfully.";
    public static final String LOAD_BACKWARD_ERR = "Please select a source before rewinding.";
    public static final String PREV = "prev";
    public static final String PREV_SUCCESS = "Returned to previous track successfully. "
            + "The current track is ";
    public static final String LOAD_PREV_ERR = "Please load a source before returning to the "
            + "previous track.";
    public static final String PLAY_PAUSE = "playPause";
    public static final String LOAD_PLAY_PAUSE_ERR = "Please load a source before attempting to "
            + "pause or resume playback.";
    public static final String RESUME = "Playback resumed successfully.";
    public static final String PAUSE = "Playback paused successfully.";
    public static final String SWITCH_VISIBILITY = "switchVisibility";
    public static final String PLAYLIST_ID_IS_TOO_HIGH = "The specified playlist ID is too high.";
    public static final String VISIBILITY_CHANGED = "Visibility status updated successfully to ";
    public static final String PUBLIC = "public";
    public static final String PRIVATE = "private";
    public static final String FOLLOW = "follow";
    public static final String FOLLOW_SUCCESS = "Playlist followed successfully.";
    public static final String UNFOLLOW_SUCCESS = "Playlist unfollowed successfully.";
    public static final String SELECTED_NOT_PLAYLIST = "The selected source is not a playlist.";
    public static final String OWN_PLAYLIST_ERR = "You cannot follow or unfollow your own "
            + "playlist.";
    public static final String LOAD_FOLLOW_ERR = "Please select a source before following or "
            + "unfollowing.";
    public static final String CREATE_PLAYLIST = "createPlaylist";
    public static final String PLAYLIST_CREATED = "Playlist created successfully.";
    public static final String PLAYLIST_EXISTS = "A playlist with the same name already exists.";
    public static final String SHOW_PLAYLISTS = "showPlaylists";
    public static final String RESULT = "result";
    public static final String NAME = "name";
    public static final String SONGS = "songs";
    public static final String VISIBILITY = "visibility";
    public static final String FOLLOWERS = "followers";
    public static final String RESULTS = "results";
    public static final String SEARCH = "search";
    public static final String SELECT = "select";
    public static final String SELECTED_ID_HIGH = "The selected ID is too high.";
    public static final String NO_SEARCH = "Please conduct a search before making a selection.";
    public static final String PREFERRED_SONGS = "showPreferredSongs";
    public static final String TOP_5_SONGS = "getTop5Songs";
    public static final String TOP_5_PLAYLISTS = "getTop5Playlists";
    public static final String STATUS = "status";
    public static final String REMAINED_TIME = "remainedTime";
    public static final String PAUSED = "paused";
    public static final String NO_REPEAT = "No Repeat";
    public static final String REPEAT_ONCE = "Repeat Once";
    public static final String REPEAT_ALL = "Repeat All";
    public static final String REPEAT_CURRENT_SONG = "Repeat Current Song";
    public static final String REPEAT_INFINITE = "Repeat Infinite";
    public static final String LOAD = "load";
    public static final String LOAD_SUCCESS = "Playback loaded successfully.";
    public static final String EMPTY_COLLECTION = "You can't load an empty audio collection!";
    public static final String NO_SELECT = "Please select a source before attempting to load.";
    public static final String SWITCH_CONNECTION_STATUS = "switchConnectionStatus";
    public static final String NOT_NORMAL_USER_ERR = " is not a normal user.";
    public static final String ONLINE_USERS = "getOnlineUsers";
    public static final String CONNECTION_STATUS_CHANGED = " has changed status successfully.";
    public static final String IS_OFFLINE = " is offline.";
    public static final String ADD_USER = "addUser";
    public static final String USER_ALREADY_TAKEN = " is already taken.";
    public static final String USER_ADDED = " has been added successfully.";
    public static final String ADD_ALBUM = "addAlbum";
    public static final String NOT_ARTIST = " is not an artist.";
    public static final String SAME_NAME_ALBUM = " has another album with the same name.";
    public static final String DUPLICATE_SONG_NAMES = " has the same song at least twice in "
            + "this album.";
    public static final String NEW_ALBUM_ADD = " has added new album successfully.";
    public static final String SHOW_ALBUMS = "showAlbums";
    public static final String PRINT_CURRENT_PAGE = "printCurrentPage";
    public static final String THE_USERNAME = "The username ";
    public static final String ADD_EVENT = "addEvent";
    public static final String DOESNT_EXIST = " doesn't exist.";
    public static final String EVENT_ALREADY_EXISTS = " has another event with the same name";
    public static final String INVALID_DATE = " does not have a valid date.";
    public static final String ADD_MERCH = "addMerch";
    public static final String MERCH_ALREADY_EXISTS = " has merchandise with the same name.";
    public static final String NEGATIVE_PRICE_MERCH = "Price for merchandise can not be negative.";
    public static final String EVENT_ADD_SUCCESS = " has added new event successfully.";
    public static final String MERCH_ADD_SUCCESS = " has added new merchandise successfully.";
    public static final String GET_ALL_USERS = "getAllUsers";
    public static final String DELETE_USER = "deleteUser";
    public static final String DELETE_USER_SUCCESS = " was successfully deleted.";
    public static final String DELETE_USER_FAIL = " can't be deleted.";
    public static final String ADD_PODCAST = "addPodcast";
    public static final String NOT_HOST = " is not a host.";
    public static final String SAME_NAME_PODCAST = " has another podcast with the same name.";
    public static final String DUPLICATE_EPISODE = " has the same episode in this podcast.";
    public static final String NEW_PODCAST_ADD = " has added new podcast successfully.";
    public static final String SAME_NAME_ANNOUNCE = " has already added an announcement with this"
            + "name.";
    public static final String REMOVE_ANNOUNCE = "removeAnnouncement";
    public static final String ANNOUNCE_NAME_ERR = " has no announcement with the given name.";
    public static final String SHOW_PODCASTS = "showPodcasts";
    public static final String EPISODES = "episodes";
    public static final String ADD_ANNOUNCE = "addAnnouncement";
    public static final String ANNOUNCE_ADD_SUCCESS = " has successfully added new announcement.";
    public static final String REMOVE_ANNOUNCE_SUCCESS = " has successfully deleted the "
            + "announcement.";
    public static final String REMOVE_ALBUM = "removeAlbum";
    public static final String NO_ALBUM_WITH_NAME = " doesn't have an album with the given name.";
    public static final String DELETE_ALBUM_SUCCESS = " deleted the album successfully.";
    public static final String DELETE_ALBUM_FAIL = " can't delete this album.";
    public static final String CHANGE_PAGE = "changePage";
    public static final String REMOVE_PODCAST = "removePodcast";
    public static final String NO_PODCAST_WITH_NAME = " doesn't have a podcast with the given"
            + " name.";
    public static final String PODCAST_DELETE_FAIL = " can't delete this podcast.";
    public static final String DELETE_PODCAST_SUCCESS = " deleted the podcast successfully.";
    public static final String REMOVE_EVENT = "removeEvent";
    public static final String EVENT_NAME_ERR = " doesn't have an event with the given name.";
    public static final String REMOVE_EVENT_SUCCESS = " deleted the event successfully.";
    public static final String TOP_5_ALBUMS = "getTop5Albums";
    public static final String TOP_5_ARTISTS = "getTop5Artists";
    public static final String NON_EXISTENT_PAGE = " is trying to access a non-existent page.";
}
