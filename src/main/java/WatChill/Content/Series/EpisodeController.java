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
    @FXML
    VBox profileList;
    Parent root;

    Stage stage;

    Scene scene;
    int previousRating = 0;

    public void initializePage(String episodeId) {
        episode = Episode.findById(episodeId);
        season = Season.findById(episode.getSeasonId());
        System.out.println(season.getId());
        series = Series.findById(season.getSeriesId());
        seriesTitle.setText(series.getTitle());
        initializeSeasonMenu();
        initializeEpisodePane(season.getId());
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
                Customer customer = (Customer) User.getCurrentUser();
                if (!episodeWatched(customer.getId())) {
                    customer.watchContent(episode, new Review("", 0));
                }
            }
        });
    }

    private boolean episodeWatched(String userId) {
        for (UserWatchRecord userWatchRecord : UserWatchRecord.getUserWatchRecord(userId)) {
            if (userWatchRecord.getWatchedContent().getId().equals(episode.getId())) {
                previousRating = userWatchRecord.getReview().getRating();
                return true;
            }
        }
        return false;
    }

    public void initializeSeasonMenu() {
        seasonsMenu.setText(season.getTitle());
        for (Season season1 : series.getSeasons()) {
            MenuItem currentSeriesSeason = new MenuItem(season1.getTitle());
            currentSeriesSeason.setOnAction(e -> {
                seasonsMenu.setText(currentSeriesSeason.getText());
                initializeEpisodePane(season1.getId());
            });
            seasonsMenu.getItems().add(currentSeriesSeason);
        }
        Image seriesImage = new Image(getClass().getResource(series.getPoster()).toExternalForm());
        seriesPoster.setImage(seriesImage);
    }

    public void redirectToEpisodePage(String episodeId) {
        try {
            String css = getClass().getResource("/WatChill/style/Episode.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Series/Episode.fxml"));
            root = loader.load();
            EpisodeController episodeController = loader.getController();
            episodeController.build(episodeId);
            stage = (Stage) seriesTitle.getScene().getWindow();
            scene = seriesTitle.getScene();
            scene.setRoot(root);
            scene.getStylesheets().clear();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeEpisodePane(String seasonId) {
        episodesPane.getChildren().clear();
        Season season1 = Season.findById(seasonId);
        int counter = 1;
        for (Episode episode : season1.getEpisodes()) {
            Button button = new Button();
            button.setText(((Integer) counter).toString());
            button.getStyleClass().add("episode-button");
            button.setPrefWidth(40);
            button.setPrefHeight(40);
            button.setOnAction(_ -> redirectToEpisodePage(episode.getId()));
            episodesPane.getChildren().add(button);
            counter++;

        }
    }


    public void initializeRating() {
        final int starCount = 6;
        ImageView[] stars = new ImageView[starCount];
        for (int i = 1; i <= 5; i++) {
            ImageView star = new ImageView(new Image(getClass().getResource("/WatChill/Content/Empty star.png").toExternalForm()));
            star.setFitHeight(48); // Adjust size as needed
            star.setFitWidth(48);

            stars[i] = star;
            starsBox.getChildren().add(star);
        }
        if (User.getCurrentUser() instanceof Customer) {
            Customer customer = (Customer) User.getCurrentUser();
            if (episodeWatched(customer.getId())) {
                fillStars(stars, previousRating);
            } else {
                // Create the stars and set their hover effects
                for (int i = 1; i <= 5; i++) {

                    int currentStarIndex = i;

                    // Set hover event (on mouse enter)
                    stars[i].setOnMouseEntered(event -> {
                        fillStars(stars, currentStarIndex);
                    });
                    stars[i].setOnMouseClicked(event -> {
                        fillStars(stars, currentStarIndex);
                        if (User.getCurrentUser() instanceof Customer) {
                            ((Customer) User.getCurrentUser()).watchContent(episode, new Review("", currentStarIndex));
                        }
                        for (int j = 1; j <= 5; j++) {
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
        for (int i = 1; i <= index; i++) {
            stars[i].setImage(new Image(getClass().getResource("/WatChill/Content/star.png").toExternalForm()));
        }
    }

    // Reset all stars to empty
    private void resetStars(ImageView[] stars) {
        for (int i = 1; i <= 5; i++) {
            stars[i].setImage(new Image(getClass().getResource("/WatChill/Content/Empty star.png").toExternalForm()));
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
