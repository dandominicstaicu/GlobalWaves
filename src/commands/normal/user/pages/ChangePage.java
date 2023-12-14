package commands.normal.user.pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.user.side.NormalUser;
import entities.user.side.pages.HomePage;
import entities.user.side.pages.LikedContentPage;
import entities.user.side.pages.Page;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePage extends Command {
    private String nextPage;

    @Override
    public String toString() {
        return "ChangePage{"
                + "nextPage='" + nextPage + '\''
                + '}';
    }

    private void setPageAndCreateMessage(final NormalUser user, final Page page,
                                         final String message, final ObjectNode out) {
        user.setCurrentPage(page);
        out.put(Output.MESSAGE, getUsername() + " accessed " + message + " successfully.");
    }

    @Override
    public void execute(final ArrayNode outputs, final Library lib, boolean offline) {
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.CHANGE_PAGE);

        if (offline) {
            userIsOffline(out);
            return;
        }

        NormalUser user = lib.getUserWithUsername(getUsername());
        assert user != null;

        switch (nextPage) {
            case "Home":
                setPageAndCreateMessage(user, new HomePage(), "Home", out);
                break;
            case "LikedContent":
                setPageAndCreateMessage(user, new LikedContentPage(), "LikedContent", out);
                break;
            default:
                out.put(Output.MESSAGE, getUsername() + " is trying to access a non-existent page.");
                break;
        }
    }
}
