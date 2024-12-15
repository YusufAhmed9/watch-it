package WatChill.UserManagement;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class SignupController {
    @FXML
    TextField firstNameInput;
    @FXML
    TextField lastNameInput;
    @FXML
    TextField usernameInput;
    @FXML
    TextField emailInput;
    @FXML
    PasswordField passwordInput;
    @FXML
    Text firstNameError;
    @FXML
    Text lastNameError;
    @FXML
    Text usernameError;
    @FXML
    Text emailError;
    @FXML
    Text passwordError;

    Parent root;
    Scene scene;
    Stage stage;

    public void signup(ActionEvent actionEvent) {
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String username = usernameInput.getText();
        String email = emailInput.getText();
        String password = passwordInput.getText();
        firstNameError.setText("");
        lastNameError.setText("");
        usernameError.setText("");
        emailError.setText("");
        passwordError.setText("");
        if (firstName.isEmpty()) {
            firstNameError.setText("First name is required.");
            return;
        }
        if (lastName.isEmpty()) {
            lastNameError.setText("Last name is required.");
            return;
        }
        if (username.isEmpty()) {
            usernameError.setText("Username is required");
            return;
        }
        if (!User.isUsernameUnique(username)) {
            usernameError.setText("A user with that username already exists.");
            return;
        }
        if (!User.isEmailValid(email)) {
            emailError.setText("Invalid Email.");
            return;
        }
        if (!User.isEmailUnique(email)) {
            emailError.setText("A user with that email already exists.");
            return;
        }
        if (password.isEmpty()) {
            passwordError.setText("Password is required.");
            return;
        }
        User.createUser(username, email, password, firstName, lastName);
        redirectToHome(actionEvent);
    }

    public void redirectToLogin(ActionEvent actionEvent) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/User/login.fxml"));
            root = loader.load();
            scene = ((Node) actionEvent.getSource()).getScene();
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
    };
}