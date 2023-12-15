package app.entities.userside;

import app.entities.userside.artist.Artist;
import app.entities.userside.host.Host;
import app.entities.userside.normaluser.NormalUser;


/**
 * Factory class for creating different types of User objects.
 *
 * This class follows the Factory Design Pattern to provide a method for creating various types
 * of users (NormalUser, Artist, Host) based on a given type. The class is not intended to be
 * instantiated, hence the private constructor.
 */
public final class UserFactory {
    /**
     * Private constructor to prevent instantiation of the factory class.
     */
    private UserFactory() {

    }

    /**
     * Creates and returns a User object based on the specified type. The type of user
     * created (NormalUser, Artist, Host) depends on the provided 'type' parameter.
     *
     * @param username The username of the user to be created.
     * @param age The age of the user.
     * @param city The city of the user.
     * @param type The type of the user, which determines the concrete class of the user object.
     *             Expected values are "user", "artist", or "host".
     * @return A User object of the specified type, or null if the type does not match any known
     * user types.
     */
    public static User createUser(final String username, final int age,
                                  final String city, final String type) {
        return switch (type.toLowerCase()) {
            case "user" -> new NormalUser(username, age, city);
            case "artist" -> new Artist(username, age, city);
            case "host" -> new Host(username, age, city);
            default -> null;
        };
    }
}
