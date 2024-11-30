package WatChill.Series;

import WatChill.Review.Review;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

// Specify the attributes for jackson and ignore getter methods
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)

public class Season {
    private String title;
    private LocalDate releaseDate;
    private String description;
    private ArrayList<Episode> episodes;

    // Specify the constructor and parameters for jackson to serialize rhe class
    @JsonCreator
    public Season(
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("releaseDate") LocalDate releaseDate,
            @JsonProperty("episodes") ArrayList<Episode> episodes) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.episodes = episodes;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    public void addEpisode(Episode episode) {
        episodes.add(episode);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViewsCount() {
        //Initialize total views count to zero
        int totalViewsCount = 0;
        for (Episode episode : getEpisodes()) {
            //Sums all episodes of the season view count
            totalViewsCount += episode.getViewsCount();
        }
        return totalViewsCount;
    }
}