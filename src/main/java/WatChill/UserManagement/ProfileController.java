package WatChill.UserManagement;

import WatChill.Content.Content;
import WatChill.Content.ContentCardController;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Episode;
import WatChill.Content.WatchableContent;
import WatChill.Content.WatchedContent;
import WatChill.Search.SearchResultController;
import WatChill.UserWatchRecord.UserWatchRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.Flow;

public class ProfileController {
    @FXML
    HBox profileContainer;
    @FXML
    VBox infoContainer;
    @FXML
    VBox watchLaterContainer;
    @FXML
    FlowPane watchlaterFlowPane;
    @FXML
    VBox subscriptionContainer;
    @FXML
    Label planName;
    @FXML
    HBox planContainer;
    @FXML
    HBox remainingContainer;
    @FXML
    VBox historyContainer;
    @FXML
    FlowPane historyFlowPane;

    public void initialize() {
        displayWatchLater();
    }

    public void displayWatchLater(ActionEvent actionEvent) {
        displayWatchLater();
    }

    private void displayWatchLater() {
        watchLaterContainer.setVisible(true);
        watchLaterContainer.setManaged(true);
        infoContainer.setVisible(false);
        infoContainer.setManaged(false);
        historyContainer.setVisible(false);
        historyContainer.setManaged(false);
        subscriptionContainer.setVisible(false);
        subscriptionContainer.setManaged(false);
        Customer customer = (Customer) User.getCurrentUser();
        for (Content content : customer.getWatchLater()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/content-card.fxml"));
            try {
                VBox card = loader.load();
                ContentCardController contentCardController = loader.getController();
                contentCardController.setData(content.getPoster(), content.getTitle(), content.getReleaseDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
                watchlaterFlowPane.getChildren().add(card);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void displayMyInfo(ActionEvent actionEvent) {

    }

    public void displayHistory(ActionEvent actionEvent) {
        ArrayList<UserWatchRecord> userWatchRecords = UserWatchRecord.getUserWatchRecord(User.getCurrentUser().getId());
        for (UserWatchRecord userWatchRecord : userWatchRecords) {
            WatchedContent watchableContent = userWatchRecord.getWatchedContent();
            if (watchableContent instanceof Movie) {
                Movie movie = (Movie) watchableContent;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/searchResult.fxml"));
                try {
                    HBox card = loader.load();
                    SearchResultController searchResultController = loader.getController();
                    searchResultController.setData(movie.getPoster(), movie.getTitle(), movie.getReleaseDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")), movie.getDescription());
                    historyFlowPane.getChildren().add(card);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                Episode episode = (Episode) watchableContent;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/searchResult.fxml"));
                try {
                    HBox card = loader.load();
                    SearchResultController searchResultController = loader.getController();
                    searchResultController.setData(episode.getPoster(), episode.getTitle(), episode.getReleaseDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")), episode.getDescription());
                    historyFlowPane.getChildren().add(card);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void displayMySubscription() {

    }
}
