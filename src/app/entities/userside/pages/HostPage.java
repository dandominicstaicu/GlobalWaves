package app.entities.userside.pages;

import app.common.PageTypes;
import app.entities.Library;
import app.entities.playable.Podcast;
import app.entities.userside.User;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.host.Host;

import java.util.stream.Collectors;

public class HostPage implements Page {
    private final Host host;

    /**
     * Constructs a HostPage object with the specified host.
     *
     * @param host The host for which the page is being created.
     */
    public HostPage(final Host host) {
        this.host = host;
    }

    /**
     * Generates and returns the content of the host's page for a given user and library.
     * The content includes information about the host's podcasts and announcements.
     *
     * @param lib  The library containing podcast and announcement information.
     * @param user The normal user for whom the page is being generated.
     * @return A string containing the formatted content of the host's page.
     */
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
        if (!host.getAnnouncements().isEmpty()) {
            String announcementInfo = host.getAnnouncements().stream()
                    .map(announcement -> announcement.getName() + ":\n\t"
                            + announcement.getDescription() + "\n")
                    .collect(Collectors.joining(", "));
            pageContent.append("[").append(announcementInfo.trim()).append("\n]");
        } else {
            pageContent.append("[]");
        }

        return pageContent.toString();
    }

    @Override
    public PageTypes getPageType() {
        return PageTypes.HOST_PAGE;
    }

    /**
     * Retrieves and formats information about the host's podcasts from the library.
     *
     * @param lib The library containing podcast information.
     * @return A string containing the formatted podcast information.
     */
    private String getPodcastInfo(final Library lib) {
        return lib.getHostsPodcasts(host.getName()).stream()
                .map(podcast -> podcast.getName() + ":\n\t" + getEpisodeInfo(podcast) + "\n")
                .collect(Collectors.joining(", "));
    }

    /**
     * Retrieves and formats information about the episodes of a podcast.
     *
     * @param podcast The podcast for which episode information is being retrieved.
     * @return A string containing the formatted episode information.
     */
    private String getEpisodeInfo(final Podcast podcast) {
        String episodeDescriptions = podcast.getEpisodes().stream()
                .map(episode -> episode.getName() + " - " + episode.getDescription())
                .collect(Collectors.joining(", "));

        return episodeDescriptions.isEmpty() ? "[]" : "[" + episodeDescriptions + "]";
    }

    @Override
    public User getOwner() {
        return this.host;
    }
}
