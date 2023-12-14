package app.commands.normaluser.pages;

import app.entities.Library;
import app.entities.userside.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.commands.Command;
import app.common.Output;
import app.entities.userside.pages.Page;
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

        printCommandInfo(out, Output.PRINT_CURRENT_PAGE);

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
