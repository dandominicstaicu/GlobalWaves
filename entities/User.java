package entities;

import lombok.Getter;

@Getter
public class User {
    private String username;
    private int age;
    private String city;

    public User() {
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public void setCity(final String city) {
        this.city = city;
    }
}
