package WatChill.Search;

import WatChill.Cast.Cast;
import WatChill.Content.Content;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Series;
import WatChill.Content.Series.SeriesController;
import WatChill.Director.Director;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

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

    private ArrayList<Content> contents = new ArrayList<>();
    private ArrayList<Director> directors = new ArrayList<>();
    private ArrayList<Cast> casts = new ArrayList<>();
    private String searchType;
    private String searchQuery;
//    private String filterGenre = "";
//    private String filterLanguage = "";

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

    public ArrayList<Cast> getCasts() {
        return casts;
    }

    public void setCasts(ArrayList<Cast> casts) {
        this.casts = casts;
    }

    public ArrayList<Content> getContents() {
        return contents;
    }

    public ArrayList<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<Director> directors) {
        this.directors = directors;
    }

    public void setContents(ArrayList<Content> contents) {
        this.contents = contents;
    }


    public void build(String searchQuery, String searchType) {
        setSearchQuery(searchQuery);
        setSearchType(searchType);
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
            // TODO
        }
        else if (sortType.equals("Views")) {
            contents.sort(Comparator.comparingInt(Content::getViewsCount).reversed());
        }
        else if (sortType.equals("Release Date")) {
            contents.sort(Comparator.comparing(Content::getReleaseDate).reversed());
        }
        else if (sortType.equals("Duration")) {
//            contents.sort(Comparator.comparing(Content::getReleaseDate).reversed());
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
            filteredContents = new ArrayList<>(Movie.searchMoviesByName(getSearchQuery()));
        }
        else {
            filteredContents = new ArrayList<>(Series.searchSeriesByTitle(getSearchQuery()));
        }
        if (!filterGenre.equals("Genre")) {
            filteredContents.removeIf(content -> !content.getGenres().contains(filterGenre));
        }
        if (!filterGenre.equals("Language")) {
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
            System.out.println(searchResultsContainer.getChildren());
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
                    searchResultController.setData(movie.getPoster(), movie.getTitle(), movie.getReleaseDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
                    searchResultsContainer.getChildren().add(searchResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if (searchType.equals("Casts")) {
            if (getCasts().isEmpty()) {
                displayNoResultsMessage();
                return;
            }
            for (Cast cast : getCasts()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Search/searchResult.fxml"));
                    HBox searchResult = loader.load();

                    searchResult.setOnMouseClicked(_ -> redirectToCastPage(cast.getId()));

                    SearchResultController searchResultController = loader.getController();
                    searchResultController.setData(cast.getFirstName() + " " + cast.getLastName(), cast.getNationality(), cast.getDateOfBirth().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
                    searchResultsContainer.getChildren().add(searchResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            if (getDirectors().isEmpty()) {
                displayNoResultsMessage();
                return;
            }
            for (Director director : getDirectors()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Search/searchResult.fxml"));
                    HBox searchResult = loader.load();

                    searchResult.setOnMouseClicked(_ -> redirectToDirectorPage(director.getId()));

                    SearchResultController searchResultController = loader.getController();
                    searchResultController.setData(director.getFirstName() + " " + director.getLastName(), director.getNationality(), director.getDateOfBirth().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
                    searchResultsContainer.getChildren().add(searchResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void redirectToMoviePage(String movieId) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Movie/movie.fxml"));
            root = loader.load();

            stage = (Stage) searchResultsContainer.getScene().getWindow();
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

            stage = (Stage) searchResultsContainer.getScene().getWindow();
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

            stage = (Stage) searchResultsContainer.getScene().getWindow();
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
            stage = (Stage) searchResultsContainer.getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInitialSearchResults() {
        if (getSearchType().equals("Series")) {
            setContents(new ArrayList<>(Series.searchSeriesByTitle(getSearchQuery())));
        }
        else if (getSearchType().equals("Movies")) {
            setContents(new ArrayList<>(Movie.searchMoviesByName(getSearchQuery())));
        }
        else if (getSearchType().equals("Casts")) {
            ((HBox)filtersContainer.getParent()).getChildren().remove(filtersContainer);
            setCasts(new ArrayList<>(Cast.searchCastsByName(getSearchQuery())));
        }
        else {
            ((HBox)filtersContainer.getParent()).getChildren().remove(filtersContainer);
            setDirectors(new ArrayList<>(Director.searchDirectorsByName(getSearchQuery())));
        }
    }

    private void displayNoResultsMessage() {
        Label label = new Label();
        label.setText("No Results Found");
        label.getStyleClass().add("no-results");
        searchResultsContainer.getChildren().add(label);
    }
}
