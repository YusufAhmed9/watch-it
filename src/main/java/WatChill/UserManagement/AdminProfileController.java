package WatChill.UserManagement;

import WatChill.Content.AdminCardController;
import WatChill.Content.Content;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Movie.MovieController;
import WatChill.Content.Series.*;
import WatChill.Crew.AddCrewController;
import WatChill.Crew.Cast.Cast;
import WatChill.Crew.Crew;
import WatChill.Crew.CrewController;
import WatChill.Crew.Director.Director;
import WatChill.Subscription.Subscription;
import WatChill.UserWatchRecord.UserWatchRecord;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class AdminProfileController {

    @FXML
    VBox contentAdmin;
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
    VBox mainContainer;

    @FXML
    VBox contentsAdmin;
    @FXML
    TextField contentTitle;
    @FXML
    Text contentTitleError;
    @FXML
    TextField contentDescription;
    @FXML
    Text contentDescriptionError;
    @FXML
    DatePicker contentReleaseDate;
    @FXML
    Text contentReleaseDateError;
    @FXML
    TextField contentDuration;
    @FXML
    Text contentDurationError;
    @FXML
    TextField contentCountry;
    @FXML
    Text contentCountryError;
    @FXML
    TextField contentBudget;
    @FXML
    Text contentBudgetError;
    @FXML
    TextField contentRevenue;
    @FXML
    Text contentRevenueError;
    @FXML
    TextField contentGenre;
    @FXML
    Text contentGenreError;
    @FXML
    Button contentGenreButton;
    @FXML
    TextField contentLanguage;
    @FXML
    Text contentLanguageError;
    @FXML
    Button contentLanguageButton;
    @FXML
    TextField contentPoster;
    @FXML
    Text contentPosterError;
    @FXML
    Button contentPosterButton;
    @FXML
    VBox moviesContainer;
    @FXML
    Button contentCrewButton;
    @FXML
    Button contentButton;
    @FXML
    Text contentCrewsError;

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
    FlowPane contentLanguagesContainer;
    @FXML
    FlowPane contentGenresContainer;
    @FXML
    VBox durationContainer;
    @FXML
    MenuButton contentType;

    @FXML
    VBox seasonAdmin;
    @FXML
    VBox episodesContainer;
    @FXML
    TextField seasonTitle;
    @FXML
    TextField seasonDescription;
    @FXML
    DatePicker seasonReleaseYear;
    @FXML
    Text seasonTitleError;
    @FXML
    Text seasonDescriptionError;
    @FXML
    Text seasonReleaseYearError;
    @FXML
    MenuButton seriesMenu;
    @FXML
    VBox seasonContainer;
    @FXML
    Text seriesMenuError;
    @FXML
    TextField episodeTitle;
    @FXML
    Text episodeTitleError;
    @FXML
    TextField episodeDescription;
    @FXML
    Text episodeDescriptionError;
    @FXML
    TextField episodeDuration;
    @FXML
    Text episodeDurationError;
    @FXML
    DatePicker episodeReleaseDate;
    @FXML
    Text episodeReleaseDateError;
    @FXML
    MenuButton episodeSeriesMenu;
    @FXML
    Text episodeSeriesMenuError;
    @FXML
    MenuButton episodeSeasonMenu;
    @FXML
    Text episodeSeasonMenuError;
    @FXML
    TextField episodePoster;
    @FXML
    Text episodePictureError;
    @FXML
    Button episodePosterButton;
    @FXML
    VBox episodeAdmin;

    @FXML
    VBox subscriptionsAdmin;
    @FXML
    BarChart<String, Double> monthsChart;
    @FXML
    PieChart plansPie;

    private Season currentSeason = null;
    private Episode currentEpisode = null;

    private Crew currentCrew = null;
    private Content currentContent = null;
    private ArrayList<Crew> currentContentCrews;
    private ArrayList<String> currentContentLanguages;
    private ArrayList<String> currentContentGenres;

    Stage stage;
    Parent root;
    Scene scene;


    public void setCurrentCrew(Crew currentCrew) {
        this.currentCrew = currentCrew;
    }

    public Crew getCurrentCrew() {
        return currentCrew;
    }

    public void setCurrentContent(Content currentContent) {
        this.currentContent = currentContent;
    }

    public Content getCurrentContent() {
        return currentContent;
    }


    public void initialize() {
        initializeHeader();
        displayMyInfo();
    }

    public void displayMyInfo() {
        infoContainer.setVisible(true);
        infoContainer.setManaged(true);
        contentsAdmin.setVisible(false);
        contentsAdmin.setManaged(false);
        crewAdmin.setVisible(false);
        crewAdmin.setManaged(false);
        subscriptionsAdmin.setVisible(false);
        subscriptionsAdmin.setManaged(false);
        episodeAdmin.setVisible(false);
        episodeAdmin.setManaged(false);
        seasonAdmin.setVisible(false);
        seasonAdmin.setManaged(false);
        User user = User.getCurrentUser();
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        firstNameInput.setText(user.getFirstName());
        lastNameInput.setText(user.getLastName());
    }

    public void displayContent() {
        currentContentGenres = new ArrayList<>();
        currentContentCrews = new ArrayList<>();
        currentContentLanguages = new ArrayList<>();
        infoContainer.setVisible(false);
        infoContainer.setManaged(false);
        contentsAdmin.setVisible(true);
        contentsAdmin.setManaged(true);
        crewAdmin.setVisible(false);
        crewAdmin.setManaged(false);
        subscriptionsAdmin.setVisible(false);
        subscriptionsAdmin.setManaged(false);
        episodeAdmin.setVisible(false);
        episodeAdmin.setManaged(false);
        seasonAdmin.setVisible(false);
        seasonAdmin.setManaged(false);
        initializeMovies();
        initializeSeries();
        initializeContentsInputs();
        contentPosterButton.setOnAction(_ -> {
            handleFileChoose(contentPoster, "/WatChill/Content/media/");
        });
        for (MenuItem item : contentType.getItems()) {
            item.setOnAction(_ -> {
                contentType.setText(item.getText());
                if (contentType.getText().equals("Movie")) {
                    durationContainer.setVisible(true);
                    durationContainer.setManaged(true);
                }
                else {
                    durationContainer.setManaged(false);
                    durationContainer.setVisible(false);
                }
            });
        }
    }

    public void displaySubscriptions() {
        infoContainer.setVisible(false);
        infoContainer.setManaged(false);
        contentsAdmin.setVisible(false);
        contentsAdmin.setManaged(false);
        crewAdmin.setVisible(false);
        crewAdmin.setManaged(false);
        subscriptionsAdmin.setVisible(true);
        subscriptionsAdmin.setManaged(true);
        episodeAdmin.setVisible(false);
        episodeAdmin.setManaged(false);
        seasonAdmin.setVisible(false);
        seasonAdmin.setManaged(false);
        plansPie.getData().clear();
        for (Map.Entry<String, Integer> entry : Subscription.getPlansSubscriptions().entrySet()) {
            plansPie.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        CategoryAxis xAxis = (CategoryAxis) monthsChart.getXAxis();
        xAxis.setLabel("Months");
        xAxis.setTickLabelRotation(45);
        xAxis.setAutoRanging(false);
        xAxis.setCategories(FXCollections.observableArrayList(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        ));

        monthsChart.getYAxis().setLabel("Revenues");
        monthsChart.getXAxis().setLabel("Months");
        monthsChart.getData().clear();
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Monthly Revenues");

        for (Map.Entry<String, Double> entry : Subscription.getMonthlyRevenues().entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        monthsChart.getData().add(series);
    }

    public void displayCrew() {
        infoContainer.setVisible(false);
        infoContainer.setManaged(false);
        contentsAdmin.setVisible(false);
        contentsAdmin.setManaged(false);
        crewAdmin.setVisible(true);
        crewAdmin.setManaged(true);
        subscriptionsAdmin.setVisible(false);
        subscriptionsAdmin.setManaged(false);
        episodeAdmin.setVisible(false);
        episodeAdmin.setManaged(false);
        seasonAdmin.setVisible(false);
        seasonAdmin.setManaged(false);
        initializeCrew();
        initializeCrewInputs();
        for (MenuItem item : genderMenu.getItems()) {
            item.setOnAction(_ -> genderMenu.setText(item.getText()));
        }
        for (MenuItem item : crewType.getItems()) {
            item.setOnAction(_ -> crewType.setText(item.getText()));
        }
        pictureButton.setOnAction(_ -> handleFileChoose(pictureInput, "/WatChill/Crew/"));
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

    public void saveContent() {
        initializeContentErrors();
        String title = contentTitle.getText();
        String description = contentDescription.getText();
        LocalDate releaseDate = contentReleaseDate.getValue();
        int duration = 0;
        String country = contentCountry.getText();
        double budget;
        double revenue;
        ArrayList<String> languages = currentContentLanguages;
        ArrayList<String> genres = currentContentGenres;
        ArrayList<Crew> crews = currentContentCrews;
        String poster = contentPoster.getText();
        if (title.isEmpty()) {
            contentTitleError.setText("Title is required.");
            return;
        }
        if (description.isEmpty()) {
            contentDescriptionError.setText("Description is required.");
            return;
        }
        if (releaseDate == null || releaseDate.compareTo(LocalDate.now()) > -1) {
            contentReleaseDateError.setText("Invalid release date.");
            return;
        }
        if(contentType.getText().equals("Movie")) {
            try {
                duration = Integer.parseInt(contentDuration.getText());
            } catch (NumberFormatException e) {
                contentDurationError.setText("Invalid Duration");
                return;
            }
            if (duration <= 0) {
                contentDurationError.setText("Invalid duration.");
                return;
            }
        }
        if (country.isEmpty()) {
            contentCountryError.setText("Country is required.");
            return;
        }
        try {
            budget = Double.parseDouble(contentBudget.getText());
        } catch (Exception e) {
            contentBudgetError.setText("Invalid budget.");
            return;
        }
        if (budget <= 0) {
            contentBudgetError.setText("Invalid budget.");
            return;
        }
        try {
            revenue = Double.parseDouble(contentRevenue.getText());
        } catch (Exception e) {
            contentRevenueError.setText("Invalid revenue.");
            return;
        }
        if (revenue <= 0) {
            contentRevenueError.setText("Invalid revenue.");
            return;
        }
        if (languages.isEmpty()) {
            contentLanguageError.setText("At least 1 language is required.");
            return;
        }
        if (genres.isEmpty()) {
            contentGenreError.setText("At least 1 genre is required.");
            return;
        }
        if (poster.isEmpty()) {
            contentPosterError.setText("Poster is required.");
            return;
        }
        if (crews.isEmpty()) {
            contentCrewsError.setText("At least 1 crew member is required.");
            return;
        }
        Content content;
        if (currentContent != null) {
            if (currentContent instanceof Movie) {
                content = new Movie(currentContent.getId(), title, releaseDate, duration, languages, genres, crews, country, budget, revenue, poster, currentContent.getViewsCount(), description);
            }
            else {
                content = new Series(currentContent.getId(), title, releaseDate, ((Series) currentContent).getSeasons(), description, languages, country, genres, crews, poster, budget, revenue);
            }
            for (Crew crew : Crew.retrieveCrews()) {
                crew.getContentCreated().remove(content.getId());
            }
            for (Crew crew : content.getCrews()) {
                crew.getContentCreated().add(content.getId());
            }
        }
        else {
            if (contentType.getText().equals("Movie")) {
                content = new Movie(UUID.randomUUID().toString(), title, releaseDate, duration, languages, genres, crews, country, budget, revenue, poster, 0, description);
            }
            else {
                content = new Series(UUID.randomUUID().toString(), title, releaseDate, new ArrayList<>(), description, languages, country, genres, crews, poster, budget, revenue);
            }
            for (Crew crew : content.getCrews()) {
                crew.getContentCreated().add(content.getId());
            }
        }
        if (content instanceof Movie) {
            ((Movie) content).save();
        }
        else {
            ((Series) content).save();
        }
        displayContent();
    }

    public void addContentCrew() {
        try {
            ArrayList <Crew> crews = new ArrayList<>(currentContentCrews);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Admin/AddCrew.fxml"));
            StackPane addCrew = loader.load();
            AddCrewController addCrewController = loader.getController();
            addCrewController.build(
                crews,
                () -> {
                    mainContainer.getChildren().remove(addCrew);
                    contentsAdmin.setVisible(true);
                    contentsAdmin.setManaged(true);
                    currentContentCrews = crews;
                },
                () -> {
                    mainContainer.getChildren().remove(addCrew);
                    contentsAdmin.setVisible(true);
                    contentsAdmin.setManaged(true);
                }
            );
            contentsAdmin.setVisible(false);
            contentsAdmin.setManaged(false);
            mainContainer.getChildren().add(addCrew);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
            if (crewType.getText().equals("Director")) {
                crew = new Director(crew.getId(), firstName, lastName, date, gender, nationalityValue, instagram, twitter, crew.getContentCreated(), picturePath);
            }
            else {
                crew = new Cast(crew.getId(), firstName, lastName, date, gender, nationalityValue, instagram, twitter, crew.getContentCreated(), picturePath);
            }
            for (Movie movie : Movie.retrieveMovies()) {
                for (int i = 0; i <  movie.getCrews().size(); i++) {
                    if (movie.getCrews().get(i).getId().equals(crew.getId())) {
                        movie.getCrews().set(i, crew);
                    }
                }
            }
            for (Series series : Series.retrieveSeries()) {
                for (int i = 0; i <  series.getCrews().size(); i++) {
                    if (series.getCrews().get(i).getId().equals(crew.getId())) {
                        series.getCrews().set(i, crew);
                    }
                }
            }
        }
        else {
            if (crewType.getText().equals("Director")) {
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

    public void saveSeason() throws IOException {
        String title = seasonTitle.getText();
        String description = seasonDescription.getText();
        LocalDate releaseYear = seasonReleaseYear.getValue();
        seasonTitleError.setText("");
        seasonDescriptionError.setText("");
        seasonReleaseYearError.setText("");
        seriesMenuError.setText("");

        if (title.isEmpty()) {
            seasonTitleError.setText("Title is required");
            return;
        }
        if (description.isEmpty()) {
            seasonDescriptionError.setText("Description is required");
            return;
        }
        if (releaseYear == null || releaseYear.isAfter(LocalDate.now())) {
            seasonReleaseYearError.setText("Release date is required");
            return;
        }
        if (seriesMenu.getText().equals("Series")) {
            seriesMenuError.setText("Series is required");
            return;
        }
        Series series = Series.findById(seriesMenu.getId());
        if (currentSeason == null) {
            Season season = new Season(title, description, releaseYear, new ArrayList<>(), series.getId());
            series.getSeasons().add(season);
        } else {
            currentSeason.setTitle(title);
            currentSeason.setDescription(description);
            currentSeason.setReleaseDate(releaseYear);
            assert series != null;
            series.updateSeason(currentSeason);
        }
        addSeasons(series.getId());
    }

    public void displaySeasons() {
        infoContainer.setVisible(false);
        infoContainer.setManaged(false);
        contentsAdmin.setVisible(false);
        contentsAdmin.setManaged(false);
        crewAdmin.setVisible(false);
        crewAdmin.setManaged(false);
        seasonAdmin.setVisible(true);
        seasonAdmin.setManaged(true);
        episodeAdmin.setManaged(false);
        episodeAdmin.setVisible(false);
        subscriptionsAdmin.setVisible(false);
        subscriptionsAdmin.setManaged(false);
        seriesMenu.getItems().clear();
        for (Series series : Series.retrieveSeries()) {
            MenuItem item = new MenuItem();
            item.setText(series.getTitle());
            item.setOnAction(_ -> {
                        try {
                            addSeasons(series.getId());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        seriesMenu.setText(series.getTitle());
                        seriesMenu.setId(series.getId());
                    }
            );
            seriesMenu.getItems().add(item);
        }
    }

    private void addSeasons(String seriesId) throws IOException {
        seasonContainer.getChildren().clear();
        Series series = Series.findById(seriesId);
        for (Season season : series.getSeasons()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/admin-card.fxml"));
            HBox seasonCard = loader.load();
            AdminCardController adminCardController = loader.getController();
            adminCardController.setData(season.getTitle(), season.getReleaseDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")), season.getDescription(), season.getId(), () -> series.getSeasons().remove(season), () -> editSeason(season.getId()), () -> {
                try {
                    addSeasons(seriesId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            seasonContainer.getChildren().add(seasonCard);
        }
    }

    private void editSeason(String seasonId) {
        currentSeason = Season.findById(seasonId);
        seasonTitle.setText(currentSeason.getTitle());
        seasonDescription.setText(currentSeason.getDescription());
        seasonReleaseYear.setValue(currentSeason.getReleaseDate());
    }

    public void saveEpisode() throws IOException {
        String title = episodeTitle.getText();
        String description = episodeDescription.getText();
        LocalDate releaseYear = episodeReleaseDate.getValue();
        String poster = episodePoster.getText();
        int duration;
        try {
            duration = Integer.parseInt(episodeDuration.getText());
        } catch (NumberFormatException exception) {
            episodeDuration.setText("Invalid format");
            return;
        }
        episodeTitleError.setText("");
        episodeDescriptionError.setText("");
        episodeReleaseDateError.setText("");
        episodeDurationError.setText("");
        episodePictureError.setText("");

        if (title.isEmpty()) {
            episodeTitleError.setText("Title is required");
            return;
        }
        if (description.isEmpty()) {
            episodeDescriptionError.setText("Description is required");
            return;
        }
        if (releaseYear == null || releaseYear.isAfter(LocalDate.now())) {
            episodeReleaseDateError.setText("Release date is required");
            return;
        }
        if (poster.isEmpty()) {
            episodePictureError.setText("Picture is required");
            return;
        }
        if (episodeSeriesMenu.getText().equals("Series")) {
            episodeSeriesMenuError.setText("Series is required");
            return;
        }
        if(episodeSeasonMenu.getText().equals("Season")){
            episodeSeasonMenuError.setText("Season is required");
        }
        Season season = Season.findById(episodeSeasonMenu.getId());
        Series series = Series.findById(season.getSeriesId());
        if (currentEpisode == null) {
            Episode episode = new Episode(title, duration, releaseYear, poster, description, episodeSeasonMenu.getId());
            season.getEpisodes().add(episode);
            series.updateSeason(season);
        } else {
            currentEpisode.setTitle(title);
            currentEpisode.setDescription(description);
            currentEpisode.setReleaseDate(releaseYear);
            currentEpisode.setDuration(duration);
            currentEpisode.setPoster(poster);
            season.updateEpisode(currentEpisode);
        }
        initializeEpisodesContainer(season.getId());
    }

    public void displayEpisode() {
        infoContainer.setVisible(false);
        infoContainer.setManaged(false);
        contentsAdmin.setVisible(false);
        contentsAdmin.setManaged(false);
        crewAdmin.setVisible(false);
        crewAdmin.setManaged(false);
        seasonAdmin.setVisible(false);
        seasonAdmin.setManaged(false);
        episodeAdmin.setManaged(true);
        episodeAdmin.setVisible(true);
        subscriptionsAdmin.setVisible(false);
        subscriptionsAdmin.setManaged(false);
        episodeSeriesMenu.getItems().clear();
        episodeSeasonMenu.getItems().clear();
        episodesContainer.getChildren().clear();
        for (Series series : Series.retrieveSeries()) {
            MenuItem item = new MenuItem();
            item.setText(series.getTitle());
            item.setOnAction(_ -> {
                        initializeEpisodeSeasons(series.getId());
                        episodeSeriesMenu.setText(series.getTitle());
                        episodeSeriesMenu.setId(series.getId());
                    }
            );
            episodeSeriesMenu.getItems().add(item);
        }
        episodePosterButton.setOnAction(_ -> handleFileChoose(episodePoster, "/WatChill/Content/media/"));

    }

    private void initializeEpisodeSeasons(String seriesId) {
        episodeSeasonMenu.getItems().clear();
        Series series = Series.findById(seriesId);
        for (Season season : series.getSeasons()) {
            MenuItem item = new MenuItem();
            item.setText(season.getTitle());
            item.setOnAction(_ -> {
                try {
                    initializeEpisodesContainer(season.getId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                episodeSeasonMenu.setText(season.getTitle());
                episodeSeasonMenu.setId(season.getId());
            });
            episodeSeasonMenu.getItems().add(item);
        }
    }

    private void initializeEpisodesContainer(String seasonId) throws IOException {
        episodesContainer.getChildren().clear();
        Season season = Season.findById(seasonId);
        for (Episode episode : season.getEpisodes()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/admin-card.fxml"));
            HBox episodeCard = loader.load();
            AdminCardController adminCardController = loader.getController();
            adminCardController.setData(episode.getPoster(), episode.getTitle(), episode.getReleaseDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")), episode.getDescription(), episode.getId(), ()->{
                season.getEpisodes().remove(episode);
                Series series = Series.findById(season.getSeriesId());
                series.updateSeason(season);
                try {
                    initializeEpisodesContainer(season.getId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            },()->editEpisode(episode.getId()), () ->redirectToEpisodePage(episode.getId()),()-> {
                try {
                    initializeEpisodesContainer(seasonId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            episodesContainer.getChildren().add(episodeCard);
        }
    }

    private void editEpisode(String episodeId) {
        currentEpisode = Episode.findById(episodeId);
        episodeTitle.setText(currentEpisode.getTitle());
        episodeDescription.setText(currentEpisode.getDescription());
        episodeReleaseDate.setValue(currentEpisode.getReleaseDate());
        episodeDuration.setText(((Integer)(currentEpisode.getDuration())).toString());
        episodePoster.setText(currentEpisode.getPoster());
    }
    private void redirectToEpisodePage(String episodeId) {
        try {
            String css = getClass().getResource("/WatChill/style/Episode.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Series/Episode.fxml"));
            root = loader.load();
            EpisodeController episodeController = loader.getController();
            episodeController.build(episodeId);
            stage = (Stage) episodesContainer.getScene().getWindow();
            scene = episodesContainer.getScene();
            scene.setRoot(root);
            scene.getStylesheets().clear();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void handleFileChoose(TextField fileInput, String directoryPath) {
        // Create a FileChooser instance
        FileChooser fileChooser = new FileChooser();

        // Set file chooser properties
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        // Get the current window (Stage)
        Stage stage = (Stage) contentsAdmin.getScene().getWindow();

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
                        () -> deleteCrew(crew.getId()),
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

    private void deleteCrew(String crewId) {
        Crew crew = Crew.findById(crewId);
        for (String contentId : crew.getContentCreated()) {
            Content content = Movie.findById(contentId);
            if (content == null) {
                content = Series.findById(contentId);
            }
            content.getCrews().removeIf(c -> c.getId().equals(crewId));
        }
        crew.delete();
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

    private void setEditedContent(String contentId) {
        Content content = Movie.findById(contentId);
        if (content == null) {
            content = Series.findById(contentId);
        }
        setCurrentContent(content);
        contentTitle.setText(getCurrentContent().getTitle());
        contentDescription.setText(getCurrentContent().getDescription());
        contentReleaseDate.setValue(getCurrentContent().getReleaseDate());
        if (content instanceof Movie) {
            contentDuration.setText(((Integer) ((Movie)getCurrentContent()).getDuration()).toString());
        }
        contentCountry.setText(getCurrentContent().getCountry());
        contentDescription.setText(getCurrentContent().getDescription());
        contentBudget.setText(((Double) getCurrentContent().getBudget()).toString());
        contentRevenue.setText(((Double) getCurrentContent().getRevenue()).toString());
        contentPoster.setText(getCurrentContent().getPoster());

        currentContentCrews = new ArrayList<>(getCurrentContent().getCrews());
        currentContentLanguages = new ArrayList<>(getCurrentContent().getLanguages());
        currentContentGenres = new ArrayList<>(getCurrentContent().getGenres());
        contentGenresContainer.getChildren().clear();
        contentLanguagesContainer.getChildren().clear();
        for (String genre : currentContentGenres) {
            Label label = new Label(genre);
            label.getStyleClass().add("flowpane-label");
            contentGenresContainer.getChildren().add(label);
        }
        for (String language : currentContentLanguages) {
            Label label = new Label(language);
            label.getStyleClass().add("flowpane-label");
            contentLanguagesContainer.getChildren().add(label);
        }
        contentButton.setText("Save");
        contentCrewButton.setText("Edit Crew");
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
                        () -> deleteContent(movie.getId()),
                        () -> setEditedContent(movie.getId()),
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

    private void initializeSeries() {
        seriesContainer.getChildren().clear();
        for (Series series : Series.retrieveSeries()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/admin-card.fxml"));
                HBox card = loader.load();
                AdminCardController adminCardController = loader.getController();
                adminCardController.setData(
                        series.getPoster(),
                        series.getTitle(),
                        series.getDescription(),
                        series.getReleaseDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")),
                        series.getId(),
                        () -> deleteContent(series.getId()),
                        () -> setEditedContent(series.getId()),
                        () -> redirectToSeriesPage(series.getId()),
                        () -> initializeSeries()
                );
                seriesContainer.getChildren().add(card);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeContentsInputs() {
        contentButton.setText("Create");
        contentCrewButton.setText("Add Crew");
        contentTitle.setText("");
        contentDescription.setText("");
        contentReleaseDate.setValue(null);
        contentDuration.setText("");
        contentCountry.setText("");
        contentBudget.setText("");
        contentRevenue.setText("");
        contentLanguage.setText("");
        contentGenre.setText("");
        contentPoster.setText("");
        contentLanguagesContainer.getChildren().clear();
        contentGenresContainer.getChildren().clear();
    }

    private void initializeContentErrors() {
        contentTitleError.setText("");
        contentDescriptionError.setText("");
        contentReleaseDateError.setText("");
        contentDurationError.setText("");
        contentCountryError.setText("");
        contentBudgetError.setText("");
        contentRevenueError.setText("");
        contentLanguageError.setText("");
        contentGenreError.setText("");
        contentPosterError.setText("");
        contentCrewsError.setText("");
    }

    private void deleteContent(String contentId) {
        Content content = Movie.findById(contentId);
        if (content == null) {
            content = Series.findById(contentId);
        }
        for (Crew crew : Crew.retrieveCrews()) {
            crew.getContentCreated().remove(contentId);
        }
        for (User user : User.getUsers()) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                if (customer.findMovieWatchLaterIndex(content.getId()) != -1) {
                    customer.removeFromWatchLater(content.getId());
                }
            }
        }
        UserWatchRecord.retrieveRecords().removeIf(record -> record.getWatchedContent().getId().equals(contentId));
        if (content instanceof Movie) {
            ((Movie) content).delete();
        }
        else {
            ((Series) content).delete();
        }
    }

    public void redirectToMoviePage(String movieId) {
        try {
            String css = getClass().getResource("/WatChill/style/Movie.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/Movie/movie.fxml"));

            MovieController movieController = loader.getController();
            movieController.build(movieId);

            root = loader.load();
            scene = contentAdmin.getScene();
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
            scene = contentAdmin.getScene();
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
            scene = crewAdmin.getScene();
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

    public void addContentLanguage() {
        if (contentLanguage.getText().isEmpty() || currentContentLanguages.contains(contentLanguage.getText())) {
            return;
        }
        Label label = new Label(contentLanguage.getText());
        label.getStyleClass().add("flowpane-label");
        contentLanguagesContainer.getChildren().add(label);
        currentContentLanguages.add(contentLanguage.getText());
        contentLanguage.setText("");
    }

    public void addContentGenre() {
        if (contentGenre.getText().isEmpty() || currentContentGenres.contains(contentGenre.getText())) {
            return;
        }
        Label label = new Label(contentGenre.getText());
        label.getStyleClass().add("flowpane-label");
        contentGenresContainer.getChildren().add(label);
        currentContentGenres.add(contentGenre.getText());
        contentGenre.setText("");
    }

    public void clearContentLanguages() {
        currentContentLanguages.clear();
        contentLanguagesContainer.getChildren().clear();
    }

    public void clearContentGenres() {
        currentContentGenres.clear();
        contentGenresContainer.getChildren().clear();
    }
}
