package app.commands.specialusers.artist;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.User;
import app.entities.userside.artist.Artist;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class CheckExistenceArtist extends Command {
    /**
     * Validates the existence of a user and artist with the given username in the library.
     *
     * @param library  The Library where user data is stored.
     * @param out      The ObjectNode to which command outputs are added.
     * @param username The username to validate.
     * @return True if the user and artist do not exist, false otherwise.
     */
    protected boolean validateUserAndArtist(final Library library, final ObjectNode out,
                                            final String username) {
        User user = library.searchAllUsersForUsername(username);
        if (user == null) {
            out.put(Output.MESSAGE, Output.THE_USERNAME + username + Output.DOESNT_EXIST);
            return true;
        }

        Artist artist = library.getArtistWithName(username);
        if (artist == null) {
            out.put(Output.MESSAGE, username + Output.NOT_ARTIST);
            return true;
        }

        return false;
    }
}
