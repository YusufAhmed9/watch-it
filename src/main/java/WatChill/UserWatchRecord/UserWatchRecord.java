package WatChill.UserWatchRecord;

import WatChill.Movie.Movie;
import WatChill.Series.Episode;

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
    private ArrayList<WatchRecord<Movie>> movieRecords;
    private ArrayList<WatchRecord<Episode>> episodeRecords;
    private static final String FILE_PATH = "./src/main/data/UserWatchRecords.json";

    // Constructor
    @JsonCreator
    public UserWatchRecord(
            @JsonProperty("userId") String userId,
            @JsonProperty("movieRecords") ArrayList<WatchRecord<Movie>> movieRecords,
            @JsonProperty("episodeRecords") ArrayList<WatchRecord<Episode>> episodeRecords
    ) {
        this.userId = userId;
        this.movieRecords = movieRecords == null ? new ArrayList<>() : movieRecords;
        this.episodeRecords = episodeRecords == null ? new ArrayList<>() : episodeRecords;
    }

    public UserWatchRecord(String userId) {
        this(userId, new ArrayList<>(), new ArrayList<>());
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<WatchRecord<Movie>> getMovieRecords() {
        return movieRecords;
    }
    public void setMovieRecords(ArrayList<WatchRecord<Movie>> movieRecords) {
        this.movieRecords = movieRecords;
    }

    public ArrayList<WatchRecord<Episode>> getEpisodeRecords() {
        return episodeRecords;
    }
    public void setEpisodeRecords(ArrayList<WatchRecord<Episode>> episodeRecords) {
        this.episodeRecords = episodeRecords;
    }
    
    // Nested class to represent a generic watch record
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
    private static class WatchRecord<T> {
        // Attributes
        private T content;
        private LocalDate watchDate;
        private Integer rating;

        // Constructor
        @JsonCreator
        public WatchRecord(
                @JsonProperty("content") T content,
                @JsonProperty("watchDate") LocalDate watchDate,
                @JsonProperty("rating") Integer rating
        ) {
            this.content = content;
            this.watchDate = watchDate;
            this.rating = rating;
        }

        // Getters and Setters
        public T getContent() {
            return content;
        }
        public void setContent(T content) {
            this.content = content;
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

    // Methods to handle movies
    public void addMovieRecord(Movie movie, LocalDate watchDate, Integer rating) {
        movieRecords.add(new WatchRecord<>(movie, watchDate, rating));
        saveToFile();
    }

    public void displayWatchedMovies() {
        if (movieRecords.isEmpty()) {
            System.out.println("No movies watched yet.");
            return;
        }
        System.out.println("Movies watched by user " + userId + ":");
        for (WatchRecord<Movie> record : movieRecords) {
            Movie movie = record.content;
            System.out.println("Title: " + movie.getTitle());
            System.out.println("Date Watched: " + record.watchDate);
            System.out.println("Rating: " + (record.rating != null ? record.rating : "Not Rated"));
        }
    }

    public void updateMovieRating(Movie movie, Integer newRating) {
        for (WatchRecord<Movie> record : movieRecords) {
            if (record.content.getId().equals(movie.getId())) {
                record.rating = newRating;
                saveToFile();
                System.out.println("Rating updated for movie: " + movie.getTitle());
                return;
            }
        }
        System.out.println("Movie not found in watch records.");
    }

    // Methods to handle episodes
    public void addEpisodeRecord(Episode episode, LocalDate watchDate, Integer rating) {
        episodeRecords.add(new WatchRecord<>(episode, watchDate, rating));
        saveToFile();
    }

    public void displayWatchedEpisodes() {
        if (episodeRecords.isEmpty()) {
            System.out.println("No episodes watched yet.");
            return;
        }
        System.out.println("Episodes watched by user " + userId + ":");
        for (WatchRecord<Episode> record : episodeRecords) {
            Episode episode = record.content;
            System.out.println("Title: " + episode.getTitle());
            System.out.println("Date Watched: " + record.watchDate);
            System.out.println("Rating: " + (record.rating != null ? record.rating : "Not Rated"));
        }
    }

    public void updateEpisodeRating(Episode episode, Integer newRating) {
        for (WatchRecord<Episode> record : episodeRecords) {
            if (record.content.getId().equals(episode.getId())) {
                record.rating = newRating;
                saveToFile();
                System.out.println("Rating updated for episode: " + episode.getTitle());
                return;
            }
        }
        System.out.println("Episode not found in watch records.");
    }

    // JSON File Handling

    // Save all user watch records to a JSON file
    private void saveToFile() {
        ArrayList<UserWatchRecord> allRecords = retrieveAllRecords();
        // Update current user record or add a new one
        boolean updated = false;
        for (int i = 0; i < allRecords.size(); i++) {
            if (allRecords.get(i).userId.equals(this.userId)) {
                allRecords.set(i, this);
                updated = true;
                break;
            }
        }
        if (!updated) {
            allRecords.add(this);
        }
        JsonWriter.writeJsonToFile(FILE_PATH, allRecords);
    }

    // Retrieve all user watch records from the JSON file
    public static ArrayList<UserWatchRecord> retrieveAllRecords() {
        return JsonReader.readJsonFile(FILE_PATH, UserWatchRecord.class);
    }

    // Retrieve a specific user's watch record
    public static UserWatchRecord getUserRecord(String userId) {
        for (UserWatchRecord record : retrieveAllRecords()) {
            if (record.userId.equals(userId)) {
                return record;
            }
        }
        return new UserWatchRecord(userId); // Return a new record if none found
    }
}
