package WatChill;

import WatChill.Content.Content;
import WatChill.Crew.Cast.Cast;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Series;
import WatChill.Content.Series.SeriesController;
import WatChill.Crew.Crew;
import WatChill.Crew.Director.Director;
import WatChill.Search.SearchController;
import WatChill.Search.SearchResultController;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HomeController {
    @FXML
    VBox container;
    @FXML
    TextField searchInput;
    @FXML
    MenuButton searchMenu;
    @FXML
    VBox searchResultsContainer;
    @FXML
    Button searchButton;
    @FXML
    Button signInButton;
    @FXML
    Button signUpButton;
    @FXML
    Button subscriptionButton;
    @FXML
    HBox trendingMoviesContainer;
    @FXML
    ScrollPane trendingSeriesScrollPane;
    @FXML
    HBox trendingSeriesContainer;

    Parent root;
    Stage stage;
    Scene scene;

    public void initialize() {
//        if () {
//            signInButton.setVisible(false);
//            signUpButton.setVisible(false);
//            subscriptionButton.setVisible(true);
//        }
//        else {
//            signInButton.setVisible(true);
//            signUpButton.setVisible(true);
//            subscriptionButton.setVisible(false);
//        }
        for (MenuItem menuItem : searchMenu.getItems()) {
            menuItem.setOnAction(_ -> searchMenu.setText(menuItem.getText()));
        }
        searchMenu.setText(searchMenu.getItems().get(0).getText());

        for (Movie movie : Movie.getTopTen()) {
            VBox movieCard = new VBox();
            Text titleText = new Text(movie.getTitle());
            movieCard.getStyleClass().add("movie-card");
            movieCard.getChildren().add(titleText);
            movieCard.setOnMouseClicked(_ -> redirectToMoviePage(movie.getId()));
            trendingMoviesContainer.getChildren().add(movieCard);
        }

        for (Series series : Series.getTopTen()) {
            VBox seriesCard = new VBox(); // The main container
            StackPane stackPane = new StackPane(); // StackPane to stack the image and textContainer
            Image image = new Image(String.valueOf(getClass().getResource(series.getPoster())));
            ImageView imageView = new ImageView(image);
            Text seriesTitle = new Text(series.getTitle());
            Text releaseDate = new Text(series.getReleaseDate().toString());
            Text seriesDescription = new Text(series.getDescription());
            int seasonsCount = series.getSeasons().size();
            String seasonsText = seasonsCount == 1 ? "Season" : "Seasons";
            Text seriesSeasonsCount = new Text(seasonsCount + " " + seasonsText);

            VBox textContainer = new VBox(seriesTitle, seriesSeasonsCount, seriesDescription, releaseDate); // Text container
            textContainer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 10; -fx-spacing: 3;"); // Semi-transparent background
            textContainer.setMaxHeight(140);
            textContainer.setAlignment(Pos.TOP_CENTER); // Center text inside the container
            textContainer.setOpacity(0); // Initially hidden
            StackPane.setAlignment(textContainer, Pos.BOTTOM_CENTER);
            seriesCard.setCursor(Cursor.HAND);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), textContainer);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), textContainer);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(300), imageView);
            scaleUp.setFromX(1.0);
            scaleUp.setFromY(1.0);
            scaleUp.setToX(1.1);
            scaleUp.setToY(1.1);

            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(300), imageView);
            scaleDown.setFromX(1.1);
            scaleDown.setFromY(1.1);
            scaleDown.setToX(1.0);
            scaleDown.setToY(1.0);

            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            seriesCard.setOnMouseEntered(e -> {
                fadeIn.play();
                scaleUp.play();
            });
            seriesCard.setOnMouseExited(e -> {
                fadeOut.play();
                scaleDown.play();
            });
            stackPane.getChildren().addAll(imageView, textContainer);
            seriesCard.getChildren().add(stackPane);
            seriesCard.setOnMouseClicked(_ -> redirectToSeriesPage(series.getId()));
            seriesTitle.getStyleClass().add("series-title");
            seriesSeasonsCount.getStyleClass().add("series-seasons-count");
            seriesDescription.getStyleClass().add("series-seasons-count");
            releaseDate.getStyleClass().add("series-seasons-count");
            textContainer.getStyleClass().add("text-container");
            trendingSeriesContainer.getChildren().add(seriesCard);
        }
    }

    public void redirectToSignUp(ActionEvent actionEvent) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/User/signup.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
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
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void redirectToMoviePage(String movieId) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Movie/movie.fxml"));
            root = loader.load();

            stage = (Stage) trendingMoviesContainer.getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setFullScreen(true);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void redirectToSeriesPage(String seriesId) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Series/Series.fxml"));
            root = loader.load();

            SeriesController seriesController = loader.getController();
            seriesController.build(seriesId);

            stage = (Stage) trendingSeriesContainer.getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void redirectToCastPage(String castId) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Cast/cast.fxml"));
            root = loader.load();

            SeriesController seriesController = loader.getController();
            seriesController.build(castId);

            stage = (Stage) trendingSeriesContainer.getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void redirectToDirectorPage(String directorId) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Director/director.fxml"));
            root = loader.load();

            SeriesController seriesController = loader.getController();
            seriesController.build(directorId);

            stage = (Stage) trendingSeriesContainer.getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
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
            stage = (Stage) trendingSeriesContainer.getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scrollRight(MouseEvent event) {
        double newHValue = Math.min(trendingSeriesScrollPane.getHvalue() + 0.4, 1); // Scroll right
        trendingSeriesScrollPane.setHvalue(newHValue);
    }

    public void scrollLeft(MouseEvent event) {
        double newHValue = Math.min(trendingSeriesScrollPane.getHvalue() - 0.4, 1); // Scroll right
        trendingSeriesScrollPane.setHvalue(newHValue);
    }

    public void redirectToSearch(ActionEvent actionEvent) {
        String query = searchInput.getText();
        if (query.isEmpty()) {
            return;
        }
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Search/search.fxml"));
            root = loader.load();

            SearchController searchController = loader.getController();
            searchController.build(query, searchMenu.getText());

            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSearch(KeyEvent keyEvent) {
        String searchType = searchMenu.getText();
        String searchQuery = searchInput.getText();
        searchResultsContainer.getChildren().clear();
        if (searchQuery.isEmpty()) {
            searchResultsContainer.setVisible(false);
            return;
        }
        else {
            searchResultsContainer.setVisible(true);
        }
        if (searchType.equals("Series")) {
            ArrayList<Series> searchResults = Series.searchByTitle(searchQuery);
            for (int i = 0; i < searchResults.size(); i++) {
                Series series = searchResults.get(i);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Search/searchResult.fxml"));
                    HBox searchResult = loader.load();

                    searchResult.setOnMouseClicked(_ -> redirectToSeriesPage(series.getId()));

                    SearchResultController searchResultController = loader.getController();
                    searchResultController.setData(series.getPoster(), series.getTitle(), series.getReleaseDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")), series.getDescription());
                    searchResultsContainer.getChildren().add(searchResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (i == 2) {
                    break;
                }
            }
        }
        else if (searchType.equals("Movies")) {
            ArrayList<Movie> searchResults = Movie.searchByTitle(searchQuery);
            for (int i = 0; i < searchResults.size(); i++) {
                Movie movie = searchResults.get(i);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Search/searchResult.fxml"));
                    HBox searchResult = loader.load();

                    searchResult.setOnMouseClicked(_ -> redirectToMoviePage(movie.getId()));

                    SearchResultController searchResultController = loader.getController();
                    searchResultController.setData(movie.getPoster(), movie.getTitle(), movie.getReleaseDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
                    searchResultsContainer.getChildren().add(searchResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (i == 2) {
                    break;
                }
            }
        }
        else if (searchType.equals("Crew")) {
            ArrayList<Crew> searchResults = Crew.searchByName(searchQuery);
            for (int i = 0; i < searchResults.size(); i++) {
                Crew crew = searchResults.get(i);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Search/searchResult.fxml"));
                    HBox searchResult = loader.load();

                    searchResult.setOnMouseClicked(_ -> redirectToCastPage(crew.getId()));

                    SearchResultController searchResultController = loader.getController();
                    searchResultController.setData(crew.getFirstName() + " " + crew.getLastName(), crew.getNationality(), crew.getDateOfBirth().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
                    searchResultsContainer.getChildren().add(searchResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (i == 2) {
                    break;
                }
            }
        }
    }
}
