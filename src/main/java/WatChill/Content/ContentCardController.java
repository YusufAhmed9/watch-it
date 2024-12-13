package WatChill.Content;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ContentCardController {

    @FXML
    private ImageView posterImage;

    @FXML
    private Label titleText;

    @FXML
    private Label releaseDateText;

    public void setData(String imagePath, String title, String releaseDate) {
        posterImage.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        titleText.setText(title);
        releaseDateText.setText(releaseDate);
    }
}