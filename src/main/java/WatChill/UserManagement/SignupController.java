package WatChill.UserManagement;

import javafx.event.ActionEvent;
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
        if (!isUsernameValid(username)) {
            usernameError.setText("A user with that username already exists.");
            return;
        }
        if (!isEmailValid(email)) {
            emailError.setText("Invalid Email.");
            return;
        }
        if (password.isEmpty()) {
            passwordError.setText("Password is required.");
            return;
        }
    }

    public void redirectToLogin(ActionEvent actionEvent) {
        try {
            String css = getClass().getResource("/WatChill/style/Main.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WatChill/User/login.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isUsernameValid(String username) {
        return true;
    }
}