package app.entities.userside.normaluser;

import app.entities.userside.artist.Artist;
import app.entities.userside.host.Host;
import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class WrappedStats {
    private HashMap<String, Integer> artistsListenCount;
    private HashMap<String, Integer> genresListenCount;
    private HashMap<String, Integer> songsListenCount;
    private HashMap<String, Integer> albumsListenCount;
    private HashMap<String, Integer> episodesListenCount;
    private HashMap<String, Integer> listenersCount;

    private Boolean registeredStats;

    public WrappedStats(NormalUser normalUser) {
        this.artistsListenCount = new HashMap<>();
        this.genresListenCount = new HashMap<>();
        this.songsListenCount = new HashMap<>();
        this.albumsListenCount = new HashMap<>();
        this.episodesListenCount = new HashMap<>();

        this.registeredStats = false;
    }

    public WrappedStats(Artist artist) {
        this.songsListenCount = new HashMap<>();
        this.albumsListenCount = new HashMap<>();
        this.listenersCount = new HashMap<>();

        this.registeredStats = false;
    }

    public WrappedStats(Host host) {
        this.episodesListenCount = new HashMap<>();
        this.listenersCount = new HashMap<>();

        this.registeredStats = false;
    }

    public void registerStats() {
        this.registeredStats = true;
    }

    /**
     * Increments the listen count for a specific artist.
     *
     * @param artist The artist to increment the listen count for.
     */
    public void addArtistListenCount(String artist) {
        int currentCount = artistsListenCount.getOrDefault(artist, 0);
        artistsListenCount.put(artist, currentCount + 1);
    }

    /**
     * Increments the listen count for a specific genre.
     *
     * @param genre The genre to increment the listen count for.
     */
    public void addGenreListenCount(String genre) {
        int currentCount = genresListenCount.getOrDefault(genre, 0);
        genresListenCount.put(genre, currentCount + 1);
    }

    /**
     * Increments the listen count for a specific song.
     *
     * @param song The song to increment the listen count for.
     */
    public void addSongListenCount(String song) {
        int currentCount = songsListenCount.getOrDefault(song, 0);
        songsListenCount.put(song, currentCount + 1);
    }

    /**
     * Increments the listen count for a specific album.
     *
     * @param album The album to increment the listen count for.
     */
    public void addAlbumListenCount(String album) {
        int currentCount = albumsListenCount.getOrDefault(album, 0);
        albumsListenCount.put(album, currentCount + 1);
    }

    /**
     * Increments the listen count for a specific episode.
     *
     * @param episode The episode to increment the listen count for.
     */
    public void addEpisodeListenCount(String episode) {
        int currentCount = episodesListenCount.getOrDefault(episode, 0);
        episodesListenCount.put(episode, currentCount + 1);
    }

    /**
     * Increments the listen count for a specific listener (NormalUser).
     *
     * @param listener The listener (NormalUser) to increment the listen count for.
     */
    public void addListenerCount(String listener) {
        int currentCount = listenersCount.getOrDefault(listener, 0);
        listenersCount.put(listener, currentCount + 1);
    }

    /**
     * Retrieves the top 5 artists based on their listen counts in descending order.
     *
     * @return A list of Map entries representing the top 5 artists and their listen counts.
     */
    public List<Map.Entry<String, Integer>> top5Artists() {
        return artistsListenCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry::getKey))
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the top 5 genres based on their listen counts in descending order.
     *
     * @return A list of Map entries representing the top 5 genres and their listen counts.
     */
    public List<Map.Entry<String, Integer>> top5Genres() {
        return genresListenCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the top 5 songs based on their listen counts in descending order.
     *
     * @return A list of Map entries representing the top 5 songs and their listen counts.
     */
    public List<Map.Entry<String, Integer>> top5Songs() {
        return songsListenCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry::getKey))
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the top 5 albums based on their listen counts in descending order.
     *
     * @return A list of Map entries representing the top 5 albums and their listen counts.
     */
    public List<Map.Entry<String, Integer>> top5Albums() {
        return albumsListenCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the top 5 episodes based on their listen counts in descending order.
     *
     * @return A list of Map entries representing the top 5 episodes and their listen counts.
     */
    public List<Map.Entry<String, Integer>> top5Episodes() {
        return episodesListenCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the top 5 listeners (NormalUsers) based on their listen counts in descending order.
     *
     * @return A list of Map entries representing the top 5 listeners and their listen counts.
     */
    public List<Map.Entry<String, Integer>> top5Fans() {
        return listenersCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry::getKey))
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Returns the number of listeners counted.
     *
     * @return The size of the listeners count.
     */
    public int getListenersCountSize() {
        return listenersCount.size();
    }
}
