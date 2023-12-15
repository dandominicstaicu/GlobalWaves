package app.entities.userside.pages;

import app.entities.Library;
import app.entities.playable.Album;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.artist.Artist;

import java.util.stream.Collectors;

/**
 * Represents a page dedicated to an Artist, displaying their albums, merchandise, and events.
 */
public class ArtistPage implements Page {
    private final Artist artist;

    /**
     * Constructs an ArtistPage for a given Artist.
     *
     * @param artist The Artist for whom the page is created.
     */
    public ArtistPage(final Artist artist) {
        this.artist = artist;
    }

    /**
     * Generates and returns the content of the artist page in a formatted string.
     * The content includes lists of albums, merchandise, and events associated with the artist.
     *
     * @param lib The library containing data related to albums.
     * @param user The user viewing the artist page.
     * @return A string representing the formatted content of the artist page.
     */
    @Override
    public String printPage(final Library lib, final NormalUser user) {
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
        return lib.getArtistsAlbums(artist.getName()).stream()
                .map(Album::getName)
                .collect(Collectors.joining(", "));
    }

    private String getMerchInfo() {
        // Assuming Merch has getName(), getPrice(), and getDescription() methods
        return artist.getMerchList().stream()
                .map(merch -> merch.getName() + " - " + merch.getPrice() + ":\n\t"
                        + merch.getDescription())
                .collect(Collectors.joining(", "));
    }

    private String getEventInfo() {
        // Assuming Event has getName(), getDate(), and getDescription() methods
        return artist.getEvents().stream()
                .map(event -> event.getName() + " - " + event.getDate() + ":\n\t"
                        + event.getDescription())
                .collect(Collectors.joining(", "));
    }

}
