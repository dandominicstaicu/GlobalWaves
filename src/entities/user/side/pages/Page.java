package entities.user.side.pages;

import entities.Library;
import entities.user.side.NormalUser;

public interface Page {
    String printPage(Library lib, NormalUser user);
}
