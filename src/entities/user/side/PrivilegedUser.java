package entities.user.side;

import common.UserTypes;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class PrivilegedUser extends User {


    public PrivilegedUser(String username, int age, String city, UserTypes userType) {
        super(username, age, city, userType);
    }
}
