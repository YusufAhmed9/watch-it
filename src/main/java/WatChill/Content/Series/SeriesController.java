package WatChill.Content.Series;

import WatChill.Crew.Cast.Cast;
import WatChill.Crew.Crew;
import WatChill.Crew.CrewController;
import WatChill.Crew.Director.Director;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private VBox episodesContainer;
    @FXML
    private HBox genresBox;
    @FXML
    private HBox ratingBox;
    @FXML
    private HBox castsBox;
    @FXML
    private HBox directorsBox;

    private Parent root;
    private Scene scene;
    private Stage stage;


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
        addCrew();
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
        episodesContainer.getChildren().clear();
        for (Season season : currentSeries.getSeasons()) {
            if (seasonsMenu.getText().equals(season.getTitle())) {
                for (Episode episode : season.getEpisodes()) {
                    HBox episodeContainer = new HBox(); // HBox to hold image and labels
                    ImageView episodeImageView = new ImageView(getClass().getResource(episode.getPoster()).toExternalForm());
                    // Set dimensions for the image
                    double imageWidth = 270;  // Set the width of the image
                    double imageHeight = 172; // Set the height of the image
                    episodeImageView.setFitWidth(imageWidth);
                    episodeImageView.setFitHeight(imageHeight);

                    // Create a rectangle with rounded top-left and bottom-left corners
                    Rectangle clip = new Rectangle(imageWidth, imageHeight);
                    clip.setArcWidth(50);  // The arc width for the left corners
                    clip.setArcHeight(50); // The arc height for the left corners
                    episodeImageView.setClip(clip);  // Apply the clipping to the image

                    episodeImageView.getStyleClass().add("episode-poster");


                    // VBox to hold the title, release date, and seasonDescription
                    VBox episodeLabels = new VBox();

                    Label episodeTitle = new Label("S" + (currentSeries.getSeasons().indexOf(season) + 1) + " E" + (season.getEpisodes().indexOf(episode) + 1) + " - " + episode.getTitle());
                    episodeTitle.getStyleClass().add("episode-title");

                    Label episodeReleaseDate = new Label(episode.getReleaseDate().toString());
                    episodeReleaseDate.getStyleClass().add("episode-release-date");

                    Label episodeDescription = new Label(episode.getDescription());
                    episodeDescription.getStyleClass().add("episode-description");
                    episodeDescription.setWrapText(true);
                    // Add labels to the VBox
                    episodeLabels.getChildren().addAll(episodeTitle, episodeReleaseDate, episodeDescription);
                    episodeLabels.getStyleClass().add("episode-labels");
                    // Add the image and the labels to the episodeContainer
                    episodeContainer.getChildren().addAll(episodeImageView, episodeLabels);
                    // Add episode container to the episodes VBox
                    episodeContainer.getStyleClass().add("episode-container");

                    HBox indentation = new HBox();
                    HBox.setHgrow(indentation, Priority.ALWAYS);
                    episodeContainer.getChildren().add(indentation);
                    ImageView playButton = new ImageView(getClass().getResource("/WatChill/Content/Series/media/play-circle.png").toExternalForm());
                    playButton.setOnMouseClicked(_ -> redirectToEpisodePage(episode.getId()));
                    playButton.setStyle("-fx-cursor: hand; -fx-padding: 0 10 0 0");
                    episodeContainer.getChildren().add(playButton);

                    episodesContainer.getChildren().add(episodeContainer);
                }
            }
        }

    }

    public void addCrew() {
        castsBox.getChildren().clear();
        directorsBox.getChildren().clear();
        for (Crew crew : currentSeries.getCrews()) {
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
//
//    public void addDirectors() {
//        directorsBox.getChildren().clear();
//        for (Crew crew : currentSeries.getCrews()) {
//            if (crew instanceof Director) {
//                ImageView castImage = new ImageView(crew.getPicture());
//                double size = 100; // Set desired image size
//                castImage.setFitWidth(size);
//                castImage.setFitHeight(size);
//
//                // Create a circular clip using a rectangle with rounded corners
//                Rectangle clip = new Rectangle(size, size);
//                clip.setArcWidth(size);
//                clip.setArcHeight(size);
//                castImage.setClip(clip);
//                Label directorName = new Label(crew.getFirstName() + ' ' + crew.getLastName());
//                directorName.getStyleClass().add("cast-label");
//                directorName.setOnMouseClicked(_ -> redirectToCrewPage(crew.getId()));
//                directorsBox.getChildren().add(directorName);
//                Label bullet = new Label("•");
//                bullet.getStyleClass().add("director-name");
//                directorsBox.getChildren().add(bullet);
//            }
//        }
//        if (!directorsBox.getChildren().isEmpty()) {
//            directorsBox.getChildren().removeLast();
//        }
//    }

    public void redirectToEpisodePage(String episodeId) {
        try {
            String css = getClass().getResource("/WatChill/style/Episode.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Series/Episode.fxml"));
            root = loader.load();
            EpisodeController episodeController = loader.getController();
            episodeController.build(episodeId);
            stage = (Stage) episodesContainer.getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
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
//        double newHValue = Math.min(trendingSeriesScrollPane.getHvalue() + 0.4, 1); // Scroll right
//        trendingSeriesScrollPane.setHvalue(newHValue);
    }

    public void scrollLeft(MouseEvent event) {
//        double newHValue = Math.min(trendingSeriesScrollPane.getHvalue() - 0.4, 1); // Scroll right
//        trendingSeriesScrollPane.setHvalue(newHValue);
    }

    public void build(String seriesId) {
        initializeSeries(seriesId);
    }

}
