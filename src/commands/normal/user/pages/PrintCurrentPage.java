package commands.normal.user.pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Constants;
import common.Output;
import entities.Library;
import entities.user.side.NormalUser;
import entities.user.side.pages.Page;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class PrintCurrentPage extends Command {

    @Override
    public String toString() {
        return super.toString() + "PrintCurrentPage{}";
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
        ObjectNode out = outputs.addObject();

        out.put(Output.COMMAND, Output.PRINT_CURRENT_PAGE);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        if (offline) {
            out.put(Output.MESSAGE, getUsername() + Output.IS_OFFLINE);
            return;
        }


        NormalUser user = library.getUserWithUsername(getUsername());
        assert user != null;
        Page currentPage = user.getCurrentPage();

        out.put(Output.MESSAGE, currentPage.printPage(library, user));
    }
}
