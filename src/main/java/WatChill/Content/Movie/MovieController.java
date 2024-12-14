package WatChill.Content.Movie;

import WatChill.Crew.Cast.Cast;
import WatChill.Crew.Crew;
import WatChill.Crew.Director.Director;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class MovieController {
    private Movie movie;
    @FXML
    private ImageView moviePoster;
    @FXML
    private Label movieTitle;
    @FXML
    private Text movieDescription;
    @FXML
    private HBox genresBox;
    @FXML
    private HBox ratingBox;
    @FXML
    private HBox castsBox;
    @FXML
    private HBox directorsBox;

    public void initializePage(String movieId) {
        movie = Movie.findById(movieId);
        moviePoster.setImage(new Image(getClass().getResource(movie.getPoster()).toExternalForm()));
        movieTitle.setText(movie.getTitle());
        movieDescription.setText(movie.getDescription());
        addGenres();
        addRating();
        addCast();
        addDirectors();
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

    public void addRating() {
        ratingBox.getChildren().clear();
        int star = 1;
        for (; star <= Math.floor(movie.getRating()); star++) {
            ImageView starImage = new ImageView(getClass().getResource("/WatChill/Content/Star 5 (Stroke).png").toExternalForm());
            ratingBox.getChildren().add(starImage);
        }
        for (; star <= 5; star++) {
            ImageView starImage = new ImageView(getClass().getResource("/WatChill/Content/Star 5.png").toExternalForm());
            ratingBox.getChildren().add(starImage);
        }
        Label rating = new Label(((Double) movie.getRating()).toString() + "/5");
        rating.getStyleClass().add("rating-text");
        ratingBox.getChildren().add(rating);
    }

    public void addCast() {
        castsBox.getChildren().clear();
        for (Crew crew : movie.getCrews()) {
            if (crew instanceof Cast) {
                VBox castBox = new VBox();
                ImageView castImage = new ImageView(crew.getPicture());
                castBox.getChildren().add(castImage);
                Label castName = new Label(crew.getFirstName() + ' ' + crew.getLastName());
                castName.getStyleClass().add("cast-label");
                castBox.getChildren().add(castName);
                castBox.getStyleClass().add("cast-box");
                castsBox.getChildren().add(castBox);
            }
        }
    }

    public void addDirectors() {
        directorsBox.getChildren().clear();
        for (Crew crew : movie.getCrews()) {
            if (crew instanceof Director) {
                Label directorName = new Label(crew.getFirstName() + ' ' + crew.getLastName());
                directorName.getStyleClass().add("director-name");
                directorsBox.getChildren().add(directorName);
                Label comma = new Label(", ");
                comma.getStyleClass().add("director-name");
                directorsBox.getChildren().add(comma);
            }
        }
        if (!directorsBox.getChildren().isEmpty()) {
            directorsBox.getChildren().removeLast();
        }
    }

    public void build(String movieId) {
        initializePage(movieId);
    }
}
