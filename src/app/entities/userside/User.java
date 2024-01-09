package app.entities.userside;

import app.entities.Library;
import app.common.UserTypes;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class User {
    private String username;
    private int age;
    private String city;
    private UserTypes userType;

    /**
     * Adds the user to the specified library. The implementation of this method depends on the
     * specific user type (e.g., NormalUser, Artist, Host) and their interactions with the library.
     *
     * @param library The library to which the user is added.
     */
    public abstract void addUser(Library library);

    /**
     * Handles the deletion of the user's account from the library. The implementation of this
     * method depends on the specific user type and the actions required to delete their account.
     *
     * @param library The library from which the user's account is deleted.
     * @return true if the user's account was successfully deleted, false otherwise.
     */
    public abstract boolean handleDeletion(Library library);

    public abstract void printWrappedStats(final ObjectNode out);

    public void subscribe(NormalUser user) {
        System.out.println("normal user can't do that");
    }

    public boolean isSubscribed(String username) {
        System.out.println("normal user can't do that");
        return false;
    }

    public void unsubscribe(NormalUser user) {
        System.out.println("normal user can't do that");
    }
}
