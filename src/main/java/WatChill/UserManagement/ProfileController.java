package WatChill.UserManagement;

import WatChill.Content.Content;
import WatChill.Content.ContentCardController;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Episode;
import WatChill.Content.WatchableContent;
import WatChill.Content.WatchedContent;
import WatChill.Search.SearchResultController;
import WatChill.Subscription.BasicPlan;
import WatChill.Subscription.StandardPlan;
import WatChill.Subscription.Subscription;
import WatChill.UserWatchRecord.UserWatchRecord;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.Flow;

public class ProfileController {
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
    VBox remainingContainer;
    @FXML
    VBox historyContainer;
    @FXML
    VBox historyBox;
    @FXML
    Label username;
    @FXML
    Label email;
    @FXML
    TextField firstNameInput;
    @FXML
    Text firstNameError;
    @FXML
    TextField lastNameInput;
    @FXML
    Text lastNameError;
    @FXML
    PasswordField oldPasswordInput;
    @FXML
    Text oldPasswordError;
    @FXML
    TextField newPasswordInput;
    @FXML
    Text newPasswordError;
    @FXML
    BorderPane profileBorderPane;

    Parent root;
    Stage stage;
    Scene scene;

    public void initialize() {
        initializeHeader();
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
        watchlaterFlowPane.getChildren().clear();
        Customer customer = (Customer) User.getCurrentUser();
        if (customer.getWatchLater().isEmpty()) {
            Text noWatchLaterText = new Text("No content in your watch later.");
            noWatchLaterText.setFill(Color.WHITE);
            noWatchLaterText.getStyleClass().add("subscription-text");
            watchLaterContainer.getChildren().add(noWatchLaterText);
            return;
        }
        for (Content content : customer.getWatchLater()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/content-card.fxml"));
            try {
                VBox card = loader.load();
                ContentCardController contentCardController = loader.getController();
                contentCardController.setData(content, () -> displayWatchLater());
                watchlaterFlowPane.getChildren().add(card);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void displayMyInfo(ActionEvent actionEvent) {
        watchLaterContainer.setVisible(false);
        watchLaterContainer.setManaged(false);
        infoContainer.setVisible(true);
        infoContainer.setManaged(true);
        historyContainer.setVisible(false);
        historyContainer.setManaged(false);
        subscriptionContainer.setVisible(false);
        subscriptionContainer.setManaged(false);
        User user = User.getCurrentUser();
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        firstNameInput.setText(user.getFirstName());
        lastNameInput.setText(user.getLastName());
    }

    public void displayHistory(ActionEvent actionEvent) {
        watchLaterContainer.setVisible(false);
        watchLaterContainer.setManaged(false);
        infoContainer.setVisible(false);
        infoContainer.setManaged(false);
        historyContainer.setVisible(true);
        historyContainer.setManaged(true);
        subscriptionContainer.setVisible(false);
        subscriptionContainer.setManaged(false);
        historyBox.getChildren().clear();
        ArrayList<UserWatchRecord> userWatchRecords = UserWatchRecord.getUserWatchRecord(User.getCurrentUser().getId());
        if (userWatchRecords.isEmpty()) {
            Text noHistoryText = new Text("You have no watch records yet.");
            noHistoryText.getStyleClass().add("subscription-text");
            historyBox.getChildren().add(noHistoryText);
            return;
        }
        for (UserWatchRecord userWatchRecord : userWatchRecords) {
            WatchedContent watchableContent = userWatchRecord.getWatchedContent();
            if (watchableContent instanceof Movie) {
                Movie movie = (Movie) watchableContent;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/searchResult.fxml"));
                try {
                    HBox card = loader.load();
                    SearchResultController searchResultController = loader.getController();
                    searchResultController.setData(movie.getPoster(), movie.getTitle(), movie.getReleaseDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")), movie.getDescription());
                    historyBox.getChildren().add(card);
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
                    historyBox.getChildren().add(card);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void displayMySubscription() {
        watchLaterContainer.setVisible(false);
        watchLaterContainer.setManaged(false);
        infoContainer.setVisible(false);
        infoContainer.setManaged(false);
        historyContainer.setVisible(false);
        historyContainer.setManaged(false);
        subscriptionContainer.setVisible(true);
        subscriptionContainer.setManaged(true);
        User user = User.getCurrentUser();
        Subscription subscription = Subscription.getUserSubscription(user.getId());
        remainingContainer.getChildren().clear();
        if (subscription == null) {
            planContainer.getChildren().clear();
            Text noSubscriptionText = new Text("You don't have an active subscription");
            noSubscriptionText.getStyleClass().add("subscription-text");
            planContainer.getChildren().add(noSubscriptionText);
            return;
        }
        if (subscription.getPlan() instanceof BasicPlan) {
            planName.setText("Basic");
        }
        else if (subscription.getPlan() instanceof StandardPlan) {
            planName.setText("Standard");
        }
        else {
            planName.setText("Premium");
        }
        Text remainingText = new Text("You have " + subscription.getMoviesLeftCount() + " contents left to watch");
        remainingText.getStyleClass().add("subscription-text");
        Text endDate = new Text("Your Subscription ends on " + subscription.getStartDate().plusDays(30).format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
        endDate.getStyleClass().add("subscription-text");
        remainingContainer.getChildren().add(remainingText);
        remainingContainer.getChildren().add(endDate);
    }

    public void editInfo(ActionEvent actionEvent) {
        firstNameError.setText("");
        lastNameError.setText("");
        oldPasswordError.setText("");
        newPasswordError.setText("");
        User user = User.getCurrentUser();
        if (firstNameInput.getText().isEmpty()) {
            firstNameError.setText("First name can't be empty.");
            return;
        }
        if (lastNameInput.getText().isEmpty()) {
            lastNameError.setText("Last name can't be empty.");
            return;
        }
        if (!newPasswordInput.isDisable() && newPasswordInput.getText().length() < 8) {
            newPasswordError.setText("Password must have at least 8 characters.");
            return;
        }
        user.setFirstName(firstNameInput.getText());
        user.setLastName(lastNameInput.getText());
        if (!newPasswordInput.getText().isEmpty()) {
            String newPassword = newPasswordInput.getText();
            byte[] salt = User.generateSalt();
            String hashedPassword = User.hashPassword(newPassword, salt);
            user.setPassword(Base64.getEncoder().encodeToString(salt) + ":" + hashedPassword);
        }
        oldPasswordInput.setText("");
        newPasswordInput.setText("");
        newPasswordInput.setDisable(true);
    }

    public void checkPassword(ActionEvent actionEvent) {
        User user = User.getCurrentUser();
        oldPasswordError.setText("");
        if (!User.loginWithUsername(user.getUsername(), oldPasswordInput.getText())) {
            oldPasswordError.setText("Wrong password.");
            newPasswordInput.setText("");
            newPasswordInput.setDisable(true);
            return;
        }
        newPasswordInput.setDisable(false);
        newPasswordInput.setText(oldPasswordInput.getText());
    }

    private void initializeHeader() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Home/header.fxml"));
            Pane header = loader.load();
            profileBorderPane.setTop(header);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
