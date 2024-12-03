package WatChill.Series;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SeriesController {
    private Series currentSeries;
    @FXML
    private MenuButton seasonsMenu;
    @FXML
    private Label seriesTitle;
    @FXML
    private Text description;
    @FXML
    private ImageView seriesPoster;

    private void initializeSeries(String seriesId) {
        this.currentSeries = Series.getSeriesById(seriesId);
        seriesTitle.setText(currentSeries.getTitle());
        for (Season season : currentSeries.getSeasons()) {
            MenuItem currentSeriesSeason = new MenuItem(season.getTitle());
            currentSeriesSeason.setOnAction(e -> seasonsMenu.setText(currentSeriesSeason.getText()));
            seasonsMenu.getItems().add(currentSeriesSeason);
        }
        seasonsMenu.setText(seasonsMenu.getItems().getFirst().getText());
        description.setText(currentSeries.getDescription());
        Image seriesImage = new Image(getClass().getResource(currentSeries.getPoster()).toExternalForm());
        seriesPoster.setImage(seriesImage);
    }

    public void displaySeasonEpisodes(MouseEvent event){

    }

    public void build(String seriesId) {
        initializeSeries(seriesId);
    }

}
