package entities.user.side;

import entities.user.side.pages.*;

public class UserFactory {
    public static User createUser(String username, int age, String city, String type) {

        return switch (type.toLowerCase()) {
            case "user" -> new NormalUser(username, age, city);
            case "artist" -> new ArtistPage(username, age, city);
            case "host" -> new HostPage(username, age, city);
            default -> null;
        };
    }
}
