package WatChill;

import WatChill.Content.Movie.MovieController;
import WatChill.Content.Series.EpisodeController;
import WatChill.Content.Series.SeriesController;
import WatChill.FileHandling.ReadAllFiles;
import WatChill.FileHandling.WriteAllFiles;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {

            Image appIcon = new Image("AppLogo.png");

            stage.getIcons().add(appIcon);
            stage.setTitle("WatChill");
            Scene scene = redirectToHome();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Scene redirectToHome() throws IOException {
        String css = getClass().getResource("/WatChill/Style/Movie.css").toExternalForm();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Movie/Movie.fxml"));
        Parent root = loader.load();
        MovieController seriesController = loader.getController();
        seriesController.build("038a8fe1-2698-4aad-9eb2-df598f9448da");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        return scene;
    }

    public static void main(String[] args) {
        ReadAllFiles.readAllFiles();
        launch(args);
        WriteAllFiles.writeAllFiles();
    }
}