package app.entities.userside.normaluser;

import app.commands.normaluser.Notification;
import app.commands.specialusers.artist.Monetization;
import app.common.Constants;
import app.common.Output;
import app.common.UpdateRecommend;
import app.common.UserTypes;
import app.entities.Library;
import app.entities.playable.Playlist;
import app.entities.playable.Searchable;
import app.entities.playable.audio_files.AudioFile;
import app.entities.playable.audio_files.Song;
import app.entities.userside.User;
import app.entities.userside.artist.Artist;
import app.entities.userside.artist.Merch;
import app.entities.userside.pages.HomePage;
import app.entities.userside.pages.Page;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
@Setter
public class NormalUser extends User {
    private UserPlayer player;
    private ArrayList<Song> favoriteSongs;
    private ArrayList<Playlist> followedPlaylists;
    private boolean online;
    private Page currentPage;

    private WrappedStats wrappedStats;

    private HashMap<String, ArrayList<Song>> regularHistory;
    private HashMap<String, ArrayList<Song>> premiumHistory;

    private Boolean isPremium;
    private ArrayList<Notification> notifications;
    private ArrayList<Merch> boughtMerch;

    private ArrayList<Song> songRecommendations;
    private Playlist playlistsRecommendations;
    private Searchable lastRecommendation;

    private ArrayList<Page> pageHistory;
    private int historyIndex = 0;

    public NormalUser(final String username, final int age, final String city) {
        super(username, age, city, UserTypes.NORMAL_USER);
        this.player = new UserPlayer();
        this.favoriteSongs = new ArrayList<>();
        this.followedPlaylists = new ArrayList<>();
        this.online = true;
        this.currentPage = new HomePage();
        this.wrappedStats = new WrappedStats(this);
        this.isPremium = false;

        this.premiumHistory = new HashMap<>();
        this.regularHistory = new HashMap<>();

        this.notifications = new ArrayList<>();
        this.boughtMerch = new ArrayList<>();

        this.pageHistory = new ArrayList<>();

//        this.playlistsRecommendations = new ArrayList<>();
        this.playlistsRecommendations = null;
        this.songRecommendations = new ArrayList<>();
    }

    /**
     * Toggles the 'like' status of a given song. If the song is already liked (present in
     * favoriteSongs) it is unliked (removed from favoriteSongs) and its like count is
     * decremented. If the song is not liked, it is added to favoriteSongs
     * and its like count is incremented.
     *
     * @param song The song to like or unlike.
     * @return true if the song was liked (added to favoriteSongs) as a result of this operation,
     * false if the song was unliked (removed from favoriteSongs).
     */
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

    /**
     * Toggles the 'follow' status of a given playlist. If the playlist is already followed
     * (present in followedPlaylists), t is unfollowed (removed from followedPlaylists) and its
     * i follower count is decremented. If the playlist is not followed, it is added to
     * followedPlaylists and its follower count is incremented.
     *
     * @param playlist The playlist to follow or unfollow.
     * @return true if the playlist was followed (added to followedPlaylists) as a result of this
     * operation, false if the playlist was unfollowed (removed from followedPlaylists).
     */
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

    /**
     * Retrieves a list of playlists owned by the user. It filters through all provided playlists
     * and returns only those where the owner matches the current user's username.
     *
     * @param allPlaylists A list of all playlists to filter through.
     * @return A list of {@link Playlist} objects that are owned by the user.
     */
    public List<Playlist> getPlaylistsOwnedByUser(final List<Playlist> allPlaylists) {
        List<Playlist> userSeenPlaylists = new ArrayList<>();

        for (Playlist playlist : allPlaylists) {
            if (playlist.getOwner().equals(getUsername())) {
                userSeenPlaylists.add(playlist);
            }
        }

        return userSeenPlaylists;
    }

    /**
     * Switches the online status of the user.
     *
     * @param currentTimestamp The current timestamp for updating pause start time.
     */
    public void switchConnectionStatus(final int currentTimestamp) {
        UserPlayer userPlayer = this.getPlayer();
        if (this.online) {
            userPlayer.setPauseStartTimeStamp(currentTimestamp);
            userPlayer.setIsOffline(true);
        } else {
            userPlayer.setPauseStartTimeStamp(0);
            userPlayer.setIsOffline(false);

        }

        this.online = !this.online;
    }

    /**
     * Gets the online status of the user.
     *
     * @return true if the user is online, false if the user is offline.
     */
    public boolean getOnline() {
        return this.online;
    }

    /**
     * Adds the user to the specified library. The implementation of this method depends on the
     * specific user type (e.g., NormalUser, Artist, Host) and their interactions with the library.
     *
     * @param library The library to which the user is added.
     */
    @Override
    public void addUser(final Library library) {
        library.getUsers().add(this);
    }

