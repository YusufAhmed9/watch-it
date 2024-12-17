package WatChill;

import WatChill.Content.Content;
import WatChill.Content.ContentCardController;
import WatChill.Content.Movie.MovieController;
import WatChill.Crew.Cast.Cast;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Series;
import WatChill.Content.Series.SeriesController;
import WatChill.Crew.Crew;
import WatChill.Crew.CrewController;
import WatChill.Search.SearchController;
import WatChill.Search.SearchResultController;
import WatChill.Subscription.*;
import WatChill.UserManagement.Customer;
import WatChill.UserManagement.User;
import WatChill.UserWatchRecord.UserWatchRecord;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class HomeController {
    @FXML
    HBox trendingMoviesHBox;
    @FXML
    AnchorPane trendingMoviesAnchor;
    @FXML
    ImageView trendingMoviesRight;
    @FXML
    ImageView trendingMoviesLeft;
    @FXML
    ScrollPane trendingMoviesScrollPane;
    @FXML
    HBox trendingSeriesHBox;
    @FXML
    AnchorPane trendingSeriesAnchor;
    @FXML
    ImageView trendingSeriesLeft;
    @FXML
    ImageView trendingSeriesRight;
    @FXML
    HBox recommendationTitle;
    @FXML
    HBox recommendationHBox;
    @FXML
    AnchorPane recommendationAnchor;
    @FXML
    HBox recommendationContainer;
    @FXML
    ImageView recommendationRight;
    @FXML
    ImageView recommendationLeft;
    @FXML
    ScrollPane recommendationScrollPane;
    @FXML
    HBox bgParent;
    @FXML
    VBox container;
    @FXML
    VBox searchResultsContainer;
    @FXML
    HBox trendingMoviesContainer;
    @FXML
    ScrollPane trendingSeriesScrollPane;
    @FXML
    HBox trendingSeriesContainer;
    @FXML
    VBox plansContainer;
    @FXML
    HBox homeLoginSignup;
    @FXML
    BorderPane homeBorderPane;

    Parent root;
    Stage stage;
    Scene scene;

    public void initialize() {
        initializeHeader();
        User currentUser = User.getCurrentUser();
        trendingSeriesContainer.getChildren().clear();
        trendingMoviesContainer.getChildren().clear();
        recommendationContainer.getChildren().clear();
        if (currentUser != null) {
            homeLoginSignup.setVisible(false);
            if (currentUser instanceof Customer) {
                Subscription subscription = Subscription.getUserSubscription(currentUser.getId());
                ArrayList<Content> contents = UserWatchRecord.recommendWatchableContent(User.getCurrentUser().getId());
                if (!contents.isEmpty()) {
                    initializeRecommendations(contents);
                }
                else {
                    recommendationHBox.setVisible(false);
                    recommendationHBox.setManaged(false);
                    recommendationTitle.setVisible(false);
                    recommendationTitle.setManaged(false);
                }
                if (subscription != null) {
                    plansContainer.setVisible(false);
                    plansContainer.setManaged(false);
                }
            }
            else {
                plansContainer.setVisible(false);
                plansContainer.setManaged(false);
                recommendationHBox.setVisible(false);
                recommendationHBox.setManaged(false);
                recommendationTitle.setVisible(false);
                recommendationTitle.setManaged(false);
            }
        }
        else {
            homeLoginSignup.setVisible(true);
            recommendationHBox.setVisible(false);
            recommendationHBox.setManaged(false);
            recommendationTitle.setVisible(false);
            recommendationTitle.setManaged(false);
        }

        for (Movie movie : Movie.getTopTen()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/content-card.fxml"));
                VBox movieCard = loader.load();
                ContentCardController contentCardController = loader.getController();
                contentCardController.setData(movie, () -> initialize());
                trendingMoviesContainer.getChildren().add(movieCard);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Series series : Series.getTopWatchedSeries()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/content-card.fxml"));
                    VBox seriesCard = loader.load();
                    ContentCardController contentCardController = loader.getController();
                    contentCardController.setData(series, () -> initialize());
                    trendingSeriesContainer.getChildren().add(seriesCard);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
        }
        trendingMoviesAnchor.prefWidthProperty().bind(trendingMoviesHBox.widthProperty());
        trendingSeriesAnchor.prefWidthProperty().bind(trendingSeriesHBox.widthProperty());
        recommendationAnchor.prefWidthProperty().bind(recommendationHBox.widthProperty());
        initializeScroll();
    }

    private void initializeScroll() {
        trendingSeriesLeft.setOnMouseClicked(_ -> scrollLeft(trendingSeriesScrollPane));
        trendingMoviesRight.setOnMouseClicked(_ -> scrollRight(trendingSeriesScrollPane));
        trendingMoviesLeft.setOnMouseClicked(_ -> scrollLeft(trendingMoviesScrollPane));
        trendingMoviesRight.setOnMouseClicked(_ -> scrollRight(trendingMoviesScrollPane));
        recommendationLeft.setOnMouseClicked(_ -> scrollLeft(recommendationScrollPane));
        recommendationRight.setOnMouseClicked(_ -> scrollRight(recommendationScrollPane));
    }

    public void redirectToSignUp(ActionEvent actionEvent) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/User/signup.fxml"));
            root = loader.load();
            scene = ((Node) actionEvent.getSource()).getScene();
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

    public void redirectToLogin(ActionEvent actionEvent) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/User/login.fxml"));
            root = loader.load();
            scene = ((Node) actionEvent.getSource()).getScene();
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

    private void redirectToMoviePage(String movieId) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Movie/Movie.fxml"));
            root = loader.load();

            MovieController movieController = loader.getController();
            movieController.build(movieId);

            scene = trendingMoviesContainer.getScene();
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

    private void redirectToSeriesPage(String seriesId) {
        try {
            String css = getClass().getResource("/WatChill/style/Series.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Series/Series.fxml"));
            root = loader.load();

            SeriesController seriesController = loader.getController();
            seriesController.build(seriesId);

            scene = trendingSeriesContainer.getScene();
            stage = (Stage) scene.getWindow();
            scene.setRoot(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void redirectToCrewPage(String castId) {
        try {
            String css = getClass().getResource("/WatChill/style/Crew.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Crew/Crew.fxml"));
            root = loader.load();

            CrewController crewController = loader.getController();
            crewController.build(castId);

            scene = trendingSeriesContainer.getScene();
            stage = (Stage) scene.getWindow();
            scene.setRoot(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redirectToProfile(MouseEvent mouseEvent) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/User/profile.fxml"));
            root = loader.load();
            scene = trendingSeriesContainer.getScene();
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

    private void scrollRight(ScrollPane scrollPane) {
        double newHValue = Math.min(scrollPane.getHvalue() + 0.4, 1); // Scroll right
        scrollPane.setHvalue(newHValue);
    }

    private void scrollLeft(ScrollPane scrollPane) {
        double newHValue = Math.min(scrollPane.getHvalue() - 0.4, 1); // Scroll right
        scrollPane.setHvalue(newHValue);
    }

    public void chooseBasicPlan(ActionEvent actionEvent) {
        User user = User.getCurrentUser();
        if (user == null) {
            redirectToLogin(actionEvent);
            return;
        }
        Plan plan = new BasicPlan();
        Subscription subscription = new Subscription(UUID.randomUUID().toString(), user.getId(), LocalDate.now(), plan, plan.getMaxMoviesCount());
        subscription.saveSubscription();
        renderSubscriptionSuccess();
    }

    public void chooseStandardPlan(ActionEvent actionEvent) {
        User user = User.getCurrentUser();
        if (user == null) {
            redirectToLogin(actionEvent);
            return;
        }
        Plan plan = new StandardPlan();
        Subscription subscription = new Subscription(UUID.randomUUID().toString(), user.getId(), LocalDate.now(), plan, plan.getMaxMoviesCount());
        subscription.saveSubscription();
        renderSubscriptionSuccess();
    }

    public void choosePremiumPlan(ActionEvent actionEvent) {
        User user = User.getCurrentUser();
        if (user == null) {
            redirectToLogin(actionEvent);
            return;
        }
        Plan plan = new PremiumPlan();
        Subscription subscription = new Subscription(UUID.randomUUID().toString(), user.getId(), LocalDate.now(), plan, plan.getMaxMoviesCount());
        subscription.saveSubscription();
        renderSubscriptionSuccess();
    }

    private void renderSubscriptionSuccess() {
        Text successMessage = new Text("Subscription Successful.");
        successMessage.getStyleClass().add("home-title");
        successMessage.setFill(Color.WHITE);
        plansContainer.getChildren().clear();
        plansContainer.getChildren().add(successMessage);
    }

    private void initializeRecommendations(ArrayList<Content> contents) {
        for (Content content : contents) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/content-card.fxml"));
                VBox contentCard = loader.load();
                ContentCardController contentCardController = loader.getController();
                contentCardController.setData(content, () -> initialize());
                recommendationContainer.getChildren().add(contentCard);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeHeader() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Home/header.fxml"));
            Pane header = loader.load();
            homeBorderPane.setTop(header);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
