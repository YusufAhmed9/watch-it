package WatChill.Content.Series;

import WatChill.Content.Movie.Movie;
import WatChill.Content.Movie.MovieController;
import WatChill.Crew.Cast.Cast;
import WatChill.Crew.Crew;
import WatChill.Crew.CrewController;
import WatChill.Crew.Director.Director;
import WatChill.Search.SearchController;
import WatChill.Search.SearchResultController;
import WatChill.UserManagement.Customer;
import WatChill.UserManagement.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SeriesController {
    @FXML
    private ScrollPane castsScrollPane;
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

    private Series currentSeries;
    @FXML
    private MenuButton seasonsMenu;
    @FXML
    private Label seriesTitle;
    @FXML
    private Text seasonDescription;
    @FXML
    private ImageView seriesPoster;
    @FXML
    private VBox episodesContainer;
    @FXML
    private HBox genresBox;
    @FXML
    private HBox ratingBox;
    @FXML
    private HBox castsBox;
    @FXML
    private HBox directorsBox;
    @FXML
    private ImageView watchLaterButton;

    private Parent root;
    private Scene scene;
    private Stage stage;


    private void initializeSeries(String seriesId) {
        if (User.getCurrentUser() != null) {
            signInButton.setVisible(false);
            signUpButton.setVisible(false);
            profileIcon.setVisible(true);
        } else {
            signInButton.setVisible(true);
            signUpButton.setVisible(true);
            profileIcon.setVisible(false);

        }
        this.currentSeries = Series.findById(seriesId);
        seriesTitle.setText(currentSeries.getTitle());
        for (Season season : currentSeries.getSeasons()) {
            MenuItem currentSeriesSeason = new MenuItem(season.getTitle());
            currentSeriesSeason.setOnAction(e -> {
                seasonsMenu.setText(currentSeriesSeason.getText());
                displayEpisodes();
            });
            seasonsMenu.getItems().add(currentSeriesSeason);
        }
        seasonsMenu.setText(seasonsMenu.getItems().getFirst().getText());
        seasonDescription.setText(currentSeries.getDescription());
        seasonDescription.getStyleClass().add("series-description");
        Image seriesImage = new Image(getClass().getResource(currentSeries.getPoster()).toExternalForm());
        seriesPoster.setImage(seriesImage);
        addCrew();
        addRating();
        addGenres();
        displayEpisodes();
        if (User.getCurrentUser() instanceof Customer) {
            if (((Customer) User.getCurrentUser()).findMovieWatchLaterIndex(currentSeries.getId()) == -1) {
                watchLaterButton.setImage(new Image(getClass().getResource("/WatChill/Content/Series/media/plus.png").toExternalForm()));
            } else {
                watchLaterButton.setImage(new Image(getClass().getResource("/WatChill/Content/Series/media/minus-circle.png").toExternalForm()));
            }
        }
        for (MenuItem menuItem : searchMenu.getItems()) {
            menuItem.setOnAction(_ -> searchMenu.setText(menuItem.getText()));
        }
    }

    public void addGenres() {
        genresBox.getChildren().clear();
        for (String genre : currentSeries.getGenres()) {
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

    public void addRating() {
        ratingBox.getChildren().clear();
        int star = 1;
        for (; star <= Math.floor(currentSeries.getRating()); star++) {
            ImageView starImage = new ImageView(getClass().getResource("/WatChill/Content/Star 5 (Stroke).png").toExternalForm());
            ratingBox.getChildren().add(starImage);
        }
        for (; star <= 5; star++) {
            ImageView starImage = new ImageView(getClass().getResource("/WatChill/Content/Star 5.png").toExternalForm());
            ratingBox.getChildren().add(starImage);
        }
        Label rating = new Label(((Double) currentSeries.getRating()).toString() + "/5");
        rating.getStyleClass().add("rating-text");
        ratingBox.getChildren().add(rating);
    }

    public void displayEpisodes() {
        episodesContainer.getChildren().clear();
        for (Season season : currentSeries.getSeasons()) {
            if (seasonsMenu.getText().equals(season.getTitle())) {
                for (Episode episode : season.getEpisodes()) {
                    HBox episodeContainer = new HBox(); // HBox to hold image and labels
                    ImageView episodeImageView = new ImageView(getClass().getResource(episode.getPoster()).toExternalForm());
                    // Set dimensions for the image
                    double imageWidth = 270;  // Set the width of the image
                    double imageHeight = 172; // Set the height of the image
                    episodeImageView.setFitWidth(imageWidth);
                    episodeImageView.setFitHeight(imageHeight);

                    // Create a rectangle with rounded top-left and bottom-left corners
                    Rectangle clip = new Rectangle(imageWidth, imageHeight);
                    clip.setArcWidth(50);  // The arc width for the left corners
                    clip.setArcHeight(50); // The arc height for the left corners
                    episodeImageView.setClip(clip);  // Apply the clipping to the image

                    episodeImageView.getStyleClass().add("episode-poster");


                    // VBox to hold the title, release date, and seasonDescription
                    VBox episodeLabels = new VBox();

                    Label episodeTitle = new Label("S" + (currentSeries.getSeasons().indexOf(season) + 1) + " E" + (season.getEpisodes().indexOf(episode) + 1) + " - " + episode.getTitle());
                    episodeTitle.getStyleClass().add("episode-title");

                    Label episodeReleaseDate = new Label(episode.getReleaseDate().toString());
                    episodeReleaseDate.getStyleClass().add("episode-release-date");

                    Label episodeDescription = new Label(episode.getDescription());
                    episodeDescription.getStyleClass().add("episode-description");
                    episodeDescription.setWrapText(true);
                    // Add labels to the VBox
                    episodeLabels.getChildren().addAll(episodeTitle, episodeReleaseDate, episodeDescription);
                    episodeLabels.getStyleClass().add("episode-labels");
                    // Add the image and the labels to the episodeContainer
                    episodeContainer.getChildren().addAll(episodeImageView, episodeLabels);
                    // Add episode container to the episodes VBox
                    episodeContainer.getStyleClass().add("episode-container");

                    HBox indentation = new HBox();
                    HBox.setHgrow(indentation, Priority.ALWAYS);
                    episodeContainer.getChildren().add(indentation);
                    ImageView playButton = new ImageView(getClass().getResource("/WatChill/Content/Series/media/play-circle.png").toExternalForm());
                    playButton.setOnMouseClicked(_ -> redirectToEpisodePage(episode.getId()));
                    playButton.setStyle("-fx-cursor: hand; -fx-padding: 0 10 0 0");
//                    if(User.getCurrentUser() == null) {
//                        playButton.setVisible(false);
//                    }
                    episodeContainer.getChildren().add(playButton);

                    episodesContainer.getChildren().add(episodeContainer);
                }
            }
        }

    }

    public void addCrew() {
        castsBox.getChildren().clear();
        directorsBox.getChildren().clear();
        for (Crew crew : currentSeries.getCrews()) {
            VBox castBox = new VBox();
            ImageView castImage = new ImageView(crew.getPicture());
            double size = 100; // Set desired image size
            castImage.setFitWidth(size);
            castImage.setFitHeight(size);

            // Create a circular clip using a rectangle with rounded corners
            Rectangle clip = new Rectangle(size, size);
            clip.setArcWidth(size);
            clip.setArcHeight(size);
            castImage.setClip(clip); // Apply the clip to the ImageView

            castBox.getChildren().add(castImage);
            Label castName = new Label(crew.getFirstName() + ' ' + crew.getLastName());
            castName.getStyleClass().add("cast-label");
            castBox.getChildren().add(castName);
            castBox.getStyleClass().add("cast-box");
            castBox.setOnMouseClicked(_ -> redirectToCrewPage(crew.getId()));
            if (crew instanceof Cast) {
                castsBox.getChildren().add(castBox);
            } else {
                directorsBox.getChildren().add(castBox);

            }
        }
    }

    public void redirectToEpisodePage(String episodeId) {
//        if(User.getCurrentUser() == null){
//            return;
//        }
        try {
            String css = getClass().getResource("/WatChill/style/Episode.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Series/Episode.fxml"));
            root = loader.load();
            EpisodeController episodeController = loader.getController();
            episodeController.build(episodeId);
            stage = (Stage) episodesContainer.getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redirectToCrewPage(String crewId) {
        try {
            String css = getClass().getResource("/WatChill/style/Crew.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Crew/Crew.fxml"));
            root = loader.load();
            CrewController crewController = loader.getController();
            crewController.build(crewId);
            if (Crew.findById(crewId) instanceof Cast) {
                stage = (Stage) castsBox.getScene().getWindow();
            } else {
                stage = (Stage) directorsBox.getScene().getWindow();
            }
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scrollRight(MouseEvent event) {
        double newHValue = Math.min(castsScrollPane.getHvalue() + 0.4, 1); // Scroll right
        castsScrollPane.setHvalue(newHValue);
    }

    public void scrollLeft(MouseEvent event) {
        double newHValue = Math.min(castsScrollPane.getHvalue() - 0.4, 1); // Scroll right
        castsScrollPane.setHvalue(newHValue);
    }

    public void addToWatchLater(MouseEvent event) {
        if (User.getCurrentUser() instanceof Customer) {
            if (((Customer) User.getCurrentUser()).findMovieWatchLaterIndex(currentSeries.getId()) == -1) {
                ((Customer) User.getCurrentUser()).addToWatchLater(currentSeries);
                watchLaterButton.setImage(new Image(getClass().getResource("/WatChill/Content/Series/media/minus-circle.png").toExternalForm()));
                System.out.println("added");
            } else {
                ((Customer) User.getCurrentUser()).removeFromWatchLater(currentSeries.getId());
                watchLaterButton.setImage(new Image(getClass().getResource("/WatChill/Content/Series/media/plus.png").toExternalForm()));
                System.out.println("removed");
            }
        }
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

    public void build(String seriesId) {
        initializeSeries(seriesId);
    }

}
