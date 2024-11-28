package WatChill.Series;

import WatChill.Review.Review;

import java.util.ArrayList;
import java.util.Date;

public class Episode {
    private String id;
    private ArrayList<Review> reviews;
    private String title;
    private int duration;
    private Date releaseDate;

    public Episode(String id, ArrayList<Review> reviews, String title, int duration, Date releaseDate) {
        this.id = id;
        this.reviews = reviews;
        this.title = title;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void addReview(Review review){
        reviews.add(review);
    }

    public float calculateEpisodeRating(){
        //Calculates average rating of an episode
        //A variable to store sum of ratings of an episode
        float episodeAverageRating = 0.0f;
        for(Review review : getReviews()) {
            episodeAverageRating += review.getRating();
        }
        //Divide by number of reviewers
        episodeAverageRating /= getReviews().size();
        return episodeAverageRating;
    }
}