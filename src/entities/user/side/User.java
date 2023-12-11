package entities.user.side;

import common.UserTypes;
import entities.user.side.pages.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class User {
    private String username;
    private int age;
    private String city;
    private UserTypes userType;
    private Page currentPage;

//    public abstract boolean isNormalUser();

}
