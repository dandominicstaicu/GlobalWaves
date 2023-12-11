package entities.user.side;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Merch {
    private String name;
    private String description;
    private int price;
}
