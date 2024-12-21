package WatChill.Content;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class AdminCardController {

    @FXML
    private ImageView posterImage;

    @FXML
    private Label titleText;

    @FXML
    private Label subTitleText;

    @FXML
    private Label descriptionText;

    @FXML
    ImageView editButton;

    @FXML
    ImageView deleteButton;

    Parent root;
    Stage stage;
    Scene scene;

    public void setData(String poster, String title, String subTitle, String content, String id, Runnable delete, Runnable edit, Runnable redirect, Runnable rerender) {
        deleteButton.setOnMouseClicked(_ -> {
            delete.run();
            rerender.run();
        });
        editButton.setOnMouseClicked(_ -> edit.run());
        titleText.setText(title);
        subTitleText.setText(subTitle);
        subTitleText.setOnMouseClicked(_ -> redirect.run());
        posterImage.setImage(new Image(getClass().getResource(poster).toExternalForm()));
        posterImage.setOnMouseClicked(_ -> redirect.run());
        descriptionText.setText(content);
    }
    public void setData( String title, String subTitle, String content, String id, Runnable delete, Runnable edit, Runnable rerender) {
        deleteButton.setOnMouseClicked(_ -> {
            delete.run();
            rerender.run();
        });
        editButton.setOnMouseClicked(_ -> edit.run());
        titleText.setText(title);
        subTitleText.setText(subTitle);
        descriptionText.setText(content);
    }
}