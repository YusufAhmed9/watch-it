package WatChill.Crew;

import WatChill.Content.Content;
import WatChill.Content.ContentCardController;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Series;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CrewController {
    private Crew crew;
    @FXML
    private ImageView crewPicture;
    @FXML
    private Label name;
    @FXML
    private Label birthDate;
    @FXML
    private Label gender;
    @FXML
    private Label nationality;
    @FXML
    private Label twitterLink;
    @FXML
    private Label instagramLink;
    @FXML
    private ScrollPane contentMadeScrollPane;
    @FXML
    BorderPane crewBorderPane;
    @FXML
    ImageView contentMadeRight;
    @FXML
    ImageView contentMadeLeft;
    @FXML
    HBox contentMadeContainer;

    public void initializePage(String crewId) {
        crew = Crew.findById(crewId);
        Image crewImage = new Image(getClass().getResource(crew.getPicture()).toExternalForm());
        crewPicture.setImage(crewImage);
        name.setText(crew.getFirstName() + ' ' + crew.getLastName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        birthDate.setText(crew.getDateOfBirth().format(formatter));
        gender.setText(crew.getGender());
        nationality.setText(crew.getNationality());
        twitterLink.setText(crew.getTwitterLink());
        instagramLink.setText(crew.getInstagramLink());
        contentMadeLeft.setOnMouseClicked(_ -> scrollLeft(contentMadeScrollPane));
        contentMadeRight.setOnMouseClicked(_ -> scrollRight(contentMadeScrollPane));
        initializeHeader();
        initializeContentMade();
    }

    public void scrollRight(ScrollPane scrollPane) {
        double newHValue = Math.min(scrollPane.getHvalue() + 0.4, 1); // Scroll right
        scrollPane.setHvalue(newHValue);
    }

    public void scrollLeft(ScrollPane scrollPane) {
        double newHValue = Math.min(scrollPane.getHvalue() - 0.4, 1); // Scroll right
        scrollPane.setHvalue(newHValue);
    }

    private void initializeContentMade() {
        contentMadeContainer.getChildren().clear();
        for (String contentId : crew.getContentCreated()) {
            Content contentCreated = Movie.findById(contentId);
            if (contentCreated == null) {
                contentCreated = Series.findById(contentId);
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Content/content-card.fxml"));
                VBox contentCard = loader.load();
                ContentCardController contentCardController = loader.getController();
                contentCardController.setData(contentCreated, () -> initializeContentMade());
                contentMadeContainer.getChildren().add(contentCard);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void initializeHeader() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Home/header.fxml"));
            Pane header = loader.load();
            crewBorderPane.setTop(header);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void build(String crewId) {
        initializePage(crewId);
    }
}
