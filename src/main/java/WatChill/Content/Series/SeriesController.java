package WatChill.Content.Series;

import WatChill.Crew.Cast.Cast;
import WatChill.Crew.Crew;
import WatChill.Crew.Director.Director;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SeriesController {
    private Series currentSeries;
    @FXML
    private MenuButton seasonsMenu;
    @FXML
    private Label seriesTitle;
    @FXML
    private Text seasonDescription;
    @FXML
    private ImageView seriesPoster;
    @FXML
    private Label episodesButton;
    @FXML
    private VBox episodesContainer;
    @FXML
    private HBox genresBox;
    @FXML
    private HBox ratingBox;
    @FXML
    private HBox castsBox;
    @FXML
    private HBox directorsBox;

    private void initializeSeries(String seriesId) {
        this.currentSeries = Series.findById(seriesId);
        seriesTitle.setText(currentSeries.getTitle());
        for (Season season : currentSeries.getSeasons()) {
            MenuItem currentSeriesSeason = new MenuItem(season.getTitle());
            currentSeriesSeason.setOnAction(e -> {
                seasonsMenu.setText(currentSeriesSeason.getText());
                displayEpisodes();
            });
            seasonsMenu.getItems().add(currentSeriesSeason);
        }
        seasonsMenu.setText(seasonsMenu.getItems().getFirst().getText());
        seasonDescription.setText(currentSeries.getDescription());
        Image seriesImage = new Image(getClass().getResource(currentSeries.getPoster()).toExternalForm());
        seriesPoster.setImage(seriesImage);
        addGenres();
        displayEpisodes();
        addCast();
        addDirectors();
        addRating();
    }

    public void addGenres() {
        genresBox.getChildren().clear();
        for (String genre : currentSeries.getGenres()) {
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
        for (; star <= Math.floor(currentSeries.getRating()); star++) {
            ImageView starImage = new ImageView(getClass().getResource("/WatChill/Content/Star 5 (Stroke).png").toExternalForm());
            ratingBox.getChildren().add(starImage);
        }
        for (; star <= 5; star++) {
            ImageView starImage = new ImageView(getClass().getResource("/WatChill/Content/Star 5.png").toExternalForm());
            ratingBox.getChildren().add(starImage);
        }
        Label rating = new Label(((Double) currentSeries.getRating()).toString() + "/5");
        rating.getStyleClass().add("rating-text");
        ratingBox.getChildren().add(rating);
    }

    public void displayEpisodes() {
        clearEpisodes();
        for (Season season : currentSeries.getSeasons()) {
            if (seasonsMenu.getText().equals(season.getTitle())) {
                for (Episode episode : season.getEpisodes()) {
                    HBox episodeContainer = new HBox(); // HBox to hold image and labels
                    ImageView episodeImageView = new ImageView(getClass().getResource(episode.getPoster()).toExternalForm());

                    episodeImageView.getStyleClass().add("episode-poster");

                    // VBox to hold the title, release date, and seasonDescription
                    VBox episodeLabels = new VBox();

                    Label episodeTitle = new Label(episode.getTitle());
                    episodeTitle.getStyleClass().add("episode-title");

                    Label episodeReleaseDate = new Label(episode.getReleaseDate().toString());
                    episodeReleaseDate.getStyleClass().add("episode-release-date");

                    Label episodeDescription = new Label(episode.getDescription());
                    episodeDescription.getStyleClass().add("episode-description");

                    // Add labels to the VBox
                    episodeLabels.getChildren().addAll(episodeTitle, episodeReleaseDate, episodeDescription);
                    episodeLabels.getStyleClass().add("episode-labels");
                    // Add the image and the labels to the episodeContainer
                    episodeContainer.getChildren().addAll(episodeImageView, episodeLabels);

                    // Add episode container to the episodes VBox
                    episodeContainer.getStyleClass().add("episode-container");
                    episodesContainer.getChildren().add(episodeContainer);
                    episodeContainer.getStyleClass().add("episodes-container");
                }
            }
        }

    }

    public void clearEpisodes() {
        episodesContainer.getChildren().clear();
    }

    public void addCast() {
        clearCast();
        for (Crew crew : currentSeries.getCrews()) {
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

    public void clearCast() {
        castsBox.getChildren().clear();
    }

    public void addDirectors() {
        clearDirectors();
        for (Crew crew : currentSeries.getCrews()) {
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

    public void clearDirectors() {
        directorsBox.getChildren().clear();
    }

    public void build(String seriesId) {
        initializeSeries(seriesId);
    }

}
