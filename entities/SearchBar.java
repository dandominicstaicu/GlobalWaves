package entities;

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


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchBar {
	private List<Playable> lastSearchResults;
	private Playable selectedResult;

	// ChatGPT helped me structure this method and wrote some of the code for the actual searching
	public List<Playable> search(Library library, String type, Map<String, Object> filters, String username) {
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

		List<Playable> searchResults = results.size() > 5 ? results.subList(0, 5) : results;
		this.setLastSearchResults(searchResults);

		return searchResults;
	}

	private List<Song> searchSongs(Library library, Map<String, Object> filters) {
		return library.getSongs().stream()
				.filter(song -> matchesFiltersSong(song, filters))
				.collect(Collectors.toList());
	}

	private List<Playlist> searchPlaylists(Library library, Map<String, Object> filters, String username) {
		return library.getPlaylists().stream()
				.filter(playlist -> matchesFiltersPlaylist(playlist, filters, username))
				.collect(Collectors.toList());
	}

	private List<Podcast> searchPodcasts(Library library, Map<String, Object> filters) {
		return library.getPodcasts().stream()
				.filter(podcast -> matchesFiltersPodcast(podcast, filters))
				.collect(Collectors.toList());
	}

	// chatGPT helped me write this function so I can parse Strings easily
	// this helped me implement the other 2 search functions by myself
	private boolean matchesFiltersSong(Song song, Map<String, Object> filters) {
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
					List<String> tags = (List<String>) filter.getValue();
					if (!song.getTags().containsAll(tags)) {
						return false;
					}
					break;
				case "lyrics":
					if (!song.getLyrics().contains((String) filter.getValue())) {
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
					// Handle unknown filter
					break;
			}
		}
		return true;
	}

	private boolean matchesFiltersPlaylist(Playlist playlist, Map<String, Object> filters, String username) {
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
					// Example: You might want to add a filter by playlist name
					if (!playlist.getName().equalsIgnoreCase((String) filter.getValue())) {
						return false;
					}
					break;
				// Add other cases if there are more filters
				default:
					// Handle unknown filter
					break;
			}
		}
		return true;
	}

	private boolean matchesFiltersPodcast(Podcast podcast, Map<String, Object> filters) {
		for (Map.Entry<String, Object> filter : filters.entrySet()) {
			switch (filter.getKey().toLowerCase()) {
				case "name":
					// Check if the podcast's name contains the string specified in the 'name' filter
					if (!podcast.getName().toLowerCase().contains(((String) filter.getValue()).toLowerCase())) {
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
	 * @param filter      The filter string, which can be a specific year or a comparator with a year.
	 * @return true if the song's release year matches the filter, false otherwise.
	 */
	private boolean matchesReleaseYearFilter(Integer releaseYear, String filter) {
		Pattern pattern = Pattern.compile("([<>=]*)(\\d+)");
		Matcher matcher = pattern.matcher(filter);

		if (matcher.matches()) {
			String operator = matcher.group(1);
			int year = Integer.parseInt(matcher.group(2));

			// Intellij suggested I use enhanced switch expression
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
