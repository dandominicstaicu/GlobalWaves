package entities.user.side;

import common.UserTypes;
import entities.user.side.pages.Page;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class PrivilegedUser extends User {


    public PrivilegedUser(String username, int age, String city, UserTypes userType, Page defaultPage) {
        super(username, age, city, userType, defaultPage);
    }
}
