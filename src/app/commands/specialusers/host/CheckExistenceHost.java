package app.commands.specialusers.host;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.User;
import app.entities.userside.host.Host;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class CheckExistenceHost extends Command {
    /**
     * Validates the existence of a user and host with the given username in the library.
     *
     * @param library  The Library where user data is stored.
     * @param out      The ObjectNode to which command outputs are added.
     * @param username The username to validate.
     * @return True if the user and artist do not exist, false otherwise.
     */
    protected boolean validateUserAndHost(final Library library, final ObjectNode out,
                                            final String username) {
        User user = library.searchAllUsersForUsername(username);
        if (user == null) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + username + Output.DOESNT_EXIST);
            return true;
        }

        Host host = library.getHostWithName(username);
        if (host == null) {
            out.put(Output.MESSAGE, username + Output.NOT_HOST);
            return true;
        }

        return false;
    }
}
