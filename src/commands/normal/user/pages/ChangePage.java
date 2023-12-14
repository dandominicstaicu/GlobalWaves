package commands.normal.user.pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import common.Output;
import entities.Library;
import entities.user.side.NormalUser;
import entities.user.side.pages.HomePage;
import entities.user.side.pages.LikedContentPage;
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

    @Override
    public void execute(final ArrayNode outputs, final Library lib, boolean offline) {
        if (offline) {
            userIsOffline(outputs);
            return;
        }

        ObjectNode out = outputs.addObject();
        out.put(Output.COMMAND, Output.CHANGE_PAGE);
        out.put(Output.USER, getUsername());
        out.put(Output.TIMESTAMP, getTimestamp());

        NormalUser user = lib.getUserWithUsername(getUsername());

        switch (nextPage) {
            case "Home":
                HomePage homePage = new HomePage();
                user.setCurrentPage(homePage);
                out.put(Output.MESSAGE, getUsername() + " accessed Home successfully.");
                break;
            case "LikedContent":
                LikedContentPage likedContentPage = new LikedContentPage();
                user.setCurrentPage(likedContentPage);
                out.put(Output.MESSAGE, getUsername() + " accessed LikedContent successfully.");
                break;
            default:
                out.put(Output.MESSAGE, getUsername() + " is trying to access a non-existent page.");
                break;
        }

    }
}
