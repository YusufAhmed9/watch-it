package WatChill.UserWatchRecord;

import WatChill.Content.Content;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Episode;
import WatChill.Content.Series.Series;
import WatChill.Crew.Crew;
import WatChill.Review.Review;
import WatChill.Content.WatchableContent;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

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
    public static ArrayList<UserWatchRecord> getUserWatchRecord(String userId) {
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
    public void addWatchableContentRating(WatchableContent content, String userID, Review review) {
        records.add(new UserWatchRecord(userID, review, content));
    }

    // Method to recommends content to user
    public static ArrayList<Content> recommendWatchableContent(String userId) {
        ArrayList<Content> userWatchedContent = new ArrayList<>();
        for (UserWatchRecord userRecord : getUserWatchRecord(userId)) {
            if (userRecord.watchedContent instanceof Movie) {
                userWatchedContent.add((Movie) userRecord.watchedContent);
            } else {
                userWatchedContent.add(Series.findById(((Episode) userRecord.watchedContent).getSeriesId()));
            }
        }
        String preferredGenre = getPreferredGenre(userWatchedContent);
        Crew preferredCrew = getPreferredCrew(userWatchedContent);
        ArrayList<Content>preferredContent = new ArrayList<>();
        for(Content content : Movie.retrieveMovies()){
            if(content.getGenres().contains(preferredGenre) || content.getCrews().contains(preferredCrew)){
                preferredContent.add(content);
            }
        }
        for(Content content : Series.retrieveSeries()){
            if(content.getGenres().contains(preferredGenre) || content.getCrews().contains(preferredCrew)){
                preferredContent.add(content);
            }
        }
        return preferredContent;
    }

    private static Crew getPreferredCrew(ArrayList<Content> preferredContent) {
        HashMap<Crew, Integer> crewAppearances = new HashMap<>();
        int maxAppearances = 0;
        Crew preferredCrew = null;
        // Count the appearances of each crew
        for (Content content : preferredContent) {
            for (Crew crew : content.getCrews()) {
                crewAppearances.put(crew, crewAppearances.getOrDefault(crew, 0) + 1);
                if (crewAppearances.get(crew) > maxAppearances) {
                    preferredCrew = crew;
                    maxAppearances = crewAppearances.get(crew);
                }
            }
        }
        return preferredCrew;
    }

    private static String getPreferredGenre(ArrayList<Content> preferredContent) {
        HashMap<String, Integer> genreAppearances = new HashMap<>();
        int maxAppearances = 0;
        String preferredGenre = null;
        // Count the appearances of each crew
        for (Content content : preferredContent) {
            for (String genre : content.getGenres()) {
                genreAppearances.put(genre, genreAppearances.getOrDefault(genre, 0) + 1);
                if (genreAppearances.get(genre) > maxAppearances) {
                    preferredGenre = genre;
                    maxAppearances = genreAppearances.get(genre);
                }
            }
        }


        return preferredGenre;
    }

}