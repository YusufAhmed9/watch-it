package WatChill.Content.Movie;

import WatChill.Crew.Cast.Cast;
import WatChill.Crew.Crew;
import WatChill.Crew.CrewController;
import WatChill.Crew.Director.Director;
import WatChill.UserManagement.Customer;
import WatChill.UserManagement.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


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
    @FXML
    private ImageView watchLaterButton;
    @FXML
    private ImageView playButton;
    @FXML
    private ScrollPane castsScrollPane;
    @FXML
    private ScrollPane directorScrollPane;
    private Parent root;
    private Scene scene;
    private Stage stage;

    public void initializePage(String movieId) {
        movie = Movie.findById(movieId);
        moviePoster.setImage(new Image(getClass().getResource(movie.getPoster()).toExternalForm()));
        movieTitle.setText(movie.getTitle());
        movieDescription.setText(movie.getDescription());
        addCrew();
        addGenres();
        addRating();
        playButton.setOnMouseClicked(_->redirectToMoviePlayerPage(movieId));
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

    public void addCrew() {
        castsBox.getChildren().clear();
        directorsBox.getChildren().clear();
        for (Crew crew : movie.getCrews()) {
            VBox castBox = new VBox();
            ImageView castImage = new ImageView(crew.getPicture());
            double size = 100; // Set desired image size
            castImage.setFitWidth(size);
            castImage.setFitHeight(size);

            // Create a circular clip using a rectangle with rounded corners
            Rectangle clip = new Rectangle(size, size);
            clip.setArcWidth(size);
            clip.setArcHeight(size);
            castImage.setClip(clip); // Apply the clip to the ImageView

            castBox.getChildren().add(castImage);
            Label castName = new Label(crew.getFirstName() + ' ' + crew.getLastName());
            castName.getStyleClass().add("cast-label");
            castBox.getChildren().add(castName);
            castBox.getStyleClass().add("cast-box");
            castBox.setOnMouseClicked(_ -> redirectToCrewPage(crew.getId()));
            if (crew instanceof Cast) {
                castsBox.getChildren().add(castBox);
            } else {
                directorsBox.getChildren().add(castBox);

            }
        }
    }

    public void redirectToCrewPage(String crewId) {
        try {
            String css = getClass().getResource("/WatChill/style/Crew.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Crew/Crew.fxml"));
            root = loader.load();
            CrewController crewController = loader.getController();
            crewController.build(crewId);
            if (Crew.findById(crewId) instanceof Cast) {
                stage = (Stage) castsBox.getScene().getWindow();
            } else {
                stage = (Stage) directorsBox.getScene().getWindow();
            }
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scrollRight(MouseEvent event) {
        double newHValue = Math.min(castsScrollPane.getHvalue() + 0.4, 1); // Scroll right
        castsScrollPane.setHvalue(newHValue);
    }

    public void scrollLeft(MouseEvent event) {
        double newHValue = Math.min(castsScrollPane.getHvalue() - 0.4, 1); // Scroll right
        castsScrollPane.setHvalue(newHValue);
    }

    public void addToWatchLater(MouseEvent event) {
        if (User.getCurrentUser() instanceof Customer) {
            if (((Customer) User.getCurrentUser()).findMovieWatchLaterIndex(movie.getId()) == -1) {
                ((Customer) User.getCurrentUser()).addToWatchLater(movie);
                watchLaterButton.setImage(new Image(getClass().getResource("/WatChill/Content/Series/media/minus-circle.png").toExternalForm()));
                System.out.println("added");
            } else {
                ((Customer) User.getCurrentUser()).removeFromWatchLater(movie.getId());
                watchLaterButton.setImage(new Image(getClass().getResource("/WatChill/Content/Series/media/plus.png").toExternalForm()));
                System.out.println("removed");
            }
        }
    }
    public void redirectToMoviePlayerPage(String movieId){
        try {
            String css = getClass().getResource("/WatChill/style/Movie.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Movie/MoviePlayer.fxml"));
            root = loader.load();

            MoviePlayerController moviePlayerController = loader.getController();
            moviePlayerController.build(movieId);

            scene = watchLaterButton.getScene();
            stage = (Stage) scene.getWindow();
            scene.setRoot(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void build(String movieId) {
        initializePage(movieId);
    }
}
