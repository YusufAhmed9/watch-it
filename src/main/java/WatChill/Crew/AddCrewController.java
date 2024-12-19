package WatChill.Crew;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class AddCrewController {
    @FXML
    FlowPane crewFlowPane;
    @FXML
    FlowPane currentCrewFlowPane;

    private void initializePage(ArrayList<Crew> crews) throws IOException {
        crewFlowPane.getChildren().clear();
        currentCrewFlowPane.getChildren().clear();
        for (Crew crew : Crew.retrieveCrews()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Crew/AddCrewCard.fxml"));
            try {
                VBox crewCard = loader.load();
                AddCrewCardController addCrewCardController = loader.getController();
                addCrewCardController.build(crew.getId(), () -> addCrewToList(crews, crew, crewCard));
                crewFlowPane.getChildren().add(crewCard);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void addCrewToList(ArrayList<Crew> crews, Crew crew, VBox crewCard) {
        crews.add(crew);
        if (currentCrewFlowPane.getChildren().contains(crewCard)) {
            crewFlowPane.getChildren().add(crewCard);
        } else {
            currentCrewFlowPane.getChildren().add(crewCard);
        }
    }

    private void removeCrewToList(ArrayList<Crew> crews, Crew crew, VBox crewCard) {
        crews.add(crew);
        crewFlowPane.getChildren().add(crewCard);
    }

    public void build(ArrayList<Crew> crews) throws IOException {
        initializePage(crews);
    }
}
