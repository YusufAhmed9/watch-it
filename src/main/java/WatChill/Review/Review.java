package WatChill.Review;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {
    private String content;
    private int rating;
    @JsonCreator
    public Review(
            @JsonProperty("content") String content,
            @JsonProperty("rating") int rating) {
        this.content = content;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
