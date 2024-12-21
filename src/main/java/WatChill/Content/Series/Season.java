package WatChill.Content.Series;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

// Specify the attributes for jackson and ignore getter methods
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)

public class Season {
    private String id;
    private String seriesId;
    private String title;
    private LocalDate releaseDate;
    private String description;
    private ArrayList<Episode> episodes;

    // Specify the constructor and parameters for jackson to serialize rhe class
    @JsonCreator
    public Season(
            @JsonProperty("id") String id,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("releaseDate") LocalDate releaseDate,
            @JsonProperty("episodes") ArrayList<Episode> episodes,
            @JsonProperty("seriesId") String seriesId

    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.episodes = episodes;
        this.seriesId = seriesId;
    }

    public Season(String title, String description, LocalDate releaseDate, ArrayList<Episode> episodes, String seriesId) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.episodes = episodes;
        this.seriesId = seriesId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public void updateEpisode(Episode updatedEpisode) {
        for (int i = 0; i < getEpisodes().size(); i++) {
            Episode episode = getEpisodes().get(i);
            if (episode.getId().equals(updatedEpisode.getId())) {
                getEpisodes().set(i, updatedEpisode);  // Replace the old episode with the updated one
                break;  // Stop once the episode is found and updated
            }
        }
        Series series = Series.findById(getSeriesId());
        series.updateSeason(this);
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

    public double getRating() {
        double totalRating = 0.0;

        for (Episode episode : getEpisodes()) {
            totalRating += episode.getRating();
        }

        return !getEpisodes().isEmpty() ? totalRating / getEpisodes().size() : 0.0;
    }

    public static Season findById(String id) {
        return Series.retrieveSeries().stream()
                .flatMap(series -> series.getSeasons().stream())
                .filter(season -> season.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}