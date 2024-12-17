package WatChill.Content.Series;

import WatChill.Content.Movie.Movie;
import WatChill.Content.Movie.MovieController;
import WatChill.Crew.Crew;
import WatChill.Crew.CrewController;
import WatChill.Review.Review;
import WatChill.Search.SearchController;
import WatChill.Search.SearchResultController;
import WatChill.UserManagement.Customer;
import WatChill.UserManagement.User;
import WatChill.UserWatchRecord.UserWatchRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EpisodeController {
    private Episode episode;
    private Series series;
    private Season season;

    @FXML
    TextField searchInput;
    @FXML
    MenuButton searchMenu;
    @FXML
    VBox searchResultsContainer;
    @FXML
    Button signInButton;
    @FXML
    Button signUpButton;
    @FXML
    HBox trendingSeriesContainer;
    @FXML
    ImageView bgImage;
    @FXML
    ImageView profileIcon;

    @FXML
    private MenuButton seasonsMenu;
    @FXML
    private ImageView seriesPoster;
    @FXML
    private FlowPane episodesPane;
    @FXML
    private Label seriesTitle;
    @FXML
    private HBox genresBox;
    @FXML
    private HBox languagesBox;
    @FXML
    private HBox starsBox;
    @FXML
    private Label releaseDate;
    @FXML
    private Label country;
    @FXML
    private Label budgetLabel;
    @FXML
    private Label ratingLabel;

    Parent root;

    Stage stage;

    Scene scene;

    public void initializePage(String episodeId) {
        episode = Episode.findById(episodeId);
        season = Season.findById(episode.getSeasonId());
        series = Series.findById(season.getSeriesId());
        seriesTitle.setText(series.getTitle());
        initializeSeasonMenu();
        initializeEpisodePane();
        addGenres();
        addLanguages();
        releaseDate.setText(((Integer) series.getReleaseDate().getYear()).toString());
        country.setText(series.getCountry());
        Image seriesImage = new Image(getClass().getResource(series.getPoster()).toExternalForm());
        seriesPoster.setImage(seriesImage);
        budgetLabel.setText(((Double) series.getBudget()).toString());
        ratingLabel.setText(((Double) series.getRating()).toString());
        for (MenuItem menuItem : searchMenu.getItems()) {
            menuItem.setOnAction(_ -> searchMenu.setText(menuItem.getText()));
        }
        initializeRating();
    }

    public void initializeSeasonMenu() {
        for (Season season : series.getSeasons()) {
            MenuItem currentSeriesSeason = new MenuItem(season.getTitle());
            currentSeriesSeason.setOnAction(e -> {
                seasonsMenu.setText(currentSeriesSeason.getText());
                initializeEpisodePane();
            });
            seasonsMenu.getItems().add(currentSeriesSeason);
        }
        seasonsMenu.setText(seasonsMenu.getItems().getFirst().getText());
        Image seriesImage = new Image(getClass().getResource(series.getPoster()).toExternalForm());
        seriesPoster.setImage(seriesImage);
    }

    public void initializeEpisodePane() {
        episodesPane.getChildren().clear();
        for (Season season : series.getSeasons()) {
            if (season.getTitle().equals(seasonsMenu.getText())) {
                for (int episode = 1; episode <= season.getEpisodes().size(); episode++) {
                    Button button = new Button();
                    button.setText(((Integer) episode).toString());
                    button.getStyleClass().add("episode-button");
                    button.setPrefWidth(40);
                    button.setPrefHeight(40);
                    episodesPane.getChildren().add(button);
                }
            }
        }

    }

    public void initializeRating() {
        int previousRating = -1;
        if (User.getCurrentUser() instanceof Customer) {
            for (UserWatchRecord userWatchRecord : UserWatchRecord.getUserWatchRecord(User.getCurrentUser().getId())) {
                if (userWatchRecord.getWatchedContent().getId().equals(episode.getId())) {
                    previousRating = userWatchRecord.getReview().getRating();
                }
            }
        }
        final int starCount = 5;
        ImageView[] stars = new ImageView[starCount];
        if (previousRating != -1) {
            fillStars(stars, previousRating);
        } else {
            // Create the stars and set their hover effects
            for (int i = 0; i < starCount; i++) {
                ImageView star = new ImageView(new Image(getClass().getResource("/WatChill/Content/Empty star.png").toExternalForm()));
                star.setFitHeight(48); // Adjust size as needed
                star.setFitWidth(48);

                int currentStarIndex = i;

                // Set hover event (on mouse enter)
                star.setOnMouseEntered(event -> {
                    fillStars(stars, currentStarIndex);
                });
                star.setOnMouseClicked(event -> {
                    fillStars(stars, currentStarIndex);
                    if (User.getCurrentUser() instanceof Customer) {
                        ((Customer) User.getCurrentUser()).watchContent(episode, new Review("", currentStarIndex));
                    }
                });

                // Set event to reset when the mouse exits any star
                star.setOnMouseExited(event -> {
                    resetStars(stars);
                });

                star.setCursor(Cursor.HAND);
                // Add star to the array and the HBox
                stars[i] = star;
                starsBox.getChildren().add(star);
            }
        }
    }

    private void fillStars(ImageView[] stars, int index) {
        for (int i = 0; i <= index; i++) {
            if (!stars[i].getImage().toString().equals(getClass().getResource("/WatChill/Content/star.png").toExternalForm())) {
                stars[i].setImage(new Image(getClass().getResource("/WatChill/Content/star.png").toExternalForm()));
            }
        }
    }

    // Reset all stars to empty
    private void resetStars(ImageView[] stars) {
        for (ImageView star : stars) {
            if (!star.getImage().toString().equals(getClass().getResource("/WatChill/Content/Empty star.png").toExternalForm())) {
                star.setImage(new Image(getClass().getResource("/WatChill/Content/Empty star.png").toExternalForm()));
            }
        }
    }

    public void addGenres() {
        genresBox.getChildren().clear();
        for (String genre : series.getGenres()) {
            Label genreLabel = new Label(genre);
            genreLabel.getStyleClass().add("genre");
            genresBox.getChildren().add(genreLabel);
            Label bullet = new Label("•");
            bullet.getStyleClass().add("genre");
            genresBox.getChildren().add(bullet);
        }
        genresBox.getChildren().removeLast();
        genresBox.getStyleClass().add("genres-box");
    }

    public void addLanguages() {
        languagesBox.getChildren().clear();
        for (String language : series.getLanguages()) {
            Label languageLabel = new Label(language);
            languageLabel.getStyleClass().add("language");
            languagesBox.getChildren().add(languageLabel);
            Label bullet = new Label("•");
            bullet.getStyleClass().add("language");
            languagesBox.getChildren().add(bullet);
        }
        if (!languagesBox.getChildren().isEmpty()) {
            languagesBox.getChildren().removeLast();
        }
        languagesBox.getStyleClass().add("genres-box");
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

    public void handleSearch(KeyEvent keyEvent) {
        String searchType = searchMenu.getText();
        String searchQuery = searchInput.getText();
        searchResultsContainer.getChildren().clear();
        if (searchQuery.isEmpty()) {
            searchResultsContainer.setVisible(false);
            return;
        } else {
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
        } else if (searchType.equals("Movies")) {
            ArrayList<Movie> searchResults = Movie.searchByTitle(searchQuery);
            for (int i = 0; i < searchResults.size(); i++) {
                Movie movie = searchResults.get(i);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Search/searchResult.fxml"));
                    HBox searchResult = loader.load();

                    searchResult.setOnMouseClicked(_ -> redirectToMoviePage(movie.getId()));

                    SearchResultController searchResultController = loader.getController();
                    searchResultController.setData(movie.getPoster(), movie.getTitle(), movie.getReleaseDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")), movie.getDescription());
                    searchResultsContainer.getChildren().add(searchResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (i == 2) {
                    break;
                }
            }
        } else if (searchType.equals("Crew")) {
            ArrayList<Crew> searchResults = Crew.searchByName(searchQuery);
            for (int i = 0; i < searchResults.size(); i++) {
                Crew crew = searchResults.get(i);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Search/searchResult.fxml"));
                    HBox searchResult = loader.load();

                    searchResult.setOnMouseClicked(_ -> redirectToCrewPage(crew.getId()));

                    SearchResultController searchResultController = loader.getController();
                    searchResultController.setData(crew.getPicture(), crew.getFirstName() + " " + crew.getLastName(), crew.getNationality(), crew.getDateOfBirth().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void redirectToSeriesPage(String seriesId) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
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

    private void redirectToMoviePage(String movieId) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Movie/Movie.fxml"));
            root = loader.load();

            MovieController movieController = loader.getController();
            movieController.build(movieId);

            scene = searchResultsContainer.getScene();
            stage = (Stage) scene.getWindow();
            scene.setRoot(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void build(String episodeId) {
        initializePage(episodeId);
    }
}
