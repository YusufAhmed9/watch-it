package WatChill.Content.Series;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EpisodeController {
    private Episode episode;
    private Series series;
    private Season season;
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
    private Label releaseDate;
    @FXML
    private Label country;
    @FXML
    private Label budgetLabel;
    @FXML
    private Label ratingLabel;
    public void initializePage(String episodeId) {
        episode = Episode.findById(episodeId);
        season = Season.findById(episode.getSeasonId());
        series = Series.findById(season.getSeriesId());
        seriesTitle.setText(series.getTitle());
        initializeSeasonMenu();
        initializeEpisodePane();
        addGenres();
        releaseDate.setText(((Integer) series.getReleaseDate().getYear()).toString());
        country.setText(series.getCountry());
        Image seriesImage = new Image(getClass().getResource(series.getPoster()).toExternalForm());
        seriesPoster.setImage(seriesImage);
        budgetLabel.setText(((Double)series.getBudget()).toString());
        ratingLabel.setText(((Double)series.getRating()).toString());
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
                    episodesPane.getChildren().add(button);
                }
            }
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

    public void build(String episodeId) {
        initializePage(episodeId);
    }
}
