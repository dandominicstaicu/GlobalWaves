package app.commands.specialusers.artist;

import app.entities.userside.artist.Artist;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Monetization {
    private Artist artist;
    private Boolean interacted;

    private Double songRevenue;
    private Double merchRevenue;
    private String mostProfitableSong;

    private HashMap<String, Double> revenuePerSong;

    public Monetization(final Artist artist) {
        this.songRevenue = 0.0;
        this.merchRevenue = 0.0;
        this.mostProfitableSong = "N/A";

        this.artist = artist;
        this.interacted = false;

        this.revenuePerSong = new HashMap<>();
    }

    public void interact() {
        this.interacted = true;
    }

    public void addSongRevenue(Double revenue) {
        this.songRevenue += revenue;
    }

    public void addRevenuePerSong(String song, Double revenue) {
        Double currentRevenue = revenuePerSong.getOrDefault(song, 0.0);
        currentRevenue += revenue;
        this.revenuePerSong.put(song, currentRevenue);
    }

    // chat GPT wrote this when I asked for a function that gets the highest value in a hashmap
    private String getKeyWithHighestValue() {
        String keyWithHighestValue = null;
        double highestValue = Double.MIN_VALUE;

        for (Map.Entry<String, Double> entry : revenuePerSong.entrySet()) {
            double currentValue = entry.getValue();
            String currentKey = entry.getKey();

            // Check if the current value is greater than the highest value found so far
            if (currentValue > highestValue) {
                highestValue = currentValue;
                keyWithHighestValue = currentKey;
            }
            // If the current value is equal to the highest value, compare keys alphabetically
            else if (currentValue == highestValue && keyWithHighestValue != null) {
                if (currentKey.compareTo(keyWithHighestValue) < 0) {
                    keyWithHighestValue = currentKey;
                }
            }
        }

        return keyWithHighestValue;
    }

    public void decideMostProfitableSong() {
        this.mostProfitableSong = getKeyWithHighestValue();
    }

}
