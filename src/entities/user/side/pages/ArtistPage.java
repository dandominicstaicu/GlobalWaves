package entities.user.side.pages;

import common.UserTypes;
import entities.Library;
import entities.playable.Playable;
import entities.user.side.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ArtistPage extends User implements Page, Playable {
    //    ArrayList<Album> albums;
    ArrayList<Event> events;
    ArrayList<Merch> merch;

    public ArtistPage(final String username, final int age, final String city) {
        super(username, age, city, UserTypes.ARTIST);
        this.events = new ArrayList<>();
        this.merch = new ArrayList<>();
    }

    @Override
    public String printPage(NormalUser user) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void loadToQueue(UserPlayer userPlayer) {

    }

    @Override
    public boolean isPlaylist() {
        return false;
    }

    @Override
    public void addUser(Library library) {
        library.getArtists().add(this);
    }
}
