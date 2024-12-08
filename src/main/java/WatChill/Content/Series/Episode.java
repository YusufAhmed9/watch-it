package WatChill.Content.Series;

import WatChill.WatchableContent;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

// Specify the attributes for jackson and ignore getter methods
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Episode implements WatchableContent {
    private String id;
    private String title;
    private int duration;
    private LocalDate releaseDate;
    private int viewsCount;
    private String poster;
    private String description;

    public Episode(String id, String title, int duration, LocalDate releaseDate, String poster, String description) {//This constructor is made for when an episode is created for first time
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.poster = poster;
        this.viewsCount = 0;
    }

    // Specify the constructor and parameters for jackson to serialize rhe class
    @JsonCreator
    public Episode(
            @JsonProperty("id") String id,
            @JsonProperty("title") String title,
            @JsonProperty("duration") int duration,
            @JsonProperty("releaseDate") LocalDate releaseDate,
            @JsonProperty("viewsCount") int viewsCount,
            @JsonProperty("poster") String poster,
            @JsonProperty("description") String description
    ) {//This constructor is made for when an episode is retrieved from file
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.viewsCount = viewsCount;
        this.poster = poster;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public void updateRating() {
    }
}