    /**
     * Handles the deletion of the user's account from the library. Checks for user interactions
     * with the user's content before deletion.
     *
     * @param library The library from which the user's account is deleted.
     * @return true if the user's account was successfully deleted, false otherwise.
     */
    @Override
    public boolean handleDeletion(final Library library) {
        for (NormalUser user : library.getUsers()) {
            if (user.getPlayer().getLoadedContentReference() != null) {
                if (user.getPlayer().getLoadedContentReference()
                        .isLoadedInPlayer(this.getUsername())) {
                    if (user.getPlayer().getIsPlaying()) {
                        return false;
                    }
                }
            }
        }

        List<Playlist> libPlaytlists = library.getPlaylists();
        List<Playlist> rmvPlaylists = new ArrayList<>();
        for (Playlist playlist : libPlaytlists) {
            if (playlist.getOwner().equals(this.getUsername())) {
                rmvPlaylists.add(playlist);
            }
        }

        for (Playlist followedPlaylist : followedPlaylists) {
            followedPlaylist.setFollowers(followedPlaylist.getFollowers() - 1);
        }

        library.getPlaylists().removeAll(rmvPlaylists);

        List<NormalUser> libUsers = library.getUsers();

        for (NormalUser user : libUsers) {
            List<Playlist> followedPlaylistsByUser = user.getFollowedPlaylists();
            rmvPlaylists = new ArrayList<>();
            for (Playlist playlist : followedPlaylistsByUser) {
                if (playlist.getOwner().equals(this.getUsername())) {
                    rmvPlaylists.add(playlist);
                }
            }

            user.getFollowedPlaylists().removeAll(rmvPlaylists);
        }

        library.removeUser(this);

        return true;
    }

    /**
     * Prints wrapped statistics related to a user, including top artists, top genres,
     * top songs, top albums, and top episodes. If the wrapped statistics are not registered
     * for the user, an error message is included in the output.
     *
     * @param out The ObjectNode where the wrapped statistics will be printed.
     */
    @Override
    public void printWrappedStats(final ObjectNode out) {
        if (!wrappedStats.getRegisteredStats()) {
            out.put(Output.MESSAGE, Output.WRAPPED_ERR_USER + getUsername() + ".");
            return;
        }

        ObjectNode result = out.putObject(Output.RESULT);

        List<Map.Entry<String, Integer>> topArtists = wrappedStats.top5Artists();
        ObjectNode artistsNode = result.putObject("topArtists");
        for (Map.Entry<String, Integer> entry : topArtists) {
            artistsNode.put(entry.getKey(), entry.getValue());
        }

        List<Map.Entry<String, Integer>> topGenres = wrappedStats.top5Genres();
        ObjectNode genresNode = result.putObject("topGenres");
        for (Map.Entry<String, Integer> entry : topGenres) {
            genresNode.put(entry.getKey(), entry.getValue());
        }

        Artist.buildSongs(result, wrappedStats);

        Artist.buildAlbums(result, wrappedStats);

        buildEpisodes(result, wrappedStats);
    }

    /**
     * Builds and adds the top 5 episodes to the JSON result object.
     *
     * @param result       The JSON ObjectNode where the top episodes will be added.
     * @param wrappedStats The WrappedStats object containing the data to retrieve the top
     *                     episode from.
     */
    public static void buildEpisodes(final ObjectNode result, final WrappedStats wrappedStats) {
        List<Map.Entry<String, Integer>> topEpisodes = wrappedStats.top5Episodes();
        ObjectNode episodesNode = result.putObject("topEpisodes");
        for (Map.Entry<String, Integer> entry : topEpisodes) {
            episodesNode.put(entry.getKey(), entry.getValue());
        }
    }

    private void addValue(final HashMap<String, ArrayList<Song>> map, final String key,
                          final Song value) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    /**
     * Adds a song to the premium history, associating it with the artist.
     *
     * @param song The Song object to be added to the premium history.
     */
    public void addPremiumHistory(final Song song) {
        addValue(premiumHistory, song.getArtist(), song);
    }

    /**
     * Adds a song to the regular history, associating it with the artist.
     *
     * @param song The Song object to be added to the regular history.
     */
    public void addRegularHistory(final Song song) {
        addValue(regularHistory, song.getArtist(), song);
    }

    private Double getTotalNumberOfSongs(final HashMap<String, ArrayList<Song>> history) {
        Double totalSongs = 0.0;

        for (ArrayList<Song> songs : history.values()) {
            totalSongs += songs.size();
        }

        return totalSongs;
    }

    /**
     * Inserts an ad break into the player with a specified price and associates it with the
     * library.
     *
     * @param lib   The Library object associated with the ad break.
     * @param price The price of the ad break.
     */
    public void insertAd(final Library lib, final Double price) {
        player.insertAdBreak(lib, price);
    }

