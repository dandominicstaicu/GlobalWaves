package entities.user.side.pages;

public class PageFactory {
    public static Page createPage(String pageType) {
        return switch (pageType.toLowerCase()) {
            case "user" -> new HomePage();
            case "artist" -> new ArtistPage();
            case "host" -> new HostPage();
            default -> null;
        };
    }
}
