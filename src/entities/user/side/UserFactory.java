package entities.user.side;

import entities.user.side.pages.HomePage;
import entities.user.side.pages.Page;
import entities.user.side.pages.PageFactory;

public class UserFactory {
    public static User createUser(String username, int age, String city, String type) {
        Page page = PageFactory.createPage(type);
        if (page == null) {
            System.out.println("error in UserFactory");
            return null;
        }

        return switch (type.toLowerCase()) {
            case "user" -> new NormalUser(username, age, city, page);
            case "artist" -> new Artist(username, age, city, page);
            case "host" -> new Host(username, age, city, page);
            default -> null;
        };
    }
}
