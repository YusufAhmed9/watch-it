package WatChill.UserManagement;

import WatChill.Content.AdminCardController;
import WatChill.Content.Content;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Movie.MovieController;
import WatChill.Content.Series.SeriesController;
import WatChill.Crew.Cast.Cast;
import WatChill.Crew.Crew;
import WatChill.Crew.CrewController;
import WatChill.Crew.Director.Director;
import WatChill.UserWatchRecord.UserWatchRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Pattern;

public class temp {
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
    @FXML
    VBox infoContainer;

    @FXML
    VBox moviesAdmin;
    @FXML
    TextField movieTitle;
    @FXML
    Text movieTitleError;
    @FXML
    TextField movieDescription;
    @FXML
    Text movieDescriptionError;
    @FXML
    DatePicker movieReleaseDate;
    @FXML
    Text movieReleaseDateError;
    @FXML
    TextField movieDuration;
    @FXML
    Text movieDurationError;
    @FXML
    TextField movieCountry;
    @FXML
    Text movieCountryError;
    @FXML
    TextField movieBudget;
    @FXML
    Text movieBudgetError;
    @FXML
    TextField movieRevenue;
    @FXML
    Text movieRevenueError;
    @FXML
    TextField movieGenre;
    @FXML
    Text movieGenreError;
    @FXML
    Button movieGenreButton;
    @FXML
    TextField movieLanguage;
    @FXML
    Text movieLanguageError;
    @FXML
    Button movieLanguageButton;
    @FXML
    TextField moviePoster;
    @FXML
    Text moviePosterError;
    @FXML
    Button moviePosterButton;
    @FXML
    VBox moviesContainer;
    @FXML
    Button movieCrewButton;
    @FXML
    Button movieButton;
    @FXML
    Text movieCrewsError;

    @FXML
    VBox seriesAdmin;
    @FXML
    VBox seriesContainer;

    @FXML
    VBox crewAdmin;
    @FXML
    VBox crewContainer;
    @FXML
    TextField crewFirstName;
    @FXML
    Text crewFirstNameError;
    @FXML
    TextField crewLastName;
    @FXML
    Text crewLastNameError;
    @FXML
    DatePicker dateOfBirth;
    @FXML
    Text dateOfBirthError;
    @FXML
    MenuButton genderMenu;
    @FXML
    Text genderError;
    @FXML
    TextField nationality;
    @FXML
    Text nationalityError;
    @FXML
    TextField instagramLink;
    @FXML
    Text instagramLinkError;
    @FXML
    TextField twitterLink;
    @FXML
    Text twitterLinkError;
    @FXML
    MenuButton crewType;
    @FXML
    Text crewTypeError;
    @FXML
    TextField pictureInput;
    @FXML
    Text pictureError;
    @FXML
    Button crewButton;
    @FXML
    Button pictureButton;
    @FXML
    FlowPane movieLanguagesContainer;
    @FXML
    FlowPane movieGenresContainer;

    private Crew currentCrew = null;
    private Movie currentMovie = null;
    private ArrayList<Crew> currentMovieCrews = new ArrayList<>();
    private ArrayList<String> currentMovieLanguages = new ArrayList<>();
    private ArrayList<String> currentMovieGenres = new ArrayList<>();

    Stage stage;
    Parent root;
    Scene scene;


    public void setCurrentCrew(Crew currentCrew) {
        this.currentCrew = currentCrew;
    }

    public Crew getCurrentCrew() {
        return currentCrew;
    }

    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    public Movie getCurrentMovie() {
        return currentMovie;
    }


    public void initialize() {
        initializeHeader();
        displayMyInfo();
    }

    public void displayMyInfo() {
        infoContainer.setVisible(true);
        infoContainer.setManaged(true);
        moviesAdmin.setVisible(false);
        moviesAdmin.setManaged(false);
        seriesAdmin.setVisible(false);
        seriesAdmin.setManaged(false);
        crewAdmin.setVisible(false);
        crewAdmin.setManaged(false);
        User user = User.getCurrentUser();
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        firstNameInput.setText(user.getFirstName());
        lastNameInput.setText(user.getLastName());
    }

