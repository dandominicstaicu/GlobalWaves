package entities.user.side;

import entities.Library;
import entities.playable.Playable;
import entities.playable.Playlist;
import entities.playable.Podcast;
import entities.playable.audio_files.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static common.Constants.MAX_LIST_RETURN;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class SearchBar {
    private List<Playable> lastSearchResults;
    private Playable selectedResult = null;


    /**
     * Searches the library for playable items (songs, playlists, podcasts)
     * based on the given type and filters.
     *
     * @param library  The library to search in.
     * @param type     The type of playable items to search for ("song", "playlist", "podcast").
     * @param filters  The search filters to apply.
     * @param username The username of the user performing the search.
     * @return A list of {@link Playable} items matching the search criteria.
     */
    public List<Playable> search(final Library library, final String type,
                                 final Map<String, Object> filters, final String username) {
        // ChatGPT helped me structure this method and wrote some of
        // the code for the actual searching
        List<Playable> results = new ArrayList<>();

        switch (type) {
            case "song":
                results.addAll(searchSongs(library, filters));
                break;
            case "playlist":
                results.addAll(searchPlaylists(library, filters, username));
                break;
            case "podcast":
                results.addAll(searchPodcasts(library, filters));
                break;
            default:
                break;
        }

        List<Playable> searchResults = results.size() > MAX_LIST_RETURN
                ? results.subList(0, MAX_LIST_RETURN) : results;
        this.setLastSearchResults(searchResults);

        return searchResults;
    }

    /**
     * Sets the selected result to a new Playable item and clears the last search results.
     * This method is used to update the currently selected Playable item and simultaneously
     * reset the last search results to null, indicating that no previous search results are
     * being held.
     *
     * @param newSelectedResult The new Playable item to be set as the selected result.
     */
    public void setSelectedResultAndClear(final Playable newSelectedResult) {
        this.selectedResult = newSelectedResult;
        this.lastSearchResults = null;
    }

    private List<Song> searchSongs(final Library library, final Map<String, Object> filters) {
        return library.getSongs().stream()
                .filter(song -> matchesFiltersSong(song, filters))
                .collect(Collectors.toList());
    }

    private List<Playlist> searchPlaylists(final Library library, final Map<String, Object> filters,
                                           final String username) {
        return library.getPlaylists().stream()
                .filter(playlist -> matchesFiltersPlaylist(playlist, filters, username))
                .collect(Collectors.toList());
    }

    private List<Podcast> searchPodcasts(final Library library, final Map<String, Object> filters) {
        return library.getPodcasts().stream()
                .filter(podcast -> matchesFiltersPodcast(podcast, filters))
                .collect(Collectors.toList());
    }

    // chatGPT helped me write this function, so I can parse Strings easily
    // this helped me implement the other 2 search functions by myself
    private boolean matchesFiltersSong(final Song song, final Map<String, Object> filters) {
        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            switch (filter.getKey().toLowerCase()) {
                case "name":
                    if (!song.getName().startsWith((String) filter.getValue())) {
                        return false;
                    }
                    break;
                case "album":
                    if (!song.getAlbum().equals(filter.getValue())) {
                        return false;
                    }
                    break;
                case "tags":
                    @SuppressWarnings("unchecked") // I know that the value is a List<String>
                    List<String> tags = (List<String>) filter.getValue();
                    if (!song.getTags().containsAll(tags)) {
                        return false;
                    }
                    break;
                case "lyrics":
                    final String lyrics = song.getLyrics().toLowerCase();
                    if (!lyrics.contains(((String) filter.getValue()).toLowerCase())) {
                        return false;
                    }
                    break;
                case "genre":
                    if (!song.getGenre().equalsIgnoreCase((String) filter.getValue())) {
                        return false;
                    }
                    break;
                case "releaseyear":
                    String releaseYearFilter = (String) filter.getValue();
                    if (!matchesReleaseYearFilter(song.getReleaseYear(), releaseYearFilter)) {
                        return false;
                    }
                    break;
                case "artist":
                    if (!song.getArtist().equalsIgnoreCase((String) filter.getValue())) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    private boolean matchesFiltersPlaylist(
            final Playlist playlist,
            final Map<String, Object> filters,
            final String username
    ) {
        // If the playlist is private and the user is not the owner, return false
        if (!playlist.getIsPublic() && !playlist.getOwner().equals(username)) {
            return false;
        }

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            switch (filter.getKey().toLowerCase()) {
                case "owner":
                    // Check if the playlist's owner matches the 'owner' filter
                    if (!playlist.getOwner().equals(filter.getValue())) {
                        return false;
                    }
                    break;
                case "name":
                    if (!playlist.getName().equalsIgnoreCase((String) filter.getValue())) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    private boolean matchesFiltersPodcast(final Podcast podcast,
                                          final Map<String, Object> filters) {
        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            switch (filter.getKey().toLowerCase()) {
                case "name":
                    // Check if the podcast's name contains the string specified in the name filter
                    final String name = podcast.getName().toLowerCase();
                    if (!name.contains(((String) filter.getValue()).toLowerCase())) {
                        return false;
                    }
                    break;
                case "owner":
                    // Check if the podcast's owner matches the 'owner' filter
                    if (!podcast.getOwner().equalsIgnoreCase((String) filter.getValue())) {
                        return false;
                    }
                    break;
                // You can add filters for episode attributes if needed
                // For example, filtering by a specific episode's name or description
                default:
                    // Handle unknown filter
                    break;
            }
        }
        return true;
    }

    /**
     * Matches the song's release year against the release year filter.
     * ChatGPT wrote this function for me
     *
     * @param releaseYear The release year of the song.
     * @param filter      The filter string; can be a specific year or a comparator with a year.
     * @return true if the song's release year matches the filter, false otherwise.
     */
    private boolean matchesReleaseYearFilter(final Integer releaseYear, final String filter) {
        Pattern pattern = Pattern.compile("([<>=]*)(\\d+)");
        Matcher matcher = pattern.matcher(filter);

        if (matcher.matches()) {
            String operator = matcher.group(1);
            int year = Integer.parseInt(matcher.group(2));

            return switch (operator) {
                case "<" -> releaseYear < year;
                case ">" -> releaseYear > year;
                case "=", "" -> releaseYear.equals(year);
                default ->
                    // Handle unknown operator
                        false;
            };
        }
        return false;
    }
}
