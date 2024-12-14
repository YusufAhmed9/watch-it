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

public class LoginController {

    @FXML
    TextField emailInput;
    @FXML
    PasswordField passwordInput;
    @FXML
    Text emailError;
    @FXML
    Text passwordError;
    @FXML
    Text loginError;

    Parent root;
    Scene scene;
    Stage stage;

    public void login(ActionEvent actionEvent) {
        String email = emailInput.getText();
        String password = passwordInput.getText();
        emailError.setText("");
        passwordError.setText("");
        loginError.setText("");
        if (!User.isEmailValid(email)) {
            emailError.setText("Invalid Email.");
            return;
        }
        if (password.isEmpty()) {
            passwordError.setText("Password is required.");
            return;
        }
        if (!User.loginWithEmail(email, password)) {
            loginError.setText("Invalid Credentials");
            return;
        }
        redirectToHome(actionEvent);
    }

    public void redirectToSignup(ActionEvent actionEvent) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/User/signup.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    };

    public void redirectToHome(Event event) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/Home/home.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    };
}
