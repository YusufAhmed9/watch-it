package WatChill.Content;

import WatChill.Cast.Cast;
import WatChill.Content.Series.Season;
import WatChill.Content.Series.Series;
import WatChill.Director.Director;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class Content {
    protected String id;
    protected String title;
    protected LocalDate releaseDate;
    protected String description;
    protected ArrayList<String> languages;
    protected String country;
    protected ArrayList<String> genres;
    protected ArrayList<Director> directors;
    protected ArrayList<Cast> casts;
    protected String poster;
    protected double budget;
    protected double revenue;
    public static HashSet<String> allContentLanguages = new HashSet<>();
    public static HashSet<String> allContentGenres = new HashSet<>();

    protected Content(String id, String title, LocalDate releaseDate, String description, ArrayList<String> languages, String country, ArrayList<String> genres, ArrayList<Director> directors, ArrayList<Cast> casts, String poster, double budget, double revenue) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.description = description;
        this.languages = languages;
        this.country = country;
        this.genres = genres;
        this.directors = directors;
        this.casts = casts;
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

    public ArrayList<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<Director> directors) {
        this.directors = directors;
    }

    public void addDirector(Director director) {
        directors.add(director);
    }

    public ArrayList<Cast> getCasts() {
        return casts;
    }

    public void setCasts(ArrayList<Cast> casts) {
        this.casts = casts;
    }

    public void addCast(Cast cast) {
        casts.add(cast);
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
    public abstract int getViewsCount();
    protected abstract int findIndex();
}
