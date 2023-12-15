package app.commands.searchbar;

import app.entities.Library;
import app.entities.playable.Searchable;
import app.entities.userside.normaluser.NormalUser;
import app.entities.userside.normaluser.SearchBar;
import app.entities.userside.normaluser.UserPlayer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import app.commands.Command;
import app.common.Output;

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
    public void execute(final ArrayNode outputs, final Library lib, final boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.SEARCH);

        if (offline) {
            userIsOffline(out);
            return;
        }

        NormalUser normalUser = lib.getUserWithUsername(getUsername());

        assert normalUser != null;
        UserPlayer userPlayer = normalUser.getPlayer();

        SearchBar bar = userPlayer.getSearchBar();
        List<Searchable> searchResult = bar.search(lib, this.type, this.filters, getUsername());

        lib.getUserWithUsername(getUsername()).getPlayer().stop();

        out.put(Output.MESSAGE, "Search returned " + searchResult.size() + " results");

        ArrayNode resultsNode = out.putArray(Output.RESULTS);
        for (Searchable result : searchResult) {
            resultsNode.add(result.getName());
        }
    }

    /**
     * Handles the case when a user is offline. This method is called to provide an
     * appropriate response when the user executing the command is offline.
     *
     * @param out The ObjectNode where the offline message should be added.
     */
    @Override
    protected void userIsOffline(final ObjectNode out) {
        out.put(Output.MESSAGE, getUsername() + Output.IS_OFFLINE);
        out.putArray(Output.RESULTS);
    }
}
