package app.entities.userside.pages;

import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;

public interface Page {
    /**
     * Generates and returns the content of the artist page in a formatted string.
     * The content includes lists of albums, merchandise, and events associated with the artist.
     *
     * @param lib The library containing data related to albums.
     * @param user The user viewing the artist page.
     * @return A string representing the formatted content of the artist page.
     */
    String printPage(Library lib, NormalUser user);
}
