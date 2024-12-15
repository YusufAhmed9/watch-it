package WatChill.Crew;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
    }

    public void scrollRight(MouseEvent event) {
        double newHValue = Math.min(contentMadeScrollPane.getHvalue() + 0.4, 1); // Scroll right
        contentMadeScrollPane.setHvalue(newHValue);
    }

    public void scrollLeft(MouseEvent event) {
        double newHValue = Math.min(contentMadeScrollPane.getHvalue() - 0.4, 1); // Scroll right
        contentMadeScrollPane.setHvalue(newHValue);
    }

    public void build(String crewId) {
        initializePage(crewId);
    }
}
