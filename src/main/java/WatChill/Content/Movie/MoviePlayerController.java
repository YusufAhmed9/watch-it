package WatChill.Content.Movie;

import WatChill.Content.Series.Episode;
import WatChill.Content.Series.Season;
import WatChill.Content.Series.Series;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

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
    private Label releaseDate;
    @FXML
    private Label country;
    @FXML
    private Label budgetLabel;
    @FXML
    private Label ratingLabel;

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

    public void build(String movieId) {
        initializePage(movieId);
    }
}
