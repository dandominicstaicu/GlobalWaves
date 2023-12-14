package entities.user.side.pages;

import entities.Library;
import entities.playable.Podcast;
import entities.user.side.NormalUser;
import entities.user.side.host.Host;

import java.util.stream.Collectors;

public class HostPage implements Page{
    Host host;

    public HostPage(Host host) {
        this.host = host;
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
        if (!host.getAnnouncements().isEmpty()) {
            String announcementInfo = host.getAnnouncements().stream()
                    .map(announcement -> announcement.getName() + ":\n\t" + announcement.getDescription() + "\n")
                    .collect(Collectors.joining(", "));
            pageContent.append("[").append(announcementInfo.trim()).append("\n]");
        } else {
            pageContent.append("[]");
        }

        return pageContent.toString();
    }

    private String getPodcastInfo(final Library lib) {
        return lib.getHostsPodcasts(host.getName()).stream()
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
}
