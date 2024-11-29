package WatChill.Series;

import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import WatChill.Review.Review;

import java.util.ArrayList;
import java.util.Date;

public class Series {
    private String id;
    private String title;
    private Date releaseDate;
    private ArrayList<Season> seasons;
    private ArrayList<Review>reviews;
    private String description;
    private ArrayList<String> languages;
    private String country;
    private ArrayList<String> genres;
    private static ArrayList<Series> series;

    public Series(String id, String title, Date releaseDate, ArrayList<Season> seasons, ArrayList<Review> reviews, String description, ArrayList<String> languages, String country, ArrayList<String> genres) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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

    public int getViewsCount(){
        //Initialize total views count to zero
        int totalViewsCount = 0;
        for(Season season : getSeasons()){
            //Sums all seasons of the series view count
            totalViewsCount += season.getViewsCount();
        }
        return totalViewsCount;
    }

    private static void setSeries(){
        if(series == null)//If series is not read yet
            series = JsonReader.readJsonFile("./src/main/data/Series.json", Series.class);
    }

    private int findSeriesIndex(){
        setSeries();
        for(int i = 0; i < series.size(); i++){
            //Comparing every series id in the database to current id
            if(series.get(i).getId().equals(getId()))//If true then it is already stored
                return i;
        }
        //if -1 then it was not found in database
        return -1;
    }

    public void addCurrentSeriesToList(){
        if(findSeriesIndex() == -1)//If it wasn't found in database
            series.add(this);//add to database
        else //Was found "There's a chance it's not updated"
            series.set(findSeriesIndex(), this);//Update it's value in database
    }

    public static void storeSeries() {
        JsonWriter.writeJsonToFile("./src/main/data/Series.json", series);
    }
}