package commands.searchbar;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.playable.Playable;
import entities.user.side.NormalUser;
import entities.user.side.UserPlayer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Select extends Command {
    private Integer itemNumber;

    /**
     * Returns a string representation of the command.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return super.toString()
                + "Select{"
                + ", itemNumber=" + itemNumber
                + '}';
    }

    /**
     * Executes the command to select a song and adds the results to the outputs.
     *
     * @param outputs The ArrayNode to which command outputs are added.
     * @param lib     The library on which the command operates.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library lib, boolean offline) {
        ObjectNode out = outputs.addObject();
        printCommandInfo(out, Output.SELECT);

        if (offline) {
            userIsOffline(out);
            return;
        }

        NormalUser normalUser = lib.getUserWithUsername(getUsername());

        assert normalUser != null;
        UserPlayer userPlayer = normalUser.getPlayer();

        List<Playable> lastSearchResults = userPlayer.getSearchBar().getLastSearchResults();

        if (lastSearchResults == null) {
            out.put(Output.MESSAGE, Output.NO_SEARCH);
        } else if (itemNumber < 1 || itemNumber > lastSearchResults.size()) {
            userPlayer.getSearchBar().setLastSearchResults(null);
            userPlayer.getSearchBar().setSelectedResult(null);
            out.put(Output.MESSAGE, Output.SELECTED_ID_HIGH);
        } else {
            Playable selectedResult = lastSearchResults.get(itemNumber - 1); // Adjust for 0 index
            userPlayer.getSearchBar().setSelectedResultAndClear(selectedResult, normalUser, out);
        }
    }
}
