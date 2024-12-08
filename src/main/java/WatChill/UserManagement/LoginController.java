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

public class LoginController {

    @FXML
    TextField emailInput;
    @FXML
    PasswordField passwordInput;
    @FXML
    Text emailError;
    @FXML
    Text passwordError;

    Parent root;
    Scene scene;
    Stage stage;

    public void login(ActionEvent actionEvent) {
        String email = emailInput.getText();
        String password = passwordInput.getText();
        emailError.setText("");
        passwordError.setText("");
        if (!isEmailValid(email)) {
            emailError.setText("Invalid Email.");
            return;
        }
        if (password.isEmpty()) {
            passwordError.setText("Password is required.");
            return;
        }
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
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
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    };
}
