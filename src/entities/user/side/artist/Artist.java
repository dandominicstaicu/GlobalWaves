package entities.user.side.artist;

import com.fasterxml.jackson.databind.node.ObjectNode;
import common.Output;
import common.UserTypes;
import entities.Library;
import entities.playable.Album;
import entities.playable.Playable;
import entities.playable.audio_files.Song;
import entities.user.side.*;
import entities.user.side.pages.ArtistPage;
import entities.user.side.pages.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
public class Artist extends User implements Playable {
    ArrayList<Event> events;
    ArrayList<Merch> merchList;
    ArtistPage artistPage;

    public Artist(final String username, final int age, final String city) {
        super(username, age, city, UserTypes.ARTIST);
        this.events = new ArrayList<>();
        this.merchList = new ArrayList<>();
        this.artistPage = new ArtistPage(this);
    }

    public void addEvent(final Event event) {
        events.add(event);
    }

    public void addMerch(final Merch merch) {
        merchList.add(merch);
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
            if (user.getCurrentPage().equals(this.getArtistPage())) {
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
        user.setCurrentPage(this.getArtistPage());
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
