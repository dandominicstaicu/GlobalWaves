package app.entities.userside.artist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Event {
    private String name;
    private String description;
    private String date;
}
