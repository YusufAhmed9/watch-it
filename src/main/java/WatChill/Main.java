package WatChill;

import WatChill.Content.Series.SeriesController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            Image appIcon = new Image("AppLogo.png");

            stage.getIcons().add(appIcon);
            stage.setTitle("WatChill");
            Scene scene = redirectSeriesPage("1");
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Scene redirectSeriesPage(String seriesId) throws IOException {
        String css = getClass().getResource("/WatChill/Series/Series.css").toExternalForm();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Series/Series.fxml"));
        Parent root = loader.load();
        SeriesController seriesController = loader.getController();
        seriesController.build(seriesId);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        return scene;

    }

    public static void main(String[] args) {
//        Episode episode = new Episode("1", "Amoot", 25, LocalDate.now());
//        ArrayList<Episode>episodes = new ArrayList<>();
//        episodes.add(episode);
//        Season season = new Season("Season 1", "Greatest season ever", LocalDate.now(), episodes);
//        ArrayList<Season>seasons = new ArrayList<>();
//        seasons.add(season);
//        Series series = new Series("1", "Attack On Titan", LocalDate.now(), seasons, "Greatest series ever", new ArrayList<>(), "Japan", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//        series.addSeries();
//        Series.storeAllSeries();
        launch(args);
    }
}