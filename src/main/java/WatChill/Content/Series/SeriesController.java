package WatChill.Content.Series;

import WatChill.Content.Series.Episode;
import WatChill.Content.Series.Season;
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
    private void initializeSeries(String seriesId) {
        this.currentSeries = Series.getSeriesById(seriesId);
        seriesTitle.setText(currentSeries.getTitle());
        for (Season season : currentSeries.getSeasons()) {
            MenuItem currentSeriesSeason = new MenuItem(season.getTitle());
            currentSeriesSeason.setOnAction(e -> seasonsMenu.setText(currentSeriesSeason.getText()));
            seasonsMenu.getItems().add(currentSeriesSeason);
        }
        seasonsMenu.setText(seasonsMenu.getItems().getFirst().getText());
        seasonDescription.setText(currentSeries.getDescription());
        Image seriesImage = new Image(getClass().getResource(currentSeries.getPoster()).toExternalForm());
        seriesPoster.setImage(seriesImage);
        displayEpisodes();
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
    public void clearEpisodes(){
        if(!episodesContainer.getChildren().isEmpty()){
            episodesContainer.getChildren().clear();
        }
    }

    public void build(String seriesId) {
        initializeSeries(seriesId);
    }

}
