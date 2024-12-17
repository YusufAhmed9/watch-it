package WatChill.UserManagement;

import WatChill.Subscription.BasicPlan;
import WatChill.Subscription.StandardPlan;
import WatChill.Subscription.Subscription;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class AdminProfileController {
    @FXML
    VBox infoContainer;
    @FXML
    VBox watchLaterContainer;
    @FXML
    FlowPane watchlaterFlowPane;
    @FXML
    VBox subscriptionContainer;
    @FXML
    Label planName;
    @FXML
    HBox planContainer;
    @FXML
    VBox remainingContainer;
    @FXML
    VBox historyContainer;
    @FXML
    VBox historyBox;
    @FXML
    Label username;
    @FXML
    Label email;
    @FXML
    TextField firstNameInput;
    @FXML
    Text firstNameError;
    @FXML
    TextField lastNameInput;
    @FXML
    Text lastNameError;
    @FXML
    PasswordField oldPasswordInput;
    @FXML
    Text oldPasswordError;
    @FXML
    TextField newPasswordInput;
    @FXML
    Text newPasswordError;

    Stage stage;
    Parent root;
    Scene scene;


    public void displayMyInfo(ActionEvent actionEvent) {
        watchLaterContainer.setVisible(false);
        watchLaterContainer.setManaged(false);
        infoContainer.setVisible(true);
        infoContainer.setManaged(true);
        historyContainer.setVisible(false);
        historyContainer.setManaged(false);
        subscriptionContainer.setVisible(false);
        subscriptionContainer.setManaged(false);
        User user = User.getCurrentUser();
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        firstNameInput.setText(user.getFirstName());
        lastNameInput.setText(user.getLastName());
    }

    public void redirectToHome(Event event) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Home/home.fxml"));
            root = loader.load();
            scene = ((Node) event.getSource()).getScene();
            stage = (Stage) scene.getWindow();
            scene.setRoot(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editInfo(ActionEvent actionEvent) {
        firstNameError.setText("");
        lastNameError.setText("");
        oldPasswordError.setText("");
        newPasswordError.setText("");
        User user = User.getCurrentUser();
        if (firstNameInput.getText().isEmpty()) {
            firstNameError.setText("First name can't be empty.");
            return;
        }
        if (lastNameInput.getText().isEmpty()) {
            lastNameError.setText("Last name can't be empty.");
            return;
        }
        if (!newPasswordInput.isDisable() && newPasswordInput.getText().length() < 8) {
            newPasswordError.setText("Password must have at least 8 characters.");
            return;
        }
        user.setFirstName(firstNameInput.getText());
        user.setLastName(lastNameInput.getText());
        if (!newPasswordInput.getText().isEmpty()) {
            String newPassword = newPasswordInput.getText();
            byte[] salt = User.generateSalt();
            String hashedPassword = User.hashPassword(newPassword, salt);
            user.setPassword(Base64.getEncoder().encodeToString(salt) + ":" + hashedPassword);
        }
        oldPasswordInput.setText("");
        newPasswordInput.setText("");
        newPasswordInput.setDisable(true);
    }

    public void checkPassword(ActionEvent actionEvent) {
        User user = User.getCurrentUser();
        oldPasswordError.setText("");
        if (!User.loginWithUsername(user.getUsername(), oldPasswordInput.getText())) {
            oldPasswordError.setText("Wrong password.");
            newPasswordInput.setText("");
            newPasswordInput.setDisable(true);
            return;
        }
        newPasswordInput.setDisable(false);
        newPasswordInput.setText(oldPasswordInput.getText());
    }
}