    public void displayMovies() {
        currentMovieLanguages.clear();
        currentMovieCrews.clear();
        currentMovieGenres.clear();
        infoContainer.setVisible(false);
        infoContainer.setManaged(false);
        moviesAdmin.setVisible(true);
        moviesAdmin.setManaged(true);
        seriesAdmin.setVisible(false);
        seriesAdmin.setManaged(false);
        crewAdmin.setVisible(false);
        crewAdmin.setManaged(false);
        initializeMovies();
        initializeMoviesInputs();
        moviePosterButton.setOnAction(_ -> {
            handleFileChoose(moviePoster, "/WatChill/Content/Movie/media");
        });
    }

    public void displayCrew() {
        infoContainer.setVisible(false);
        infoContainer.setManaged(false);
        moviesAdmin.setVisible(false);
        moviesAdmin.setManaged(false);
        seriesAdmin.setVisible(false);
        seriesAdmin.setManaged(false);
        crewAdmin.setVisible(true);
        crewAdmin.setManaged(true);
        initializeCrew();
        initializeCrewInputs();
        for (MenuItem item : genderMenu.getItems()) {
            item.setOnAction(_ -> genderMenu.setText(item.getText()));
        }
        for (MenuItem item : crewType.getItems()) {
            item.setOnAction(_ -> crewType.setText(item.getText()));
        }
        pictureButton.setOnAction(_ -> handleFileChoose(pictureInput, "/WatChill/Crew/media"));
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

    public void saveMovie() {
        initializeMovieErrors();
        Movie movie;
        String title = movieTitle.getText();
        String description = movieDescription.getText();
        LocalDate releaseDate = movieReleaseDate.getValue();
        int duration;
        String country = movieCountry.getText();
        double budget;
        double revenue;
        ArrayList<String> languages = currentMovieLanguages;
        ArrayList<String> genres = currentMovieGenres;
        ArrayList<Crew> crews = currentMovieCrews;
        String poster = moviePoster.getText();
        if (title.isEmpty()) {
            movieTitleError.setText("Title is required.");
            return;
        }
        if (description.isEmpty()) {
            movieDescriptionError.setText("Description is required.");
            return;
        }
        if (releaseDate == null || releaseDate.compareTo(LocalDate.now()) > -1) {
            movieReleaseDateError.setText("Invalid release date.");
            return;
        }
        try {
            duration = Integer.parseInt(movieDuration.getText());
        } catch (NumberFormatException e) {
            movieDurationError.setText("Invalid Duration");
            return;
        }
        if (duration <= 0) {
            movieDurationError.setText("Invalid duration.");
            return;
        }
        if (country.isEmpty()) {
            movieCountryError.setText("Country is required.");
            return;
        }
        try {
            budget = Double.parseDouble(movieBudget.getText());
        } catch (Exception e) {
            movieBudgetError.setText("Invalid budget.");
            return;
        }
        if (budget <= 0) {
            movieBudgetError.setText("Invalid budget.");
            return;
        }
        try {
            revenue = Double.parseDouble(movieRevenue.getText());
        } catch (Exception e) {
            movieRevenueError.setText("Invalid revenue.");
            return;
        }
        if (revenue <= 0) {
            movieRevenueError.setText("Invalid revenue.");
            return;
        }
        if (languages.isEmpty()) {
            movieLanguageError.setText("At least 1 language is required.");
            return;
        }
        if (genres.isEmpty()) {
            movieGenreError.setText("At least 1 genre is required.");
            return;
        }
        if (poster.isEmpty()) {
            moviePosterError.setText("Poster is required.");
            return;
        }
        if (crews.isEmpty()) {
            movieCrewsError.setText("At least 1 crew member is required.");
            return;
        }
        if (currentMovie != null) {
            movie = currentMovie;
            movie.setTitle(title);
            movie.setReleaseDate(releaseDate);
            movie.setDuration(duration);
            movie.setLanguages(languages);
            movie.setGenres(genres);
            movie.setCrews(crews);
            movie.setCountry(country);
            movie.setBudget(budget);
            movie.setRevenue(revenue);
            movie.setPoster(poster);
            movie.setDescription(description);
        }
        else {
            movie = new Movie(UUID.randomUUID().toString(), title, releaseDate, duration, languages, genres, crews, country, budget, revenue, poster, 0, description);
        }
        movie.save();
        displayMovies();
    }

    public void addMovieCrew() {

    }

    public void saveCrew() {
        crewFirstNameError.setText("");
        crewLastNameError.setText("");
        dateOfBirthError.setText("");
        genderError.setText("");
        nationalityError.setText("");
        instagramLinkError.setText("");
        twitterLinkError.setText("");
        crewTypeError.setText("");
        pictureError.setText("");
        String firstName = crewFirstName.getText();
        String lastName = crewLastName.getText();
        LocalDate date = dateOfBirth.getValue();
        String gender = genderMenu.getText();
        String nationalityValue = nationality.getText();
        String instagram = instagramLink.getText();
        String twitter = twitterLink.getText();
        String type = crewType.getText();
        String picturePath = pictureInput.getText();
        if (firstName.isEmpty()) {
            crewFirstNameError.setText("First name is required.");
            return;
        }
        if (lastName.isEmpty()) {
            crewLastNameError.setText("Last name is required.");
            return;
        }
        if (date == null || date.compareTo(LocalDate.now()) > -1) {
            dateOfBirthError.setText("Invalid date of birth.");
            return;
        }
        if (gender.equals("Gender")) {
            genderError.setText("Gender is required.");
            return;
        }
        if (!isInputValid(instagram, "^https?://www\\.instagram\\.com/.+$")) {
            instagramLinkError.setText("Invalid instagram link.");
            return;
        }
        if (!isInputValid(twitter, "^https?://twitter\\.com/.+$")) {
            twitterLinkError.setText("Invalid twitter link.");
            return;
        }
        if (nationalityValue.isEmpty()) {
            nationalityError.setText("Nationality is required.");
            return;
        }
        if (type.equals("Crew Type")) {
            crewTypeError.setText("Crew type is required.");
            return;
        }
        if (picturePath.isEmpty()) {
            pictureError.setText("Picture is required.");
            return;
        }
        Crew crew;
        if (getCurrentCrew() != null) {
            crew = getCurrentCrew();
            if (crewType.equals("Director")) {
                crew = new Director(crew.getId(), firstName, lastName, date, gender, nationalityValue, instagram, twitter, crew.getContentCreated(), picturePath);
            }
            else {
                crew = new Cast(crew.getId(), firstName, lastName, date, gender, nationalityValue, instagram, twitter, crew.getContentCreated(), picturePath);
            }
        }
        else {
            if (crewType.equals("Director")) {
                crew = new Director(firstName, lastName, date, gender, nationalityValue, instagram, twitter, picturePath);
            }
            else {
                crew = new Cast(firstName, lastName, date, gender, nationalityValue, instagram, twitter, picturePath);
            }
        }
        crew.saveCrew();
        crewButton.setText("Create");
        displayCrew();
    }

    public void handleFileChoose(TextField fileInput, String directoryPath) {
        // Create a FileChooser instance
        FileChooser fileChooser = new FileChooser();

        // Set file chooser properties
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        // Get the current window (Stage)
        Stage stage = (Stage) moviesAdmin.getScene().getWindow();

        // Show the FileChooser dialog
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Check if a file was selected
        if (selectedFile != null) {
            File destinationDirectory = new File("src/main/resources" + directoryPath);
            Path destinationPath = destinationDirectory.toPath().resolve(selectedFile.getName());

            try {
                // Copy the file to the destination directory
                Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
            fileInput.setText(directoryPath + selectedFile.getName());
        } else {
            fileInput.setText("");
        }
    }

    private void initializeCrew() {
        crewContainer.getChildren().clear();
        for (Crew crew : Crew.retrieveCrews()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/admin-card.fxml"));
                HBox card = loader.load();
                AdminCardController adminCardController = loader.getController();
                adminCardController.setData(
                        crew.getPicture(),
                        crew.getFirstName() + " " + crew.getLastName(),
                        crew.getNationality(),
                        crew.getDateOfBirth().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")),
                        crew.getId(),
                        () -> crew.delete(),
                        () -> setEditedCrew(crew.getId()),
                        () -> redirectToCrewPage(crew.getId()),
                        () -> initializeCrew()
                );
                crewContainer.getChildren().add(card);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setEditedCrew(String crewId) {
        Crew crew = Crew.findById(crewId);
        setCurrentCrew(crew);
        crewFirstName.setText(crew.getFirstName());
        crewLastName.setText(crew.getLastName());
        dateOfBirth.setValue(crew.getDateOfBirth());
        genderMenu.setText(crew.getGender());
        instagramLink.setText(crew.getInstagramLink());
        twitterLink.setText(crew.getTwitterLink());
        nationality.setText(crew.getNationality());
        crewType.setText(crew instanceof Director ? "Director" : "Cast");
        pictureInput.setText(crew.getPicture());
        crewButton.setText("Save");
    }

    private void setEditedMovie(String movieId) {
        Movie movie = Movie.findById(movieId);
        setCurrentMovie(movie);
        movieTitle.setText(movie.getTitle());
        movieDescription.setText(movie.getDescription());
        movieReleaseDate.setValue(movie.getReleaseDate());
        movieDuration.setText(((Integer) movie.getDuration()).toString());
        movieCountry.setText(movie.getCountry());
        movieDescription.setText(movie.getDescription());
        movieBudget.setText(((Double) movie.getBudget()).toString());
        movieRevenue.setText(((Double) movie.getRevenue()).toString());
        moviePoster.setText(movie.getPoster());

        currentMovieCrews = new ArrayList<>(movie.getCrews());
        currentMovieLanguages = new ArrayList<>(movie.getLanguages());
        currentMovieGenres = new ArrayList<>(movie.getGenres());
        movieGenresContainer.getChildren().clear();
        movieLanguagesContainer.getChildren().clear();
        for (String genre : currentMovieGenres) {
            Label label = new Label(genre);
            label.getStyleClass().add("flowpane-label");
            movieGenresContainer.getChildren().add(label);
        }
        for (String language : currentMovieLanguages) {
            Label label = new Label(language);
            label.getStyleClass().add("flowpane-label");
            movieLanguagesContainer.getChildren().add(label);
        }
        movieButton.setText("Save");
        movieCrewButton.setText("Edit Crew");
    }

    private boolean isInputValid(String input, String regex) {
        return Pattern.matches(regex, input);
    }

    private void initializeCrewInputs() {
        crewFirstName.setText("");
        crewLastName.setText("");
        dateOfBirth.setValue(null);
        genderMenu.setText("Gender");
        nationality.setText("");
        instagramLink.setText("");
        twitterLink.setText("");
        crewType.setText("Crew Type");
        pictureInput.setText("");
    }

    private void initializeMovies() {
        moviesContainer.getChildren().clear();
        for (Movie movie : Movie.retrieveMovies()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/admin-card.fxml"));
                HBox card = loader.load();
                AdminCardController adminCardController = loader.getController();
                adminCardController.setData(
                        movie.getPoster(),
                        movie.getTitle(),
                        movie.getDescription(),
                        movie.getReleaseDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")),
                        movie.getId(),
                        () -> deleteMovie(movie.getId()),
                        () -> setEditedMovie(movie.getId()),
                        () -> redirectToMoviePage(movie.getId()),
                        () -> initializeMovies()
                );
                moviesContainer.getChildren().add(card);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeMoviesInputs() {
        movieButton.setText("Create");
        movieCrewButton.setText("Add Crew");
        movieTitle.setText("");
        movieDescription.setText("");
        movieReleaseDate.setValue(null);
        movieDuration.setText("");
        movieCountry.setText("");
        movieBudget.setText("");
        movieRevenue.setText("");
        movieLanguage.setText("");
        movieGenre.setText("");
        moviePoster.setText("");
        movieLanguagesContainer.getChildren().clear();
        movieGenresContainer.getChildren().clear();
    }

    private void initializeMovieErrors() {
        movieTitleError.setText("");
        movieDescriptionError.setText("");
        movieReleaseDateError.setText("");
        movieDurationError.setText("");
        movieCountryError.setText("");
        movieBudgetError.setText("");
        movieRevenueError.setText("");
        movieLanguageError.setText("");
        movieGenreError.setText("");
        moviePosterError.setText("");
        movieCrewsError.setText("");
    }

    private void deleteMovie(String movieId) {
        Content content = Movie.findById(movieId);
        for (Crew crew : Crew.retrieveCrews()) {
            for (String contentCreatedId : crew.getContentCreated()) {
                if (contentCreatedId.equals(content.getId())) {
                    crew.getContentCreated().remove(contentCreatedId);
                }
            }
        }
        for (User user : User.getUsers()) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                if (customer.findMovieWatchLaterIndex(content.getId()) != -1) {
                    customer.removeFromWatchLater(content.getId());
                }
            }
        }
        for (UserWatchRecord userWatchRecord : UserWatchRecord.retrieveRecords()) {
            if (userWatchRecord.getWatchedContent() instanceof Movie) {
                Movie movie = (Movie) userWatchRecord.getWatchedContent();
                if (movie.getId().equals(content.getId())) {
                    userWatchRecord.delete();
                }
            }
        }
    }

    public void redirectToMoviePage(String movieId) {
        try {
            String css = getClass().getResource("/WatChill/style/Movie.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Movie/movie.fxml"));

            MovieController movieController = loader.getController();
            movieController.build(movieId);

            root = loader.load();
            scene = moviesAdmin.getScene();
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

    public void redirectToSeriesPage(String seriesId) {
        try {
            String css = getClass().getResource("/WatChill/style/Series.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Series/series.fxml"));
            root = loader.load();
            SeriesController seriesController = loader.getController();
            seriesController.build(seriesId);
            scene = moviesAdmin.getScene();
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

    public void redirectToCrewPage(String crewId) {
        try {
            String css = getClass().getResource("/WatChill/style/Crew.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Crew/Crew.fxml"));
            root = loader.load();
            CrewController crewController = loader.getController();
            crewController.build(crewId);
            scene = moviesAdmin.getScene();
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

    public void addMovieLanguage() {
        if (movieLanguage.getText().isEmpty() || currentMovieLanguages.contains(movieLanguage.getText())) {
            return;
        }
        Label label = new Label(movieLanguage.getText());
        label.getStyleClass().add("flowpane-label");
        movieLanguagesContainer.getChildren().add(label);
        currentMovieLanguages.add(movieLanguage.getText());
        movieLanguage.setText("");
    }

    public void addMovieGenre() {
        if (movieGenre.getText().isEmpty() || currentMovieGenres.contains(movieGenre.getText())) {
            return;
        }
        Label label = new Label(movieGenre.getText());
        label.getStyleClass().add("flowpane-label");
        movieGenresContainer.getChildren().add(label);
        currentMovieGenres.add(movieGenre.getText());
        movieGenre.setText("");
    }

    public void clearMovieLanguages() {
        currentMovieLanguages.clear();
        movieLanguagesContainer.getChildren().clear();
    }

    public void clearMovieGenres() {
        currentMovieGenres.clear();
        movieGenresContainer.getChildren().clear();
    }
}
