package WatChill.Search;

import WatChill.Content.Movie.MovieController;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Series;
import WatChill.Content.Series.SeriesController;
import WatChill.Crew.Crew;
import WatChill.Crew.CrewController;
import WatChill.UserManagement.Customer;
import WatChill.UserManagement.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HeaderController {
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
    ImageView bgImage;
    @FXML
    ImageView profileIcon;
    @FXML
    VBox header;
    @FXML
    VBox profileList;

    Parent root;
    Stage stage;
    Scene scene;

    public void initialize() {
        hideProfileList();
        User currentUser = User.getCurrentUser();
        if (currentUser != null) {
            signInButton.setVisible(false);
            signUpButton.setVisible(false);
            profileIcon.setVisible(true);
        } else {
            signInButton.setVisible(true);
            signUpButton.setVisible(true);
            profileIcon.setVisible(false);
        }
        bgImage.fitHeightProperty().bind((bgImage.fitWidthProperty()));
        for (MenuItem menuItem : searchMenu.getItems()) {
            menuItem.setOnAction(_ -> searchMenu.setText(menuItem.getText()));
        }
        searchMenu.setText(searchMenu.getItems().get(0).getText());
        searchResultsContainer.setManaged(false);
        profileIcon.setFocusTraversable(true);
        profileIcon.setOnMouseClicked(_ -> profileIcon.requestFocus());
        profileIcon.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                displayProfileList();
            } else {
                hideProfileList();
            }
        });
        profileList.addEventFilter(MouseEvent.MOUSE_PRESSED, MouseEvent::consume);
    }

    public void redirectToSignUp(ActionEvent actionEvent) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/User/signup.fxml"));
            root = loader.load();
            scene = ((Node) actionEvent.getSource()).getScene();
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

    public void redirectToLogin(ActionEvent actionEvent) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/User/login.fxml"));
            root = loader.load();
            scene = ((Node) actionEvent.getSource()).getScene();
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

    private void redirectToMoviePage(String movieId) {
        try {
            String css = getClass().getResource("/WatChill/style/Movie.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Movie/Movie.fxml"));
            root = loader.load();

            MovieController movieController = loader.getController();
            movieController.build(movieId);

            scene = searchInput.getScene();
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

    private void redirectToSeriesPage(String seriesId) {
        try {
            String css = getClass().getResource("/WatChill/style/Series.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Series/Series.fxml"));
            root = loader.load();

            SeriesController seriesController = loader.getController();
            seriesController.build(seriesId);

            scene = searchInput.getScene();
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

    private void redirectToCrewPage(String castId) {
        try {
            String css = getClass().getResource("/WatChill/style/Crew.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Crew/Crew.fxml"));
            root = loader.load();

            CrewController crewController = loader.getController();
            crewController.build(castId);

            scene = searchInput.getScene();
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

    public void redirectToProfile(MouseEvent mouseEvent) {
        String path;
        if (User.getCurrentUser() instanceof Customer) {
            path = "/WatChill/User/profile.fxml";
        } else {
            path = "/WatChill/Admin/admin-profile.fxml";
        }
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            root = loader.load();
            scene = searchInput.getScene();
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

            scene = searchInput.getScene();
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

    public void redirectToHome() {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Home/home.fxml"));
            root = loader.load();
            scene = searchInput.getScene();
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

    public void handleSearch(KeyEvent keyEvent) {
//        searchResultsContainer.setLayoutX((searchInput.getScene().getWindow().getWidth() / 4));
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
                    String newRadius = "";

                    searchResult.setOnMouseClicked(_ -> redirectToSeriesPage(series.getId()));

                    if (searchResults.size() == 1) {
                        newRadius = " -fx-background-radius: 50 50 50 50;";
                    } else if (i == 0) {
                        newRadius = " -fx-background-radius: 50 50 0 0;";
                    } else if (i == 2) {
                        newRadius = " -fx-background-radius: 0 0 50 50;";
                    }
                    else if (i == (searchResults.size() - 1)) {
                        newRadius = " -fx-background-radius: 0 0 50 50;";
                    }

                    searchResult.setStyle(searchResult.getStyle() + newRadius);
                    HBox.setMargin(searchResult, new Insets(0, 0, 5, 0));

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
                    String newRadius = "";

                    searchResult.setOnMouseClicked(_ -> redirectToMoviePage(movie.getId()));

                    if (searchResults.size() == 1) {
                        newRadius = " -fx-background-radius: 50 50 50 50;";
                    } else if (i == 0) {
                        newRadius = " -fx-background-radius: 50 50 0 0;";
                    } else if (i == 2) {
                        newRadius = " -fx-background-radius: 0 0 50 50;";
                    }
                    else if (i == (searchResults.size() - 1)) {
                        newRadius = " -fx-background-radius: 0 0 50 50;";
                    }

                    searchResult.setStyle(searchResult.getStyle() + newRadius);
                    VBox.setMargin(searchResult, new Insets(0, 0, 5, 0));

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
                    String newRadius = "";

                    searchResult.setOnMouseClicked(_ -> redirectToCrewPage(crew.getId()));

                    if (searchResults.size() == 1) {
                        newRadius = " -fx-background-radius: 50 50 50 50;";
                    } else if (i == 0) {
                        newRadius = " -fx-background-radius: 50 50 0 0;";
                    } else if (i == 2) {
                        newRadius = " -fx-background-radius: 0 0 50 50;";
                    }
                    else if (i == (searchResults.size() - 1)) {
                        newRadius = " -fx-background-radius: 0 0 50 50;";
                    }

                    searchResult.setStyle(searchResult.getStyle() + newRadius);
                    HBox.setMargin(searchResult, new Insets(0, 0, 5, 0));

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
        searchResultsContainer.setLayoutX((searchInput.getScene().getWindow().getWidth() / 2) - (searchResultsContainer.getMaxWidth() / 2));
    }

    public void displayProfileList() {
        profileList.setVisible(true);
        profileList.setManaged(true);
    }

    public void hideProfileList() {
        profileList.setVisible(false);
        profileList.setManaged(false);
    }

    public void signOut() {
        User.setCurrentUser(null);
        redirectToHome();
    }
}
