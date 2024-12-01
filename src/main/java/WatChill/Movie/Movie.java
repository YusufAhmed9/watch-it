package WatChill.Movie;

import WatChill.Cast.Cast;
import WatChill.Director.Director;
import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.*;

// Specifying Jackson properties: make fields visible and hide getters
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Movie {

    // Attributes:
    private String id;
    private String title;
    private LocalDate releaseDate;
    private Float duration;
    private ArrayList<String> languages;
    private ArrayList<String> genres;
    private ArrayList<Cast> casts;
    private ArrayList<Director> directors;
    private String country;
    private Double budget;
    private Double revenue;
    private String poster; // URL or path to the image
    private static ArrayList<Movie> moviesFile = new ArrayList<>();
    private int viewsCount = 0;

    // Constructor:
    @JsonCreator
    public Movie(
            @JsonProperty("id") String id,
            @JsonProperty("title") String title,
            @JsonProperty("releaseDate") LocalDate releaseDate,
            @JsonProperty("duration") Float duration,
            @JsonProperty("languages") ArrayList<String> languages,
            @JsonProperty("genres") ArrayList<String> genres,
            @JsonProperty("casts") ArrayList<Cast> casts,
            @JsonProperty("director") ArrayList<Director> directors,
            @JsonProperty("country") String country,
            @JsonProperty("budget") Double budget,
            @JsonProperty("revenue") Double revenue,
            @JsonProperty("poster") String poster,
            @JsonProperty("viewsCount") int viewsCount
    ) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.languages = languages;
        this.genres = genres;
        this.casts = casts;
        this.directors = directors;
        this.country = country;
        this.budget = budget;
        this.revenue = revenue;
        this.poster = poster;
        this.viewsCount = viewsCount;
    }

    // Getters and Setters:
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public Float getDuration() { return duration; }
    public void setDuration(Float duration) { this.duration = duration; }

    public ArrayList<String> getLanguages() { return languages; }
    public void setLanguages(ArrayList<String> languages) { this.languages = languages; }

    public ArrayList<String> getGenres() { return genres; }
    public void setGenres(ArrayList<String> genres) { this.genres = genres; }

    public ArrayList<Cast> getCasts() { return casts; }
    public void setCasts(ArrayList<Cast> casts) { this.casts = casts; }

    public ArrayList<Director> getDirectors() { return directors; }
    public void setDirectors(ArrayList<Director> directors) { this.directors = directors; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }

    public Double getRevenue() { return revenue; }
    public void setRevenue(Double revenue) { this.revenue = revenue; }

    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }

    // Method to get the total views count for the movie
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
    private int findMovieIndex() {
        for (int i = 0; i < moviesFile.size(); i++) {
            if (moviesFile.get(i).getId().equals(this.id)) {
                return i; // Movie found
            }
        }
        return -1; // Movie not found
    }

    // Method to add a new movie or update an existing one
    public void addOrUpdateMovie() {
        int index = findMovieIndex();
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
}
