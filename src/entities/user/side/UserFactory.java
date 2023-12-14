package entities.user.side;

import entities.user.side.artist.Artist;
import entities.user.side.host.Host;

public class UserFactory {
    public static User createUser(String username, int age, String city, String type) {

        return switch (type.toLowerCase()) {
            case "user" -> new NormalUser(username, age, city);
            case "artist" -> new Artist(username, age, city);
            case "host" -> new Host(username, age, city);
            default -> null;
        };
    }
}
