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

public class ContentCardController {

    @FXML
    private ImageView posterImage;

    @FXML
    private Label titleText;

    @FXML
    private Label releaseDateText;

    @FXML
    ImageView addToWatchLaterButton;

    @FXML
    ImageView redirectToContentButton;

    Parent root;
    Stage stage;
    Scene scene;

    public void initialize() {
        User currentUser = User.getCurrentUser();
        if (currentUser != null) {
            if (currentUser instanceof Admin) {
                addToWatchLaterButton.setVisible(false);
                addToWatchLaterButton.setManaged(false);
            }
        }
        else {
            addToWatchLaterButton.setOnMouseClicked(e -> redirectToLogin(e));
        }
    }

    public void setData(Content content, Runnable rerender) {
        posterImage.setImage(new Image(getClass().getResource(content.getPoster()).toExternalForm()));
        titleText.setText(content.getTitle());
        if (content instanceof Movie) {
            releaseDateText.setText(content.getReleaseDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
        }
        else {
            releaseDateText.setText(((Series) content).getSeasons().size() + " Season(s)");
        }
        User currentUser = User.getCurrentUser();
        if (currentUser instanceof Customer) {
            Customer customer = (Customer) currentUser;
            if (customer.findMovieWatchLaterIndex(content.getId()) == -1) {
                addToWatchLaterButton.setOnMouseClicked(_ -> {
                    customer.addToWatchLater(content);
                    addToWatchLaterButton.setImage(new Image(getClass().getResource("/WatChill/Content/Series/media/plus.png").toExternalForm()));
                    if (rerender != null) {
                        rerender.run();
                    }
                });
            }
            else {
                addToWatchLaterButton.setImage(new Image(getClass().getResource("/WatChill/Content/Series/media/minus-circle.png").toExternalForm()));
                addToWatchLaterButton.setOnMouseClicked(_ -> {
                    customer.removeFromWatchLater(content.getId());
                    addToWatchLaterButton.setImage(new Image(getClass().getResource("/WatChill/Content/Series/media/plus.png").toExternalForm()));
                   if (rerender != null) {
                       rerender.run();
                   }
                });
            }
        }
        redirectToContentButton.setOnMouseClicked(_ -> {
            Series series = Series.findById(content.getId());
            Movie movie = Movie.findById(content.getId());
            if (movie != null) {
                redirectToMoviePage(movie.getId());
            }
            else {
                redirectToSeriesPage(series.getId());
            }
        });
    }

    public void redirectToLogin(Event event) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/User/login.fxml"));
            root = loader.load();
            scene = posterImage.getScene();
            stage = (Stage) scene.getWindow();
            scene.setRoot(root);
            scene.getStylesheets().clear();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redirectToMoviePage(String movieId) {
        try {
            String css = getClass().getResource("/WatChill/style/Movie.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Movie/Movie.fxml"));
            root = loader.load();

            MovieController movieController = loader.getController();
            movieController.build(movieId);

            scene = posterImage.getScene();
            stage = (Stage) scene.getWindow();
            scene.setRoot(root);
            scene.getStylesheets().clear();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Series/Series.fxml"));
            root = loader.load();

            SeriesController seriesController = loader.getController();
            seriesController.build(seriesId);

            scene = posterImage.getScene();
            stage = (Stage) scene.getWindow();
            scene.setRoot(root);
            scene.getStylesheets().clear();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}