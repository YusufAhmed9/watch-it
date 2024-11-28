package WatChill.Series;

import WatChill.Review.Review;

import java.util.ArrayList;

public class Series {
    private String id;
    private String title;
    private int releaseYear;
    private ArrayList<Season> seasons;
    private ArrayList<Review>reviews;
    private String description;
    private ArrayList<String> languages;
    private String country;
    private ArrayList<String> genres;

    public Series(String id, String title, int releaseYear, ArrayList<Season> seasons, ArrayList<Review> reviews, String description, ArrayList<String> languages, String country, ArrayList<String> genres) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.seasons = seasons;
        this.reviews = reviews;
        this.description = description;
        this.languages = languages;
        this.country = country;
        this.genres = genres;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void addGenre(String genre){
        genres.add(genre);
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

    public void addLanguage(String language){
        languages.add(language);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review){
        reviews.add(review);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public void addSeason(Season season){
        seasons.add(season);
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float calculateSeasonRating(){
        //Calculates season's rating
        float seasonAverageRating = 0.0f;
        for(Review review : getReviews()){
            seasonAverageRating += review.getRating();
        }
        //Divide by number of reviewers
        seasonAverageRating /= getReviews().size();
        return seasonAverageRating;
    }
}