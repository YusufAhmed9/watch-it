package WatChill.Content.Movie;

import WatChill.Cast.Cast;
import WatChill.Content.Content;
import WatChill.Director.Director;
import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import WatChill.Content.WatchableContent;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.*;

// Specifying Jackson properties: make fields visible and hide getters
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Movie extends Content implements WatchableContent {

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
            @JsonProperty("casts") ArrayList<Cast> casts,
            @JsonProperty("director") ArrayList<Director> directors,
            @JsonProperty("country") String country,
            @JsonProperty("budget") double budget,
            @JsonProperty("revenue") double revenue,
            @JsonProperty("poster") String poster,
            @JsonProperty("viewsCount") int viewsCount,
            @JsonProperty("description") String description
    ) {
        super(id, title, releaseDate, description, languages, country, genres, directors, casts, poster, budget, revenue);
        this.duration = duration;
        this.viewsCount = viewsCount;
    }

    public Float getDuration() {
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
    public void addMovie() {
        int index = findIndex();
        if (index == -1) {
            moviesFile.add(this); // Add movie if not found
        } else {
            moviesFile.set(index, this); // Update movie if found
        }
    }

    // Method to save all movies to the JSON file
    public static void storeAllMovies() {
        JsonWriter.writeJsonToFile("./src/main/data/Movies.json", moviesFile);
    }

    // Method to find movies by genre
    public static ArrayList<Movie> findByGenre(String genre) {
        ArrayList<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : retrieveMovies()) {
            if (movie.getGenres().contains(genre)) {
                filteredMovies.add(movie); // Add movie if it matches the genre
            }
        }
        return filteredMovies;
    }

    // Method to get the total views of a movie
    private double getMovieViews() {
        return this.viewsCount; // Return the views count for the movie
    }

    // Comparator for sorting movies based on views in descending order
    static class MovieComparator implements Comparator<Movie> {
        @Override
        public int compare(Movie m1, Movie m2) {
            return Integer.compare(m2.getViewsCount(), m1.getViewsCount()); // Sort in descending order by views
        }
    }

    // Method to get the top 10 most-viewed movies
    public static ArrayList<Movie> getTopTen() {
        ArrayList<Movie> sortedMovies = new ArrayList<>(retrieveMovies());
        sortedMovies.sort(new MovieComparator()); // Sort movies by views
        return new ArrayList<>(sortedMovies.subList(0, Math.min(10, sortedMovies.size()))); // Return the top 10 movies
    }

    @Override
    public void updateRating() {

    }

    public static ArrayList<Movie> searchMoviesByName(String title) {
        ArrayList<Movie> filteredMovies = new ArrayList<>(retrieveMovies());
        // Filter directors whose names do not contain the search query
        filteredMovies.removeIf(movie -> !movie.getTitle().toLowerCase().contains(title.replaceAll(" ", "").toLowerCase()));
        return filteredMovies;
    }
}
