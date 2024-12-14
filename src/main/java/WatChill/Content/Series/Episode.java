package WatChill.Content.Series;

import WatChill.Content.WatchedContent;
import WatChill.UserWatchRecord.UserWatchRecord;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

// Specify the attributes for jackson and ignore getter methods
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Episode implements WatchedContent {
    private String id;
    private String title;
    private int duration;
    private LocalDate releaseDate;
    private int viewsCount;
    private String poster;
    private String description;
    private String seasonId;

    public Episode(String title, int duration, LocalDate releaseDate, String poster, String description, String seasonId) {//This constructor is made for when an episode is created for first time
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.poster = poster;
        this.viewsCount = 0;
        this.seasonId = seasonId;
        this.description = description;
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
            @JsonProperty("description") String description,
            @JsonProperty("seasonId") String seasonId
    ) {//This constructor is made for when an episode is retrieved from file
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.viewsCount = viewsCount;
        this.poster = poster;
        this.description = description;
        this.seasonId = seasonId;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
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

    @Override
    public String getId() {
        return id;
    }

    @Override
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

    public static Episode findById(String id) {
        return Series.retrieveSeries().stream()
                .flatMap(series -> series.getSeasons().stream())
                .flatMap(season -> season.getEpisodes().stream())
                .filter(episode -> episode.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}