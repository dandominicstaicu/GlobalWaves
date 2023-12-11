package entities.user.side;

import common.UserTypes;
import entities.playable.Podcast;
import entities.user.side.pages.HostPage;
import entities.user.side.pages.Page;

import java.util.ArrayList;

public class Host extends PrivilegedUser {
    ArrayList<Podcast> podcasts;
    ArrayList<String> announcements; //TODO make class Announcement
    public Host(String username, int age, String city, Page hostPage) {
        super(username, age, city, UserTypes.HOST, hostPage);
    }
}
