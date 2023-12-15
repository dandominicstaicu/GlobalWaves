package app.entities.userside.host;

import app.entities.Library;
import app.entities.playable.Searchable;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.normaluser.SearchBar;
import app.entities.userside.User;
import app.entities.userside.pages.HostPage;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.common.Output;
import app.common.UserTypes;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class Host extends User implements Searchable {
    private ArrayList<Announcement> announcements;
    private HostPage hostPage;

    /**
     * Constructs a Host object with the specified username, age, and city.
     *
     * @param username The username of the host.
     * @param age      The age of the host.
     * @param city     The city where the host resides.
     */
    public Host(final String username, final int age, final String city) {
        super(username, age, city, UserTypes.HOST);
        this.announcements = new ArrayList<>();
        this.hostPage = new HostPage(this);
    }

    /**
     * Checks if the host has any announcements.
     *
     * @return false since hosts typically have announcements.
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Gets the name of the host.
     *
     * @return The username of the host.
     */
    @Override
    public String getName() {
        return getUsername();
    }

    /**
     * Checks if the host represents a playlist.
     *
     * @return false since hosts do not represent playlists.
     */
    @Override
    public boolean isPlaylist() {
        return false;
    }

    /**
     * Adds the host to the specified library.
     *
     * @param library The library to which the host is added.
     */
    @Override
    public void addUser(final Library library) {
        library.getHosts().add(this);
    }

    /**
     * Handles the deletion of the host's account from the library. Checks for user interactions
     * with the host's page and content before deletion.
     *
     * @param library The library from which the host's account is deleted.
     * @return true if the host's account was successfully deleted, false otherwise.
     */
    @Override
    public boolean handleDeletion(final Library library) {
        boolean isUsedByAnyUser = library.getUsers().stream()
                .anyMatch(user ->
                        user.getCurrentPage().equals(this.getHostPage())
                                || (user.getPlayer().getLoadedContentReference() != null
                                && user.getPlayer().getLoadedContentReference()
                                .isLoadedInPlayer(this.getUsername())
                                && user.getPlayer().getIsPlaying())
                );

        if (!isUsedByAnyUser) {
            library.getHosts().remove(this);
            return true;
        }

        return false;
    }

    /**
     * Adds an announcement to the host's list of announcements.
     *
     * @param announce The announcement to be added.
     */
    public void addAnnounce(final Announcement announce) {
        announcements.add(announce);
    }

    /**
     * Removes an announcement from the host's list of announcements.
     *
     * @param announce The announcement to be removed.
     */
    public void removeAnnounce(final Announcement announce) {
        announcements.remove(announce);
    }

    /**
     * Retrieves an announcement with the given name from the host's list of announcements.
     *
     * @param name The name of the announcement to search for.
     * @return The announcement with the given name, or null if not found.
     */
    public Announcement getAnnounceWithName(final String name) {
        return announcements.stream()
                .filter(announce -> announce.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if any announcement in the list has the given name.
     *
     * @param announcementName The name of the announcement to search for.
     * @return true if an announcement with the given name exists, false otherwise.
     */
    public boolean hasAnnouncementWithName(final String announcementName) {
        return announcements.stream()
                .anyMatch(announcement -> announcement.getName().equals(announcementName));
    }

    /**
     * Handles the selection of the host's page by a normal user. Clears the selected result
     * and last search results in the search bar, and sets the current page of the user to
     * the host's page.
     *
     * @param searchBar The search bar instance.
     * @param user      The normal user performing the selection.
     * @param out       The ObjectNode for output (used for handling the selection).
     */
    @Override
    public void handleSelect(final SearchBar searchBar, final NormalUser user,
                             final ObjectNode out) {
        searchBar.setSelectedResult(null);
        searchBar.setLastSearchResults(null);

        out.put(Output.MESSAGE, "Successfully selected " + getUsername() + "'s page.");
        user.setCurrentPage(this.getHostPage());
    }
}
