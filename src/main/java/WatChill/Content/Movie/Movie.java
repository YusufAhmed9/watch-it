package WatChill.Content.Movie;

import WatChill.Content.Content;
import WatChill.Content.WatchedContent;
import WatChill.Crew.Crew;
import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import WatChill.UserWatchRecord.UserWatchRecord;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.*;

// Specifying Jackson properties: make fields visible and hide getters
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Movie extends Content implements WatchedContent {

    private float duration;
    private static ArrayList<Movie> moviesFile = new ArrayList<>();
    private int viewsCount = 0;

    @JsonCreator
    public Movie(
            @JsonProperty("id") String id,
            @JsonProperty("title") String title,
            @JsonProperty("releaseDate") LocalDate releaseDate,
            @JsonProperty("duration") float duration,
            @JsonProperty("languages") ArrayList<String> languages,
            @JsonProperty("genres") ArrayList<String> genres,
            @JsonProperty("casts") ArrayList<Crew> crews,
            @JsonProperty("country") String country,
            @JsonProperty("budget") double budget,
            @JsonProperty("revenue") double revenue,
            @JsonProperty("poster") String poster,
            @JsonProperty("viewsCount") int viewsCount,
            @JsonProperty("description") String description
    ) {
        super(id, title, releaseDate, description, languages, country, genres, crews, poster, budget, revenue);
        this.duration = duration;
        this.viewsCount = viewsCount;
    }

    public Movie(String title, LocalDate releaseDate, String description, ArrayList<String> languages, String country, ArrayList<String> genres, String poster, double budget, double revenue, float duration, int viewsCount) {
        super(title, releaseDate, description, languages, country, genres, poster, budget, revenue);
        this.duration = duration;
        this.viewsCount = viewsCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    // Method to get the total views count for the movie
    @Override
    public int getViewsCount() {
        return this.viewsCount;
    }

    // Load the list of movies from a JSON file if not loaded already
    public static ArrayList<Movie> retrieveMovies() {
        if (moviesFile.isEmpty()) {
            moviesFile = JsonReader.readJsonFile("./src/main/data/Movies.json", Movie.class);
        }
        return moviesFile;
    }

    // Method to search for a movie by its ID
    @Override
    protected int findIndex() {
        for (int i = 0; i < moviesFile.size(); i++) {
            if (moviesFile.get(i).getId().equals(getId())) {
                return i; // Movie found
            }
        }
        return -1; // Movie not found
    }

    // Method to add a new movie or update an existing one
    @Override
    public void save() {
        int index = findIndex();
        if (index == -1) {
            moviesFile.add(this); // Add movie if not found
        } else {
            update(); // Update movie if found
        }
    }

    @Override
    public void update() {
        moviesFile.set(findIndex(), this);//Update it's value in database
    }

    @Override
    public void delete() {
        moviesFile.remove(this);
    }

    // Method to save all movies to the JSON file
    public static void storeAllMovies() {
        JsonWriter.writeJsonToFile("./src/main/data/Movies.json", moviesFile);
    }

    // Method to find movies by genre


    // Method to get the top 10 most-viewed movies
    public static ArrayList<Movie> getTopTen() {
        ArrayList<Movie> sortedMovies = new ArrayList<>(retrieveMovies());
        sortedMovies.sort(Comparator.comparing(Movie::getViewsCount)); // Sort movies by views
        return new ArrayList<>(sortedMovies.subList(0, Math.min(10, sortedMovies.size()))); // Return the top 10 movies
    }

    public static Movie findById(String id) {
        for (Movie movie : retrieveMovies()) {
            if (movie.getId().equals(id)) {//A series with the given id is found
                return movie;
            }
        }
        //No series with given id is found
        return null;
    }

    public static ArrayList<Movie> searchByTitle(String title) {
        ArrayList<Movie> filteredMovies = new ArrayList<>(retrieveMovies());
        // Filter series whose titles do not contain the search query
        filteredMovies.removeIf(movie -> !movie.getTitle().toLowerCase().contains(title.strip().toLowerCase()));
        return filteredMovies;
    }
}
