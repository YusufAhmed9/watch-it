package WatChill.Content.Series;

import WatChill.Review.Review;
import WatChill.UserManagement.Customer;
import WatChill.UserManagement.User;
import WatChill.UserWatchRecord.UserWatchRecord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

public class EpisodeController {
    private Episode episode;
    private Series series;
    private Season season;
    @FXML
    BorderPane episodeBorderPane;
    @FXML
    ImageView playEpisodeButton;

    @FXML
    private MenuButton seasonsMenu;
    @FXML
    private ImageView seriesPoster;
    @FXML
    private FlowPane episodesPane;
    @FXML
    private Label seriesTitle;
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
    StackPane ratingBox;
    Parent root;

    Stage stage;

    Scene scene;

    public void initializePage(String episodeId) {
        episode = Episode.findById(episodeId);
        season = Season.findById(episode.getSeasonId());
        series = Series.findById(season.getSeriesId());
        seriesTitle.setText(series.getTitle());
        initializeSeasonMenu();
        initializeEpisodePane();
        addGenres();
        addLanguages();
        country.setText(series.getCountry());
        budgetLabel.setText(((Double) series.getBudget()).toString());
        ratingLabel.setText(((Double) series.getRating()).toString());
        releaseDate.setText(((Integer) series.getReleaseDate().getYear()).toString());
        seriesPoster.setImage(new Image(getClass().getResource(series.getPoster()).toExternalForm()));
        initializeRating();
        initializeHeader();
        playEpisodeButton.setOnMouseClicked(_ -> {
            if (User.getCurrentUser() instanceof Customer) {
                ((Customer) User.getCurrentUser()).watchContent(episode, new Review("", 0));
            }
        });
    }

    public void initializeSeasonMenu() {
        for (Season season : series.getSeasons()) {
            MenuItem currentSeriesSeason = new MenuItem(season.getTitle());
            currentSeriesSeason.setOnAction(e -> {
                seasonsMenu.setText(currentSeriesSeason.getText());
                initializeEpisodePane();
            });
            seasonsMenu.getItems().add(currentSeriesSeason);
        }
        seasonsMenu.setText(seasonsMenu.getItems().getFirst().getText());
        Image seriesImage = new Image(getClass().getResource(series.getPoster()).toExternalForm());
        seriesPoster.setImage(seriesImage);
    }

    public void initializeEpisodePane() {
        episodesPane.getChildren().clear();
        for (Season season : series.getSeasons()) {
            if (season.getTitle().equals(seasonsMenu.getText())) {
                for (int episode = 1; episode <= season.getEpisodes().size(); episode++) {
                    Button button = new Button();
                    button.setText(((Integer) episode).toString());
                    button.getStyleClass().add("episode-button");
                    button.setPrefWidth(40);
                    button.setPrefHeight(40);
                    episodesPane.getChildren().add(button);
                }
            }
        }

    }

    public void initializeRating() {

        final int starCount = 5;
        ImageView[] stars = new ImageView[starCount];
        // Create the stars and set their hover effects
        for (int i = 0; i < starCount; i++) {
            ImageView star = new ImageView(new Image(getClass().getResource("/WatChill/Content/Empty star.png").toExternalForm()));
            star.setFitHeight(48); // Adjust size as needed
            star.setFitWidth(48);

            int currentStarIndex = i;

            // Set hover event (on mouse enter)
            star.setOnMouseEntered(event -> {
                fillStars(stars, currentStarIndex);
            });
            star.setOnMouseClicked(event -> {
                fillStars(stars, currentStarIndex);
                if (User.getCurrentUser() instanceof Customer) {
                    ((Customer) User.getCurrentUser()).watchContent(episode, new Review("", currentStarIndex + 1));
                }
                for(int j = 0; j < starCount; j++){
                    stars[j].setOnMouseClicked(null);
                    stars[j].setOnMouseEntered(null);
                    stars[j].setOnMouseExited(null);
                }
            });

            // Set event to reset when the mouse exits any star
            star.setOnMouseExited(event -> {
                resetStars(stars);
            });

            star.setCursor(Cursor.HAND);
            // Add star to the array and the HBox
            stars[i] = star;
            starsBox.getChildren().add(star);
        }

    }

    private void fillStars(ImageView[] stars, int index) {
        for (int i = 0; i <= index; i++) {
            stars[i].setImage(new Image(getClass().getResource("/WatChill/Content/star.png").toExternalForm()));
        }
    }

    // Reset all stars to empty
    private void resetStars(ImageView[] stars) {
        for (ImageView star : stars) {
            star.setImage(new Image(getClass().getResource("/WatChill/Content/Empty star.png").toExternalForm()));
        }
    }

    public void addGenres() {
        genresBox.getChildren().clear();
        for (String genre : series.getGenres()) {
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
        for (String language : series.getLanguages()) {
            Label languageLabel = new Label(language);
            languageLabel.getStyleClass().add("language");
            languagesBox.getChildren().add(languageLabel);
            Label bullet = new Label("•");
            bullet.getStyleClass().add("language");
            languagesBox.getChildren().add(bullet);
        }
        if (!languagesBox.getChildren().isEmpty()) {
            languagesBox.getChildren().removeLast();
        }
        languagesBox.getStyleClass().add("genres-box");
    }

    private void initializeHeader() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Home/header.fxml"));
            Pane header = loader.load();
            episodeBorderPane.setTop(header);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void build(String episodeId) {
        initializePage(episodeId);
    }
}
