package entities.user.side.pages;

import com.fasterxml.jackson.databind.node.ObjectNode;
import common.Output;
import common.UserTypes;
import entities.Library;
import entities.playable.Album;
import entities.playable.Playable;
import entities.playable.Playlist;
import entities.playable.audio_files.Song;
import entities.user.side.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
public class ArtistPage extends User implements Page, Playable {
    ArrayList<Event> events;
    ArrayList<Merch> merchList;

    public ArtistPage(final String username, final int age, final String city) {
        super(username, age, city, UserTypes.ARTIST);
        this.events = new ArrayList<>();
        this.merchList = new ArrayList<>();
    }

    public void addEvent(final Event event) {
        events.add(event);
    }

    public void addMerch(final Merch merch) {
        merchList.add(merch);
    }

    @Override
    public String printPage(final Library lib, NormalUser user) {
        StringBuilder pageContent = new StringBuilder();

        // Formatting the albums list
        pageContent.append("Albums:\n\t");
        String albumNames = getAlbumNames(lib); // Method to get album names as a formatted string
        if (!albumNames.isEmpty()) {
            pageContent.append("[").append(albumNames).append("]");
        } else {
            pageContent.append("[]");
        }

        pageContent.append("\n\n");

        // Formatting the merch list
        pageContent.append("Merch:\n\t");
        String merchInfo = getMerchInfo(); // Method to get merch info as a formatted string
        if (!merchInfo.isEmpty()) {
            pageContent.append("[").append(merchInfo).append("]");
        } else {
            pageContent.append("[]");
        }

        pageContent.append("\n\n");

        // Formatting the events list
        pageContent.append("Events:\n\t");
        String eventInfo = getEventInfo(); // Method to get event info as a formatted string
        if (!eventInfo.isEmpty()) {
            pageContent.append("[").append(eventInfo).append("]");
        } else {
            pageContent.append("[]");
        }

        return pageContent.toString();
    }

    private String getAlbumNames(final Library lib) {
        // Assuming you have a way to get the list of albums
        // Replace this with your actual implementation
        return lib.getArtistsAlbums(this.getName()).stream()
                .map(Album::getName)
                .collect(Collectors.joining(", "));
    }

    private String getMerchInfo() {
        // Assuming Merch has getName(), getPrice(), and getDescription() methods
        return merchList.stream()
                .map(merch -> merch.getName() + " - " + merch.getPrice() + ":\n\t" + merch.getDescription())
                .collect(Collectors.joining(", "));
    }

    private String getEventInfo() {
        // Assuming Event has getName(), getDate(), and getDescription() methods
        return events.stream()
                .map(event -> event.getName() + " - " + event.getDate() + ":\n\t" + event.getDescription())
                .collect(Collectors.joining(", "));
    }


    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String getName() {
        return getUsername();
    }

    @Override
    public void loadToQueue(UserPlayer userPlayer) {

    }

    @Override
    public boolean isPlaylist() {
        return false;
    }

    @Override
    public String toString() {
        return "ArtistPage{"
                + super.getUsername()
                + "}";
    }

    @Override
    public void addUser(Library library) {
        library.getArtists().add(this);
    }

    @Override
    public boolean handleDeletion(Library library) {
        for (NormalUser user : library.getUsers()) {
            // if this page is used by a user at deletion time, it has to fail
            if (user.getCurrentPage().equals(this)) {
                return false;
            }

            if (user.getPlayer().getLoadedContentReference() != null) {
                if (user.getPlayer().getLoadedContentReference().isLoadedInPlayer(this.getUsername())) {
                    if (user.getPlayer().getIsPlaying()) {
                        return false;
                    }
                }
            }

            List<Playable> lastSearchResults = user.getPlayer().getSearchBar().getLastSearchResults();
            if (lastSearchResults != null) {
                for (Playable playable : lastSearchResults) {
                    if (playable.ownedByUser(this.getUsername())) {
                        return false;
                    }
                }
            }
        }

        library.getArtists().remove(this);

        List<Album> artistsAlbums = library.getArtistsAlbums(this.getUsername());

        List<Song> removeSongs = new ArrayList<>();

        for (Album album : artistsAlbums) {
            removeSongs.addAll(album.getSongs());
            for (Song song : library.getSongs()) {
                for (NormalUser user : library.getUsers()) {
                    if (user.getFavoriteSongs().contains(song)) {
                        user.likeUnlikeSong(song);
                    }
                }
            }
        }

        library.getSongs().removeAll(removeSongs);

        library.getAlbums().removeAll(artistsAlbums);

        return true;
    }

    @Override
    public void handleSelect(final SearchBar searchBar, final NormalUser user, final ObjectNode out) {
        searchBar.setSelectedResult(null);
        searchBar.setLastSearchResults(null);

        out.put(Output.MESSAGE, "Successfully selected " + getUsername() + "'s page.");
        user.setCurrentPage(this);
    }

    public boolean hasEventWithName(final String eventName) {
        return events.stream()
                .anyMatch(event -> event.getName().equals(eventName));
    }

    public Event getEventWithName(final String name) {
        for (Event event : events) {
            if (event.getName().equals(name)) {
                return event;
            }
        }

        return null;
    }

    @Override
    public boolean ownedByUser(final String artistName) {
        return this.getName().equals(artistName);
    }

    public void removeEvent(final Event event) {
        events.remove(event);
    }
}
