package WatChill.Search;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SearchResultController {

    @FXML
    private ImageView posterImage;

    @FXML
    private Text titleText;

    @FXML
    private Text releaseDateText;

    @FXML
    private Text descriptionText;

    public void setData(String imagePath, String title, String releaseDate, String description) {
        posterImage.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        titleText.setText(title);
        releaseDateText.setText(releaseDate);
        descriptionText.setText(description);
    }

    public void setData(String imagePath, String title, String releaseDate) {
        posterImage.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        titleText.setText(title);
        releaseDateText.setText(releaseDate);
    }
}
