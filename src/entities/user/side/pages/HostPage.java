package entities.user.side.pages;

import com.fasterxml.jackson.databind.node.ObjectNode;
import common.Output;
import common.UserTypes;
import entities.Library;
import entities.playable.Playable;
import entities.playable.Podcast;
import entities.user.side.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        StringBuilder pageContent = new StringBuilder();

        // Formatting the podcast list
        pageContent.append("Podcasts:\n\t");
        String podcastInfo = getPodcastInfo(lib);
        if (!podcastInfo.isEmpty()) {
            pageContent.append("[").append(podcastInfo).append("]");
        } else {
            pageContent.append("[]");
        }
        pageContent.append("\n\n");

        // Formatting the announcements list
        pageContent.append("Announcements:\n\t");
        if (!announcements.isEmpty()) {
            String announcementInfo = announcements.stream()
                    .map(announcement -> announcement.getName() + ":\n\t" + announcement.getDescription() + "\n")
                    .collect(Collectors.joining(", "));
            pageContent.append("[").append(announcementInfo.trim()).append("\n]");
        } else {
            pageContent.append("[]");
        }

        return pageContent.toString();
    }

    private String getPodcastInfo(final Library lib) {
        return lib.getHostsPodcasts(this.getName()).stream()
                .map(podcast -> podcast.getName() + ":\n\t" + getEpisodeInfo(podcast) + "\n")
                .collect(Collectors.joining(", "));
    }


    // method for getting episode info of a podcast
    private String getEpisodeInfo(Podcast podcast) {
        String episodeDescriptions = podcast.getEpisodes().stream()
                .map(episode -> episode.getName() + " - " + episode.getDescription())
                .collect(Collectors.joining(", "));

        return episodeDescriptions.isEmpty() ? "[]" : "[" + episodeDescriptions + "]";
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
        out.put(Output.MESSAGE, "Successfully selected " + getUsername() + "'s page.");
        user.setCurrentPage(this);
    }
}
