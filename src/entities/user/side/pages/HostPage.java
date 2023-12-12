package entities.user.side.pages;

import common.UserTypes;
import entities.Library;
import entities.playable.Playable;
import entities.playable.Podcast;
import entities.user.side.Announcement;
import entities.user.side.NormalUser;
import entities.user.side.User;
import entities.user.side.UserPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class HostPage extends User implements Page, Playable {
    //    ArrayList<Podcast> podcasts;
    ArrayList<Announcement> announcements;

    public HostPage(final String username, final int age, final String city) {
        super(username, age, city, UserTypes.HOST);
        this.announcements = new ArrayList<>();
    }

    @Override
    public String printPage(final Library lib, final NormalUser user) {
        return "empty HostPage";
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
            if (user.getCurrentPage().equals(this)) {
                return false;
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
}