    /**
     * Pays premium artists based on their song history and clears the premium history.
     * If the premium history is empty, no payments are made.
     *
     * @param lib The Library object containing song data.
     */
    public void payPremiumArtist(final Library lib) {
        if (premiumHistory.isEmpty()) {
            return;
        }
        Double songTotal = getTotalNumberOfSongs(premiumHistory);
        Double revenuePerSong = Constants.PREMIUM_CREDIT / songTotal;
        processArtistPayments(lib, premiumHistory, revenuePerSong);
        premiumHistory.clear();
    }

    /**
     * Monetizes ad revenue for regular artists based on their song history and clears the regular
     * history.
     *
     * @param lib   The Library object containing song data.
     * @param price The total price of the ad.
     */
    public void monetizeAd(final Library lib, final Double price) {
        Double songLast = getTotalNumberOfSongs(regularHistory);
        Double revenuePerSong = price / songLast;
        processArtistPayments(lib, regularHistory, revenuePerSong);
        regularHistory.clear();
    }

    private void processArtistPayments(final Library lib,
                                       final Map<String, ArrayList<Song>> history,
                                       final Double revenuePerSong) {
        for (Map.Entry<String, ArrayList<Song>> entry : history.entrySet()) {
            String artistName = entry.getKey();
            ArrayList<Song> songs = entry.getValue();
            Integer count = songs.size();

            Artist artist = lib.getArtistWithName(artistName);
            if (artist == null) {
                System.out.println("this should not happen");
                return;
            }

            Monetization monetization = artist.getMonetization();
            Double revenue = revenuePerSong * count;

            for (Song song : songs) {
                monetization.addRevenuePerSong(song.getName(), revenuePerSong);
            }

            monetization.addSongRevenue(revenue);
        }
    }

    /**
     * Adds a notification to the user's notification list.
     *
     * @param notification The Notification object to be added.
     */
    public void addNotification(final Notification notification) {
        notifications.add(notification);
    }

    /**
     * Clears all notifications from the user's notification list.
     */
    public void clearNotifications() {
        notifications.clear();
    }

    /**
     * Records the purchase of merchandise by the user.
     *
     * @param merch The Merch object representing the merchandise being purchased.
     */
    public void buyMerch(final Merch merch) {
        boughtMerch.add(merch);
    }

