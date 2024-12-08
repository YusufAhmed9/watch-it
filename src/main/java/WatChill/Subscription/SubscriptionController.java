package WatChill.Subscription;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.UUID;

public class SubscriptionController {

    @FXML
    VBox basicPlanContainer;
    @FXML
    VBox standardPlanContainer;
    @FXML
    VBox premiumPlanContainer;
    @FXML
    VBox subscriptionContainer;

    Parent root;
    Scene scene;
    Stage stage;

    public void initialize() {
        // TODO : Add actual current user id
        Subscription subscription = Subscription.getUserSubscription("");
        if (subscription != null) {
            int daysRemaining = 30 - (LocalDate.now().getDayOfMonth() - subscription.getStartDate().getDayOfMonth());
            subscriptionContainer.getChildren().clear();
            Text subscriptionText = new Text("Your subscription Ends in: "+ daysRemaining + ". You have " + subscription.getMoviesLeftCount() + " movies left to watch.");
            subscriptionText.getStyleClass().add("subscription-text");
            subscriptionContainer.getChildren().add(subscriptionText);
        }
    }

    public void selectBasic(ActionEvent actionEvent) {
        // TODO : Add actual current user id
        BasicPlan basicPlan = new BasicPlan();
        Subscription subscription = new Subscription(UUID.randomUUID().toString(), "", LocalDate.now(), basicPlan, basicPlan.getMaxMoviesCount());
        subscription.saveSubscription();
    }

    public void selectStandard(ActionEvent actionEvent) {
        // TODO : Add actual current user id
        StandardPlan standardPlan = new StandardPlan();
        Subscription subscription = new Subscription(UUID.randomUUID().toString(), "", LocalDate.now(), standardPlan, standardPlan.getMaxMoviesCount());
        subscription.saveSubscription();
    }

    public void selectPremium(ActionEvent actionEvent) {
        // TODO : Add actual current user id
        PremiumPlan premiumPlan = new PremiumPlan();
        Subscription subscription = new Subscription(UUID.randomUUID().toString(), "", LocalDate.now(), premiumPlan, premiumPlan.getMaxMoviesCount());
        subscription.saveSubscription();
    }
}
