package WatChill.Content;

import WatChill.Content.Movie.Movie;
import WatChill.Content.Movie.MovieController;
import WatChill.Content.Series.Series;
import WatChill.Content.Series.SeriesController;
import WatChill.UserManagement.Admin;
import WatChill.UserManagement.Customer;
import WatChill.UserManagement.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class AdminCardController {

    @FXML
    private ImageView posterImage;

    @FXML
    private Text titleText;

    @FXML
    private Text subTitleText;

    @FXML
    private Text descriptionText;

    @FXML
    StackPane editButton;

    @FXML
    StackPane deleteButton;

    Parent root;
    Stage stage;
    Scene scene;

    public void setData(String poster, String title, String subTitle, String content, String id, Runnable delete, Runnable edit, Runnable redirect, Runnable rerender) {
        deleteButton.setOnMouseClicked(_ -> {
            delete.run();
            rerender.run();
        });
        editButton.setOnMouseClicked(_ -> edit.run());
        titleText.setText(title);
        subTitleText.setText(subTitle);
        subTitleText.setOnMouseClicked(_ -> redirect.run());
        posterImage.setImage(new Image(getClass().getResource(poster).toExternalForm()));
        posterImage.setOnMouseClicked(_ -> redirect.run());
        descriptionText.setText(content);
    }

    public void redirectToMoviePage(String movieId) {
        try {
            String css = getClass().getResource("/WatChill/style/Movie.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Movie/movie.fxml"));

            MovieController movieController = loader.getController();
            movieController.build(movieId);

            root = loader.load();
            scene = posterImage.getScene();
            stage = (Stage) scene.getWindow();
            scene.setRoot(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redirectToSeriesPage(String seriesId) {
        try {
            String css = getClass().getResource("/WatChill/style/Series.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Series/series.fxml"));
            root = loader.load();
            SeriesController seriesController = loader.getController();
            seriesController.build(seriesId);
            scene = posterImage.getScene();
            stage = (Stage) scene.getWindow();
            scene.setRoot(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteMovie(String movieId) {
        Movie movie = Movie.findById(movieId);
        movie.delete();
    }

    private void deleteSeries(String seriesId) {
        Series series = Series.findById(seriesId);
        series.delete();
    }
}