    /**
     * Retrieves the names of all bought merch items using Java Streams.
     *
     * @return An ArrayList of Strings containing the names of all bought merch.
     */
    public ArrayList<String> getAllMerchNames() {
        return boughtMerch.stream()
                .map(Merch::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Updates user recommendations based on the provided update type.
     *
     * @param type The type of update to be applied.
     * @return true if the recommendations were successfully updated, false otherwise.
     */
    public boolean updateRecommendations(final UpdateRecommend type) {
        int elapsedTime = player.getPlayedTimeOfCurrentSong();

        switch (type) {
            case RANDOM_SONG -> {
                if (elapsedTime < Constants.TIME_LOWER_BOUND) {
                    return false;
                }

                addRandomSong();

                return true;
            }
            case RANDOM_PLAYLIST -> {
                createRandomPlaylist();

                return !playlistsRecommendations.isEmpty();
            }
            case FANS_PLAYLIST -> {
                createFansPlayList();

                return !playlistsRecommendations.getSongs().isEmpty();
            }
            default -> {
                System.out.println("unknown case");
            }
        }

        return false;
    }

    private void createFansPlayList() {
        AudioFile playing = player.getCurrentlyPlaying();
        String artistName = playing.getFileOwner();
        Library lib = Library.getInstance();
        Artist artist = lib.getArtistWithName(artistName);
        String playlistName = artistName + " Fan Club recommendations";
        Playlist fansPlaylist = new Playlist(playlistName);

        WrappedStats artistsStats = artist.getWrappedStats();
        List<Map.Entry<String, Integer>> topFans = artistsStats.top5Fans();
        for (Map.Entry<String, Integer> entry : topFans) {
            NormalUser fan = lib.getUserWithUsername(entry.getKey());
            WrappedStats fansStats = fan.getWrappedStats();

            List<Map.Entry<String, Integer>> topSongs = fansStats.top5Songs();
            for (Map.Entry<String, Integer> songEntry : topSongs) {
                Song song = lib.getSongWithName(songEntry.getKey());

                assert song != null;
                if (song.getLikes() > 0) {
                    fansPlaylist.addSong(song);
                }
            }
        }

        this.playlistsRecommendations = fansPlaylist;
        lastRecommendation = fansPlaylist;
    }

    private void createRandomPlaylist() {
        ArrayList<String> top3Genre = getTop3Genre();
        String playlistName = getUsername() + "'s recommendations";
        Playlist randomPlaylist = new Playlist(playlistName);

        if (!top3Genre.isEmpty()) {
            List<Song> genreSongs = topNSongs(top3Genre.get(0), Constants.MAX_LIST_RETURN);
            randomPlaylist.addMultipleSongs(genreSongs);

            if (top3Genre.size() > 1) {
                genreSongs.clear();
                genreSongs = topNSongs(top3Genre.get(1), Constants.SECOND_LIST_RETURN);
                randomPlaylist.addMultipleSongs(genreSongs);
            }

            if (top3Genre.size() > 2) {
                genreSongs.clear();
                genreSongs = topNSongs(top3Genre.get(2), Constants.THIRD_LIST_RETURN);
                randomPlaylist.addMultipleSongs(genreSongs);
            }
        }

        this.playlistsRecommendations = randomPlaylist;
        lastRecommendation = randomPlaylist;
    }

    private List<Song> topNSongs(final String genre, final int topSize) {
        Library lib = Library.getInstance();
        ArrayList<Song> songsWithGenre = lib.getSongsWithGenreSorted(genre);

        return songsWithGenre.stream()
                .limit(topSize)
                .collect(Collectors.toList());
    }

    private ArrayList<String> getTop3Genre() {
        HashMap<String, Integer> genreOccurrences = new HashMap<>();
        genreLikes(genreOccurrences);
        genreCreatedPlaylists(genreOccurrences);
        genreFollowedPlaylists(genreOccurrences);

        return genreOccurrences.entrySet().stream()
                // Sort by value (occurrence) in descending order
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                // Limit to top 3
                .limit(Constants.TOP3)
                // Map to get only the genre (key)
                .map(Map.Entry::getKey)
                // Collect to list
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void genreLikes(final HashMap<String, Integer> genreOccurrences) {
        for (Song song : favoriteSongs) {
            String genre = song.getGenre();
            Integer currentValue = genreOccurrences.getOrDefault(genre, 0);
            currentValue += 1;
            genreOccurrences.put(genre, currentValue);
        }
    }

    private void genreCreatedPlaylists(final HashMap<String, Integer> genreOccurrences) {
        Library lib = Library.getInstance();
        List<Playlist> playlists = lib.getPlaylists();

        for (Playlist libPlaylist : playlists) {
            String owner = libPlaylist.getOwner();
            if (owner.equals(this.getUsername())) {
                addToOccurrences(genreOccurrences, libPlaylist);
            }
        }
    }

    private void addToOccurrences(final HashMap<String, Integer> genreOccurrences,
                                  final Playlist libPlaylist) {
        List<Song> playlistsSongs = libPlaylist.getSongs();
        for (Song song : playlistsSongs) {
            String genre = song.getGenre();
            Integer currentValue = genreOccurrences.getOrDefault(genre, 0);
            currentValue += 1;
            genreOccurrences.put(genre, currentValue);
        }
    }

    private void genreFollowedPlaylists(final HashMap<String, Integer> genreOccurrences) {
        for (Playlist followed : followedPlaylists) {
            addToOccurrences(genreOccurrences, followed);
        }
    }

    private void addRandomSong() {
        AudioFile playing = player.getCurrentlyPlaying();
        String genre = playing.getSongGenre();

        Library lib = Library.getInstance();
        ArrayList<Song> sameGenreSong = lib.getSongsWithGenre(genre);
        int seed = player.getPlayedTimeOfCurrentSong();

        if (!sameGenreSong.isEmpty()) {
            Random random = new Random(seed);
            int randomIndex = random.nextInt(sameGenreSong.size());
            Song randomSong = sameGenreSong.get(randomIndex);

            songRecommendations.add(randomSong);
            lastRecommendation = randomSong;
        }

    }

    // chatGPT suggested me this function when asked how to do it
    private void removeAllFromIndex(final ArrayList<?> list, final int fromIndex) {
        if (fromIndex < list.size()) {
            list.subList(fromIndex, list.size()).clear();
        }
    }

    /**
     * Adds a page to the user's page history, removing all pages after the current index.
     *
     * @param page The Page object to be added to the history.
     */
    public void addInPageHistory(final Page page) {
        removeAllFromIndex(pageHistory, historyIndex + 1);

        pageHistory.add(page);
        historyIndex = pageHistory.size() - 1;
    }

    /**
     * Navigates to the previous page in the user's page history.
     * The current page index is decremented accordingly.
     */
    public void goToPrevPage() {
        historyIndex--;
        currentPage = pageHistory.get(historyIndex);
    }

    /**
     * Navigates to the next page in the user's page history.
     * The current page index is incremented accordingly.
     */
    public void goToNextPage() {
        historyIndex++;
        currentPage = pageHistory.get(historyIndex);
    }

}
