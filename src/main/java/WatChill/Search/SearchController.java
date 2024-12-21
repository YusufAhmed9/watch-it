package WatChill.Search;

import WatChill.Content.Movie.MovieController;
import WatChill.Crew.Cast.Cast;
import WatChill.Content.Content;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Series;
import WatChill.Content.Series.SeriesController;
import WatChill.Crew.Crew;
import WatChill.Crew.CrewController;
import WatChill.Crew.Director.Director;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SearchController {

    @FXML
    VBox searchResultsContainer;
    @FXML
    MenuButton sortMenu;
    @FXML
    MenuButton genreMenu;
    @FXML
    MenuButton languageMenu;
    @FXML
    Button clearFiltersButton;
    @FXML
    HBox filtersContainer;
    @FXML
    BorderPane searchBorderPane;


    private ArrayList<Content> contents = new ArrayList<>();
    private ArrayList<Crew> crews = new ArrayList<>();
    private String searchType;
    private String searchQuery;

    Parent root;
    Scene scene;
    Stage stage;

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public ArrayList<Content> getContents() {
        return contents;
    }

    public void setContents(ArrayList<Content> contents) {
        this.contents = contents;
    }

    public ArrayList<Crew> getCrews() {
        return crews;
    }

    public void setCrews(ArrayList<Crew> crews) {
        this.crews = crews;
    }

    public void build(String searchQuery, String searchType) {
        initializeHeader();
        setSearchQuery(searchQuery);
        setSearchType(searchType);
        if (getSearchType().equals("Series")) {
            sortMenu.getItems().removeLast();
        }
        clearFiltersButton.setOnAction(_ -> {
            sortMenu.setText("Sort By");
            genreMenu.setText("Genre");
            languageMenu.setText("Language");
            setInitialSearchResults();
            renderSearchResults();
        });
        for (MenuItem menuItem : sortMenu.getItems()) {
            menuItem.setOnAction(_ -> {
                sortMenu.setText(menuItem.getText());
                handleContentSort();
            });
        }
        for (String genre : Content.allContentGenres) {
            MenuItem menuItem = new MenuItem();
            menuItem.setText(genre);
            menuItem.setOnAction(_ -> {
                genreMenu.setText(genre);
                handleContentFilter();
            });
            genreMenu.getItems().add(menuItem);
        }
        for (String language : Content.allContentLanguages) {
            MenuItem menuItem = new MenuItem();
            menuItem.setText(language);
            menuItem.setOnAction(_ -> {
                languageMenu.setText(language);
                handleContentFilter();
            });
            languageMenu.getItems().add(menuItem);
        }
        setInitialSearchResults();
        renderSearchResults();
    }

    private void handleContentSort() {
        String sortType = sortMenu.getText();
        if (sortType.equals("Rating")) {
            if (getSearchType().equals("Series")) {
                List<Series> series = new ArrayList<>(contents.stream().filter(Series.class::isInstance).map(Series.class::cast).toList());
                series.sort(Comparator.comparing(Series::getRating).reversed());
                contents = new ArrayList<>(series);
            }
            else {
                List<Movie> movies = new ArrayList<>(contents.stream().filter(Movie.class::isInstance).map(Movie.class::cast).toList());
                movies.sort(Comparator.comparing(Movie::getRating).reversed());
                contents = new ArrayList<>(movies);
            }
        }
        else if (sortType.equals("Views")) {
            contents.sort(Comparator.comparingInt(Content::getViewsCount).reversed());
        }
        else if (sortType.equals("Release Date")) {
            contents.sort(Comparator.comparing(Content::getReleaseDate).reversed());
        }
        else if (sortType.equals("Duration")) {
            List<Movie> movies = new ArrayList<>(contents.stream().filter(Movie.class::isInstance).map(Movie.class::cast).toList());
            movies.sort(Comparator.comparing(Movie::getDuration).reversed());
            contents = new ArrayList<>(movies);
        }
        else if (sortType.equals("Name A-Z")) {
            contents.sort(Comparator.comparing(Content::getTitle));
        }
        renderSearchResults();
    }

    private void handleContentFilter() {
        ArrayList<Content> filteredContents;
        String filterGenre = genreMenu.getText();
        String filterLanguage = languageMenu.getText();
        if (getSearchType().equals("Movies")) {
            filteredContents = new ArrayList<>(Movie.searchByTitle(getSearchQuery()));
        }
        else {
            filteredContents = new ArrayList<>(Series.searchByTitle(getSearchQuery()));
        }
        if (!filterGenre.equals("Genre")) {
            filteredContents.removeIf(content -> !content.getGenres().contains(filterGenre));
        }
        if (!filterLanguage.equals("Language")) {
            filteredContents.removeIf(content -> !content.getLanguages().contains(filterLanguage));
        }
        setContents(filteredContents);
        handleContentSort();
    }

    private void renderSearchResults() {
        searchResultsContainer.getChildren().clear();
        if (searchType.equals("Series")) {
            if (getContents().isEmpty()) {
                displayNoResultsMessage();
                return;
            }
            for (Content content : getContents()) {
                Series series = (Series) content;
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
            }
        }
        else if (searchType.equals("Movies")) {
            if (getContents().isEmpty()) {
                displayNoResultsMessage();
                return;
            }
            for (Content content : getContents()) {
                Movie movie = (Movie) content;
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
            }
        }
        else {
            if (getCrews().isEmpty()) {
                displayNoResultsMessage();
                return;
            }
            for (Crew crew : getCrews()) {
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
            }
        }
    }

    private void redirectToMoviePage(String movieId) {
        try {
            String css = getClass().getResource("/WatChill/style/Movie.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Movie/movie.fxml"));
            root = loader.load();

            MovieController movieController = loader.getController();
            movieController.build(movieId);

            scene = searchResultsContainer.getScene();
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

    private void redirectToSeriesPage(String seriesId) {
        try {
            String css = getClass().getResource("/WatChill/style/Series.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Series/Series.fxml"));
            root = loader.load();

            SeriesController seriesController = loader.getController();
            seriesController.build(seriesId);

            scene = searchResultsContainer.getScene();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Crew/crew.fxml"));
            root = loader.load();

            CrewController crewController = loader.getController();
            crewController.build(castId);

            scene = searchResultsContainer.getScene();
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

    private void setInitialSearchResults() {
        if (getSearchType().equals("Series")) {
            setContents(new ArrayList<>(Series.searchByTitle(getSearchQuery())));
        }
        else if (getSearchType().equals("Movies")) {
            setContents(new ArrayList<>(Movie.searchByTitle(getSearchQuery())));
        }
        else if (getSearchType().equals("Crew")) {
            ((VBox)filtersContainer.getParent()).getChildren().remove(filtersContainer);
            setCrews(new ArrayList<>(Cast.searchByName(getSearchQuery())));
        }
    }

    private void displayNoResultsMessage() {
        Label label = new Label();
        label.setText("No Results Found");
        label.getStyleClass().add("no-results");
        searchResultsContainer.getChildren().add(label);
    }

    private void initializeHeader() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Home/header.fxml"));
            Pane header = loader.load();
            searchBorderPane.setTop(header);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
