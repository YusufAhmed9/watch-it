package WatChill.Content.Movie;

import WatChill.Content.Series.Episode;
import WatChill.Content.Series.Season;
import WatChill.Content.Series.Series;
import WatChill.Review.Review;
import WatChill.UserManagement.Customer;
import WatChill.UserManagement.User;
import WatChill.UserWatchRecord.UserWatchRecord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MoviePlayerController {
    private Movie movie;
    @FXML
    private ImageView moviePoster;
    @FXML
    private Label movieTitle;
    @FXML
    private HBox genresBox;
    @FXML
    private HBox languagesBox;
    @FXML
    private HBox starsBox;
    @FXML
    private Label releaseDate;
    @FXML
    private Label country;
    @FXML
    private Label budgetLabel;
    @FXML
    private Label ratingLabel;
    @FXML
    ImageView playMovieButton;
    @FXML
    BorderPane moviePlayerBorderPane;
    int previousRating = 0;

    public void initializePage(String movieId) {
        movie = Movie.findById(movieId);
        movieTitle.setText(movie.getTitle());
        addGenres();
        addLanguages();
        releaseDate.setText(((Integer) movie.getReleaseDate().getYear()).toString());
        country.setText(movie.getCountry());
        Image seriesImage = new Image(getClass().getResource(movie.getPoster()).toExternalForm());
        moviePoster.setImage(seriesImage);
        budgetLabel.setText(((Double) movie.getBudget()).toString());
        ratingLabel.setText(((Double) movie.getRating()).toString());
        playMovieButton.setOnMouseClicked(_ -> {
            if (User.getCurrentUser() instanceof Customer) {
                Customer customer = (Customer) User.getCurrentUser();
                if (!movieWatched(customer.getId())) {
                    customer.watchContent(movie, new Review("", 0));
                }
            }
        });
        playMovieButton.setCursor(Cursor.HAND);
        initializeRating();
        initializeHeader();
    }

    private boolean movieWatched(String userId) {
        for (UserWatchRecord userWatchRecord : UserWatchRecord.getUserWatchRecord(userId)) {
            if (userWatchRecord.getWatchedContent().getId().equals(movie.getId())) {
                previousRating = userWatchRecord.getReview().getRating();
                return true;
            }
        }
        return false;
    }

    public void addGenres() {
        genresBox.getChildren().clear();
        for (String genre : movie.getGenres()) {
            Label genreLabel = new Label(genre);
            genreLabel.getStyleClass().add("genre");
            genresBox.getChildren().add(genreLabel);
            Label bullet = new Label("•");
            bullet.getStyleClass().add("genre");
            genresBox.getChildren().add(bullet);
        }
        genresBox.getChildren().removeLast();
        genresBox.getStyleClass().add("genres-box");
    }

    public void addLanguages() {
        languagesBox.getChildren().clear();
        for (String genre : movie.getLanguages()) {
            Label genreLabel = new Label(genre);
            genreLabel.getStyleClass().add("language");
            languagesBox.getChildren().add(genreLabel);
            Label bullet = new Label("•");
            bullet.getStyleClass().add("language");
            languagesBox.getChildren().add(bullet);
        }
        languagesBox.getChildren().removeLast();
        languagesBox.getStyleClass().add("genres-box");
    }

    public void initializeRating() {
        final int starCount = 5;
        ImageView[] stars = new ImageView[starCount];
        for (int i = 0; i < starCount; i++) {
            ImageView star = new ImageView(new Image(getClass().getResource("/WatChill/Content/Empty star.png").toExternalForm()));
            star.setFitHeight(48); // Adjust size as needed
            star.setFitWidth(48);

            stars[i] = star;
            starsBox.getChildren().add(star);
        }
        if (User.getCurrentUser() instanceof Customer) {
            Customer customer = (Customer) User.getCurrentUser();
            if (movieWatched(customer.getId())) {
                fillStars(stars, previousRating);
            } else {
                // Create the stars and set their hover effects
                for (int i = 0; i < starCount; i++) {

                    int currentStarIndex = i;

                    // Set hover event (on mouse enter)
                    stars[i].setOnMouseEntered(event -> {
                        fillStars(stars, currentStarIndex);
                    });
                    stars[i].setOnMouseClicked(event -> {
                        fillStars(stars, currentStarIndex);
                        if (User.getCurrentUser() instanceof Customer) {
                            ((Customer) User.getCurrentUser()).watchContent(movie, new Review("", currentStarIndex + 1));
                        }
                        for (int j = 0; j < starCount; j++) {
                            stars[j].setOnMouseClicked(null);
                            stars[j].setOnMouseEntered(null);
                            stars[j].setOnMouseExited(null);
                        }
                    });

                    // Set event to reset when the mouse exits any star
                    stars[i].setOnMouseExited(event -> {
                        resetStars(stars);
                    });

                    stars[i].setCursor(Cursor.HAND);
                }
            }
        }
    }

    private void fillStars(ImageView[] stars, int index) {
        for (int i = 0; i < index; i++) {
            stars[i].setImage(new Image(getClass().getResource("/WatChill/Content/star.png").toExternalForm()));
        }
    }

    // Reset all stars to empty
    private void resetStars(ImageView[] stars) {
        for (ImageView star : stars) {
            star.setImage(new Image(getClass().getResource("/WatChill/Content/Empty star.png").toExternalForm()));
        }
    }

    private void initializeHeader() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Home/header.fxml"));
            Pane header = loader.load();
            moviePlayerBorderPane.setTop(header);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void build(String movieId) {
        initializePage(movieId);
    }
}
