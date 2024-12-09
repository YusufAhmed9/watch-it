package WatChill.UserWatchRecord;

import WatChill.Cast.Cast;
import WatChill.Review.Review;
import WatChill.Content.WatchableContent;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Episode;
import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class UserWatchRecord {

    // Attributes
    private String userId;
    private static final String FILE_PATH = "./src/main/data/UserWatchRecords.json";
    private WatchableContent watchedContent;
    private Review review;
    private static ArrayList<UserWatchRecord> records;

    // Constructor
    @JsonCreator
    public UserWatchRecord(
            @JsonProperty("userId") String userId,
            @JsonProperty("review") Review review,
            @JsonProperty("watchedContent") WatchableContent watchedContent
    ) {
        this.userId = userId;
        this.review = review;
        this.watchedContent = watchedContent;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public WatchableContent getWatchedContent() {
        return watchedContent;
    }

    public void setWatchedContent(WatchableContent watchedContent) {
        this.watchedContent = watchedContent;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    //Methods

    // Method to display watched content
    public static ArrayList<UserWatchRecord>getUserWatchRecord(String userId) {
        // Filter and display only records associated with the specified userId
        ArrayList<UserWatchRecord> userWatchHistory = new ArrayList<>();
        System.out.println("WatchableContent watched by user " + userId + ":");
        for (UserWatchRecord record : records) {
            if (userId.equals(record.getUserId())) {
                userWatchHistory.add(record);
            }
        }
        return userWatchHistory;
    }

    // Method to add content with a review
    public void addWatchableContentRating(WatchableContent content,String userID, Review review) {
        records.add(new UserWatchRecord(userID,review,content));
    }

    // Method to recommends content to user
    public static ArrayList<WatchableContent> recommendWatchableContent(String userId, ArrayList<WatchableContent> allWatchableContents) {

        // Collect genres and cast from watched content
        ArrayList<String> favoriteGenres = new ArrayList<>();
        ArrayList<String> favoriteCast = new ArrayList<>();

        // Loop through records to collect preferences
        for (UserWatchRecord record : records) {
            if (userId.equals(record.WatchedContent().getUserId())) {
                for (String genre : record.getWatchableContent().getGenres()) {
                    if (!favoriteGenres.contains(genre)) {
                        favoriteGenres.add(genre);
                    }
                }
                for (String castMember : record.getWatchableContent().getCast()) {
                    if (!favoriteCast.contains(castMember)) {
                        favoriteCast.add(castMember);
                    }
                }
            }
        }

        // Recommend content based on preferences
        ArrayList<WatchableContent> recommendations = new ArrayList<>();
        for (WatchableContent content : allWatchableContents) {
            // Skip already watched content
            boolean alreadyWatched = false;
            for (WatchedWatchableContent record : records) {
                if (record.getWatchableContent().equals(content)) {
                    alreadyWatched = true;
                    break;
                }
            }
            if (alreadyWatched) {
                continue;
            }

            // Check if the content matches user's favorite genres or cast
            boolean matchesGenres = false;
            boolean matchesCast = false;

            for (String genre : content.getGenres()) {
                if (favoriteGenres.contains(genre)) {
                    matchesGenres = true;
                    break;
                }
            }

            for (String castMember : content.getCast()) {
                if (favoriteCast.contains(castMember)) {
                    matchesCast = true;
                    break;
                }
            }

            // Add content to recommendations if it matches preferences
            if (matchesGenres || matchesCast) {
                recommendations.add(content);
            }
        }

        return recommendations;
    }

}