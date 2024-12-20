package WatChill.Crew;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class AddCrewCardController {
    public ImageView addButton;
    @FXML
    ImageView crewPicture;
    @FXML
    Text crewName;

    private void initializeData(String crewId, Runnable runnable) {
        Crew crew = Crew.findById(crewId);
        crewPicture.setImage(new Image(getClass().getResource(crew.getPicture()).toExternalForm()));
        crewName.setText(crew.getFirstName() + ' ' + crew.getLastName());
        addButton.setOnMouseClicked(_->{
            setAddButton();
            runnable.run();
        });
    }

    private void setAddButton(){
        if(addButton.getImage().getUrl().equals(getClass().getResource("/WatChill/Content/Series/media/plus.png").toExternalForm())){
            addButton.setImage(new Image(getClass().getResource("/WatChill/Content/Series/media/minus-circle.png").toExternalForm()));
        }
        else{
            addButton.setImage(new Image(getClass().getResource("/WatChill/Content/Series/media/plus.png").toExternalForm()));
        }
    }

    public void build(String crewId, Runnable runnable) {
        initializeData(crewId, runnable);
    }
}
