package WatChill.UserManagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProfileController {
    @FXML
    HBox profileContainer;
    @FXML
    VBox infoContainer;
    @FXML
    VBox watchLaterContainer;
    @FXML
    VBox subscriptionContainer;
    @FXML
    Label planName;
    @FXML
    HBox planContainer;
    @FXML
    HBox remainingContainer;
    @FXML
    VBox historyContainer;

    public void displayWatchLater(ActionEvent actionEvent) {
        watchLaterContainer.setVisible(true);
        watchLaterContainer.setManaged(true);
        infoContainer.setVisible(false);
        infoContainer.setManaged(false);
    }

    public void displayMyInfo(ActionEvent actionEvent) {

    }

    public void displayHistory() {

    }

    public void displayMySubscription() {

    }
}
