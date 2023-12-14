package entities.user.side.host;

import com.fasterxml.jackson.databind.node.ObjectNode;
import common.Output;
import common.UserTypes;
import entities.Library;
import entities.playable.Playable;
import entities.playable.Podcast;
import entities.user.side.*;
import entities.user.side.pages.HostPage;
import entities.user.side.pages.Page;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Setter
@Getter
public class Host extends User implements Playable {
    ArrayList<Announcement> announcements;
    HostPage hostPage;

    public Host(final String username, final int age, final String city) {
        super(username, age, city, UserTypes.HOST);
        this.announcements = new ArrayList<>();
        this.hostPage = new HostPage(this);
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
    public void addUser(Library library) {
        library.getHosts().add(this);
    }

    @Override
    public boolean handleDeletion(Library library) {
        for (NormalUser user : library.getUsers()) {
            // if this page is used by a user at deletion time, it has to fail
            if (user.getCurrentPage().equals(this.getHostPage())) {
                return false;
            }

            if (user.getPlayer().getLoadedContentReference() != null) {
                if (user.getPlayer().getLoadedContentReference().isLoadedInPlayer(this.getUsername())) {
                    if (user.getPlayer().getIsPlaying()) {
                        return false;
                    }
                }
            }

        }

        library.getHosts().remove(this);
        return true;
    }

    public void addAnnounce(final Announcement announce) {
        announcements.add(announce);
    }

    public void removeAnnounce(final Announcement announce) {
        announcements.remove(announce);
    }

    public Announcement getAnnounceWithName(final String name) {
        for (Announcement announce : announcements) {
            if (announce.getName().equals(name))
                return announce;
        }

        return null;
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

    @Override
    public void handleSelect(final SearchBar searchBar, final NormalUser user, final ObjectNode out) {
        searchBar.setSelectedResult(null);
        searchBar.setLastSearchResults(null);

        out.put(Output.MESSAGE, "Successfully selected " + getUsername() + "'s page.");
        user.setCurrentPage(this.getHostPage());
    }
}
