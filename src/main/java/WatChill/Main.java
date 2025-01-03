package WatChill;

import WatChill.FileHandling.ReadAllFiles;
import WatChill.FileHandling.WriteAllFiles;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        String css = getClass().getResource("/WatChill/Style/Main.css").toExternalForm();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Home/home.fxml"));
        Parent root = loader.load();
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