package WatChill.Search;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SearchResultController {

    @FXML
    private ImageView posterImage;

    @FXML
    private Label titleText;

    @FXML
    private Label releaseDateText;

    @FXML
    private Label descriptionText;


    public void setData(String imagePath, String title, String releaseDate, String description) {
        posterImage.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        titleText.setText(title);
        releaseDateText.setText(releaseDate);
        descriptionText.setText(description);
    }
}
