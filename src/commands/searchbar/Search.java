package commands.searchbar;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import commands.Command;
import common.Output;
import entities.Library;
import entities.SearchBar;
import entities.playable.Playable;

import entities.UserPlayer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * The command class in charge of searching for a song.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Search extends Command {
    private String type;
    private Map<String, Object> filters;

    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "Search{"
                + ", type='" + type + '\''
                + ", filters=" + filters
                + '}';
    }

    /**
     * Executes the command to search for a song and adds the results to the outputs.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib) {
        UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();
        SearchBar bar = userPlayer.getSearchBar();
        List<Playable> searchResult = bar.search(lib, this.type, this.filters, getUsername());

        lib.getUserWithUsername(getUsername()).getPlayer().stop();

        ObjectNode out = outputs.addObject();
        out.put(Output.COMMAND, Output.SEARCH);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());
        out.put(Output.MESSAGE, "Search returned " + searchResult.size() + " results");

        ArrayNode resultsNode = out.putArray(Output.RESULTS);
        for (Playable result : searchResult) {
            resultsNode.add(result.getName());
        }
    }
}
