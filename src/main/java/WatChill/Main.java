package WatChill;

import WatChill.Content.Series.Episode;
import WatChill.Content.Series.EpisodeController;
import WatChill.Content.Series.SeriesController;
import WatChill.Subscription.Subscription;
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
        String css = getClass().getResource("/WatChill/Style/Episode.css").toExternalForm();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Series/Episode.fxml"));
        System.out.println(loader.getLocation());
        Parent root = loader.load();
        EpisodeController seriesController = loader.getController();
        seriesController.build("1");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}