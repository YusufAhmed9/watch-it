package WatChill.Series;

import WatChill.Review.Review;

import java.util.ArrayList;
import java.util.Date;

public class Season {
    private String title;
    private Date releaseDate;
    private String description;
    private ArrayList<Review> reviews;
    private ArrayList<Episode> episodes;

    public Season(String title, String description, Date releaseDate, ArrayList<Episode> episodes) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.episodes = episodes;
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

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    public void addEpisode(Episode episode){
        episodes.add(episode);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float calculateSeasonRating(){
        //Calculates the average rating for the season
        float seasonAverageRating = 0.0f;
        for(Review review : getReviews()){
            //Sum all the ratings
            seasonAverageRating += review.getRating();
        }
        //Divide them by number of ratings
        seasonAverageRating /= getReviews().size();
        return seasonAverageRating;
    }
}