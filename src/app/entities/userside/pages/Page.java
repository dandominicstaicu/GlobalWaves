package app.entities.userside.pages;

import app.common.PageTypes;
import app.entities.Library;
import app.entities.userside.User;
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

    /**
     * Gets the type of page associated with this object.
     *
     * @return The PageTypes enum value representing the type of page.
     */
    PageTypes getPageType();

    /**
     * Gets the owner of this page, which may be null in some cases.
     *
     * @return The User object representing the owner of the page, or null if no owner is specified.
     */
    default User getOwner() {
        return null;
    }
}
