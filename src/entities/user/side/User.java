package entities.user.side;

import common.UserTypes;
import entities.Library;
import entities.user.side.pages.Page;
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

//    public abstract boolean isNormalUser();
    public abstract void addUser(Library library);
    public abstract boolean handleDeletion(Library library);
}
