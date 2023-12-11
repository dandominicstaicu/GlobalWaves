package entities.user.side;

import common.UserTypes;
import entities.playable.Album;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class Artist extends PrivilegedUser {
    ArrayList<Album> albums;
    ArrayList<Event> events;
    ArrayList<Merch> merches;


    public Artist(String username, int age, String city) {
        super(username, age, city, UserTypes.ARTIST);
        this.albums = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merches = new ArrayList<>();
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }
}
