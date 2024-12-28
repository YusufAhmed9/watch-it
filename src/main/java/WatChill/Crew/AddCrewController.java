package WatChill.Crew;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class AddCrewController {
    @FXML
    FlowPane crewFlowPane;
    @FXML
    FlowPane currentCrewFlowPane;
    @FXML
    StackPane cancelButton;
    @FXML
    StackPane confirmButton;

    private void initializePage(ArrayList<Crew> crews) {
        crewFlowPane.getChildren().clear();
        currentCrewFlowPane.getChildren().clear();
        ArrayList<String> crewIds = new ArrayList<>(crews.stream().map(Crew::getId).toList());
        for (Crew crew : Crew.retrieveCrews()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Crew/AddCrewCard.fxml"));
            try {
                VBox crewCard = loader.load();
                AddCrewCardController addCrewCardController = loader.getController();
                addCrewCardController.build(crew.getId(), () -> addCrewToList(crews, crew, crewCard));
                if (crewIds.contains(crew.getId())) {
                    addCrewCardController.addButton.setImage(new Image(getClass().getResource("/WatChill/Content/Series/media/minus-circle.png").toExternalForm()));
                    currentCrewFlowPane.getChildren().add(crewCard);
                }
                else {
                    crewFlowPane.getChildren().add(crewCard);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void addCrewToList(ArrayList<Crew> crews, Crew crew, VBox crewCard) {
        if (currentCrewFlowPane.getChildren().contains(crewCard)) {
            crewFlowPane.getChildren().add(crewCard);
            crews.remove(crew);
        } else {
            currentCrewFlowPane.getChildren().add(crewCard);
            crews.add(crew);
        }
    }

    private void removeCrewToList(ArrayList<Crew> crews, Crew crew, VBox crewCard) {
        crews.add(crew);
        crewFlowPane.getChildren().add(crewCard);
    }

    public void build(ArrayList<Crew> crews, Runnable confirm, Runnable cancel) throws IOException {
        initializePage(crews);
        confirmButton.setOnMouseClicked(_ -> confirm.run());
        cancelButton.setOnMouseClicked(_ -> cancel.run());
    }
}
