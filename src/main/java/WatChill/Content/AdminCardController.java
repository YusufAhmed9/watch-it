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
import javafx.scene.layout.HBox;
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
        System.out.println(poster);
        posterImage.setImage(new Image(getClass().getResource(poster).toExternalForm()));
        posterImage.setOnMouseClicked(_ -> redirect.run());
        descriptionText.setText(content);
    }
}