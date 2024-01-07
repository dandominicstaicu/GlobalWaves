package app.commands.specialusers.artist;
import app.entities.userside.artist.Artist;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Monetization {
    private Artist artist;
    private Boolean interacted;

    private Double songRevenue;
    private Double merchRevenue;
    private String mostProfitableSong;

    public Monetization(final Artist artist) {
        this.songRevenue = 0.0;
        this.merchRevenue = 0.0;
        this.mostProfitableSong = "N/A";

        this.artist = artist;
        this.interacted = false;
    }

    public void interact() {
        this.interacted = true;
    }

}
