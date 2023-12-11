package common;

public enum UserTypes {
    NORMAL_USER,
    ARTIST,
    HOST;

    public static UserTypes fromString(String type) {
        return switch (type.toLowerCase()) {
            case "user" -> NORMAL_USER;
            case "artist" -> ARTIST;
            case "host" -> HOST;
            default -> throw new IllegalArgumentException("Unknown user type: " + type);
        };
    }
}
