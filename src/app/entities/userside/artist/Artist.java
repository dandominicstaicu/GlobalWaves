package app.entities.userside.artist;

import app.commands.specialusers.artist.Monetization;
import app.entities.Library;
import app.entities.playable.Album;
import app.entities.playable.Searchable;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.normaluser.SearchBar;
import app.entities.userside.User;
import app.entities.userside.normaluser.WrappedStats;
import app.entities.userside.pages.ArtistPage;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.common.Output;
import app.common.UserTypes;
import app.entities.playable.audio_files.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
public class Artist extends User implements Searchable {
    private ArrayList<Event> events;
    private ArrayList<Merch> merchList;
    private ArtistPage artistPage;

    private Integer id;

    private WrappedStats wrappedStats;

    private Monetization monetization;

    /**
     * Constructs a new Artist with the specified username, age, and city.
     * Initializes empty lists for events and merchandise, as well as an associated ArtistPage.
     *
     * @param username The username of the artist.
     * @param age      The age of the artist.
     * @param city     The city where the artist is based.
     */
    public Artist(final String username, final int age, final String city) {
        super(username, age, city, UserTypes.ARTIST);
        this.events = new ArrayList<>();
        this.merchList = new ArrayList<>();
        this.artistPage = new ArtistPage(this);

        this.wrappedStats = new WrappedStats(this);
        this.monetization = new Monetization(this);
    }

    /**
     * Adds an event to the artist's list of events.
     *
     * @param event The event to be added.
     */
    public void addEvent(final Event event) {
        events.add(event);
    }

    /**
     * Adds a merchandise item to the artist's list of merchandise.
     *
     * @param merch The merchandise item to be added.
     */
    public void addMerch(final Merch merch) {
        merchList.add(merch);
    }

    /**
     * Checks if the artist is considered empty. In this context, an artist is never empty.
     *
     * @return false always, as an artist is never considered empty.
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Retrieves the artist's username.
     *
     * @return The username of the artist.
     */
    @Override
    public String getName() {
        return getUsername();
    }

    /**
     * Checks if the artist is considered a playlist. In this context, an artist is
     * not a playlist.
     *
     * @return false always, as an artist is not a playlist.
     */

    @Override
    public boolean isPlaylist() {
        return false;
    }

    /**
     * Provides a string representation of the Artist object.
     *
     * @return A string representation of the artist.
     */
    @Override
    public String toString() {
        return "ArtistPage{"
                + super.getUsername()
                + "}";
    }

    /**
     * Adds this artist to the specified library's list of artists.
     *
     * @param library The library where the artist will be added.
     */
    @Override
    public void addUser(final Library library) {
        library.getArtists().add(this);
        this.id = library.getArtists().size() - 1;
    }

    /**
     * Handles the deletion process of the artist from the library.
     * Ensures the artist is not currently being used or referenced before allowing deletion.
     *
     * @param library The library from which the artist will be deleted.
     * @return true if the artist can be safely deleted, false if the artist is currently in use.
     */
    @Override
    public boolean handleDeletion(final Library library) {
        for (NormalUser user : library.getUsers()) {
            // if this page is used by a user at deletion time, it has to fail
            if (user.getCurrentPage().equals(this.getArtistPage())) {
                return false;
            }

            if (user.getPlayer().getLoadedContentReference() != null) {
                if (user.getPlayer().getLoadedContentReference()
                        .isLoadedInPlayer(this.getUsername())) {
                    if (user.getPlayer().getIsPlaying()) {
                        return false;
                    }
                }
            }

            List<Searchable> lastSearchResults = user.getPlayer().getSearchBar()
                    .getLastSearchResults();
            if (lastSearchResults != null && lastSearchResults.stream()
                    .anyMatch(playable -> playable.ownedByUser(this.getUsername()))) {
                return false;
            }
        }

        library.getArtists().remove(this);

        List<Album> artistsAlbums = library.getArtistsAlbums(this.getUsername());
        List<Song> removeSongs = new ArrayList<>();

        artistsAlbums.stream()
                .flatMap(album -> album.getSongs().stream())
                .peek(removeSongs::add)
                .forEach(song -> library.getUsers().stream()
                        .filter(user -> user.getFavoriteSongs().contains(song))
                        .forEach(user -> user.likeUnlikeSong(song)));

        library.getSongs().removeAll(removeSongs);
        library.getAlbums().removeAll(artistsAlbums);

        return true;
    }

    @Override
    public void printWrappedStats(ObjectNode out) {
        ObjectNode result = out.putObject(Output.RESULT);

        List<Map.Entry<String, Integer>> topAlbums = wrappedStats.top5Albums();
        ObjectNode albumsNode = result.putObject("topAlbums");
        for (Map.Entry<String, Integer> entry : topAlbums) {
            albumsNode.put(entry.getKey(), entry.getValue());
        }

        List<Map.Entry<String, Integer>> topSongs = wrappedStats.top5Songs();
        ObjectNode songsNode = result.putObject("topSongs");
        for (Map.Entry<String, Integer> entry : topSongs) {
            songsNode.put(entry.getKey(), entry.getValue());
        }

        List<Map.Entry<String, Integer>> topFans = wrappedStats.top5Fans();
        ArrayNode fansNode = result.putArray("topFans");
        for (Map.Entry<String, Integer> entry : topFans) {
            fansNode.add(entry.getKey());
        }

        int listenersCount = wrappedStats.getListenersCountSize();
        result.put("listeners", listenersCount);
    }

    /**
     * Handles the selection process of the artist. Sets the artist's page as the current page
     * for the user.
     *
     * @param searchBar The SearchBar used for the operation.
     * @param user      The user who is selecting the artist.
     * @param out       The output node where the result message is set.
     */
    @Override
    public void handleSelect(final SearchBar searchBar, final NormalUser user,
                             final ObjectNode out) {
        searchBar.setSelectedResult(null);
        searchBar.setLastSearchResults(null);

        out.put(Output.MESSAGE, "Successfully selected " + getUsername() + "'s page.");
        user.setCurrentPage(this.getArtistPage());
    }

    /**
     * Checks if the artist has an event with the specified name.
     *
     * @param eventName The name of the event to search for.
     * @return true if an event with the given name exists, false otherwise.
     */
    public boolean hasEventWithName(final String eventName) {
        return events.stream()
                .anyMatch(event -> event.getName().equals(eventName));
    }

    /**
     * Retrieves an event with the specified name from the artist's list of events.
     *
     * @param name The name of the event to retrieve.
     * @return The event if found, or null if no event with the given name exists.
     */
    public Event getEventWithName(final String name) {
        return events.stream()
                .filter(event -> event.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if the artist's name matches the specified artist name.
     *
     * @param artistName The name of the artist to compare with.
     * @return true if the names match, false otherwise.
     */
    @Override
    public boolean ownedByUser(final String artistName) {
        return this.getName().equals(artistName);
    }

    /**
     * Removes the specified event from the artist's list of events.
     *
     * @param event The event to be removed.
     */

    public void removeEvent(final Event event) {
        events.remove(event);
    }
}
