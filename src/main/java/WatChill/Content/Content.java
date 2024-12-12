package WatChill.Content;

import WatChill.Crew.Cast.Cast;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Series;
import WatChill.Crew.Crew;
import WatChill.Crew.Director.Director;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.UUID;

import static WatChill.Content.Movie.Movie.retrieveMovies;
import static WatChill.Content.Series.Series.retrieveSeries;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS, // class name as type information
        property = "@class" // "@class" as the property name
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Series.class, name = "WatChill.Content.Series.Series"),
        @JsonSubTypes.Type(value = Movie.class, name = "WatChill.Content.Movie.Movie")
})
public abstract class Content {
    protected String id;
    protected String title;
    protected LocalDate releaseDate;
    protected String description;
    protected ArrayList<String> languages;
    protected String country;
    protected ArrayList<String> genres;
    ArrayList<Crew> crews;
    protected String poster;
    protected double budget;
    protected double revenue;
    public static HashSet<String> allContentLanguages = new HashSet<>();
    public static HashSet<String> allContentGenres = new HashSet<>();

    protected Content(String id, String title, LocalDate releaseDate, String description, ArrayList<String> languages, String country, ArrayList<String> genres, ArrayList<Crew> crews, String poster, double budget, double revenue) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.description = description;
        this.languages = languages;
        this.country = country;
        this.genres = genres;
        this.crews = crews;
        this.poster = poster;
        this.budget = budget;
        this.revenue = revenue;
        allContentLanguages.addAll(languages);
        allContentGenres.addAll(genres);
    }

    protected Content(String title, LocalDate releaseDate, String description, ArrayList<String> languages, String country, ArrayList<String> genres, String poster, double budget, double revenue) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.releaseDate = releaseDate;
        this.description = description;
        this.languages = languages;
        this.country = country;
        this.genres = genres;
        crews = new ArrayList<>();
        this.poster = poster;
        this.budget = budget;
        this.revenue = revenue;
        allContentLanguages.addAll(languages);
        allContentGenres.addAll(genres);
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public void addLanguage(String language) {
        languages.add(language);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void addGenre(String genre) {
        genres.add(genre);
    }

    public ArrayList<Crew> getCrews() {
        return crews;
    }

    public void setCrews(ArrayList<Crew> crews) {
        this.crews = crews;
    }

    public void addCrew(Crew crew) {
        crews.add(crew);
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public static ArrayList<Content> findByGenre(String genre) {
        ArrayList<Content> filteredContent = new ArrayList<>();
        for (Movie movie : retrieveMovies()) {
            if (movie.getGenres().contains(genre)) {
                filteredContent.add(movie); // Add movie if it matches the genre
            }
        }
        for (Series series : retrieveSeries()) {
            if (series.getGenres().contains(genre)) {
                filteredContent.add(series); // Add movie if it matches the genre
            }
        }
        return filteredContent;
    }

    public static ArrayList<Content> findMostRecent() {
        ArrayList<Content> mostRecentContent = new ArrayList<>();
        mostRecentContent.addAll(retrieveSeries());
        mostRecentContent.addAll(retrieveMovies());
        mostRecentContent.sort(Comparator.comparing(Content::getReleaseDate));
        return mostRecentContent;
    }

    public abstract int getViewsCount();

    protected abstract int findIndex();

    protected abstract void save();

    protected abstract void update();

    protected abstract void delete();

}
