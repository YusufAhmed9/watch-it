package WatChill.UserWatchRecord;

import WatChill.Movie.Movie;
import WatChill.Cast.Cast;
import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class UserWatchRecord {

    // Attributes
    private String userId;
    private Movie movie;
    private LocalDate watchDate;
    private Integer rating; // Rating is optional
    private static ArrayList<UserWatchRecord> records = new ArrayList<>();

    // Constructor
    @JsonCreator
    public UserWatchRecord(
            @JsonProperty("userId") String userId,
            @JsonProperty("movie") Movie movie,
            @JsonProperty("watchDate") LocalDate watchDate,
            @JsonProperty("rating") Integer rating
    ) {
        this.userId = userId;
        this.movie = movie;
        this.watchDate = watchDate;
        this.rating = rating;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDate getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(LocalDate watchDate) {
        this.watchDate = watchDate;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }




}
