package entities.user.side.pages;

import common.UserTypes;
import entities.Library;
import entities.playable.Playable;
import entities.playable.Podcast;
import entities.user.side.NormalUser;
import entities.user.side.User;
import entities.user.side.UserPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class HostPage extends User implements Page, Playable {
    //    ArrayList<Podcast> podcasts;
    ArrayList<String> announcements;

    public HostPage(final String username, final int age, final String city) {
        super(username, age, city, UserTypes.HOST);
        this.announcements = new ArrayList<>();
    }

    @Override
    public String printPage(NormalUser user) {
        return "empty HostPage";
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
        library.getHosts().add(this);
    }
}
