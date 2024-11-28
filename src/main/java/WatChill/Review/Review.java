package WatChill.Review;

public class Review {
    private String content;
    private int rating;

    public Review(String content, int rating) {
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
