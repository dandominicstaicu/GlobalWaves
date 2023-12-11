package entities.user.side;

import common.UserTypes;

public class Host extends PrivilegedUser {
    public Host(String username, int age, String city) {
        super(username, age, city, UserTypes.ARTIST);
    }
}
