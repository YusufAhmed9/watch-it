package WatChill.UserManagement;

import WatChill.Content.AdminCardController;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Movie.MovieController;
import WatChill.Content.Series.SeriesController;
import WatChill.Crew.Cast.Cast;
import WatChill.Crew.Crew;
import WatChill.Crew.Director.Director;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.regex.Pattern;

public class AdminProfileController {
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
    Text titleError;
    @FXML
    TextField titleInput;
    @FXML
    Button movieButton;
    @FXML
    VBox moviesContainer;

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

    private Crew currentCrew = null;
    private Movie currentMovie = null;

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
        infoContainer.setVisible(false);
        infoContainer.setManaged(false);
        moviesAdmin.setVisible(true);
        moviesAdmin.setManaged(true);
        seriesAdmin.setVisible(false);
        seriesAdmin.setManaged(false);
        crewAdmin.setVisible(false);
        crewAdmin.setManaged(false);
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

    }

    public void saveCrew() {
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
            crewLastName.setText("Last name is required.");
            return;
        }
        if (date.compareTo(LocalDate.now()) > -1) {
            dateOfBirthError.setText("Invalid date of birth.");
            return;
        }
        if (gender.equals("Gender")) {
            genderError.setText("Gender is required.");
            return;
        }
        if (nationalityValue.isEmpty()) {
            nationalityError.setText("Nationality is required.");
            return;
        }
        if (isInputValid(instagram, "^https?://www\\.instagram\\.com/.+$")) {
            instagramLinkError.setText("Invalid instagram link.");
            return;
        }
        if (isInputValid(twitter, "^https?://twitter\\.com/.+$")) {
            twitterLink.setText("Invalid twitter link.");
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
        displayCrew();
    }

    public void handleFileChoose() {
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
            System.out.println("File Selected: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("File selection canceled.");
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
        firstNameInput.setText(crew.getFirstName());
        lastNameInput.setText(crew.getLastName());
        dateOfBirth.setValue(crew.getDateOfBirth());
        genderMenu.setText(crew.getGender());
        instagramLink.setText(crew.getInstagramLink());
        twitterLink.setText(crew.getTwitterLink());
        nationality.setText(crew.getNationality());
        crewType.setText(crew instanceof Director ? "Director" : "Cast");
        pictureInput.setText(crew.getPicture());
        crewButton.setText("Save");
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
            SeriesController seriesController = loader.getController();
            seriesController.build(crewId);
            scene = moviesAdmin.getScene();
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
}
