package WatChill.UserManagement;

import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import com.fasterxml.jackson.annotation.*;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Pattern;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS, // Use class name as type information
    property = "@class" // Use "@class" as the property name
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = "WatChill.UserManagement.Admin"),
        @JsonSubTypes.Type(value = Customer.class, name = "WatChill.UserManagement.Customer")
})
abstract public class User {
    private static ArrayList<User> users;
    private static User currentUser;
    protected String id;
    protected String username;
    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;

    public User(String id, String username, String email, String password, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return username; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }

    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getFirstName() { return firstName; }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public static ArrayList<User> getUsers() {
        if (users == null) {
            users = JsonReader.readJsonFile("./src/main/data/users.json", User.class);
        }
        return users;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    private static boolean loginWithEmail(String email, String password) {
        User user = findUserByEmail(email);
        if (user != null && verifyPassword(password, user.getPassword())) {
            setCurrentUser(user);
            return true;
        }
        return false;
    }

    private static boolean loginWithUsername(String username, String password) {
        User user = findUserByUsername(username);
        if (user != null && verifyPassword(password, user.getPassword())) {
            setCurrentUser(user);
            return true;
        }
        return false;
    }

    public static void signUp() {
        Scanner scanner = new Scanner(System.in);
        String firstName;
        String lastName;
        String username;
        String email;
        String password;
        while (true) {
            System.out.print("Enter your first name: ");
            firstName = scanner.nextLine();
            if (!firstName.isEmpty()) {
                break;
            }
            System.out.println("First name can't be empty.");
        }
        while (true) {
            System.out.print("Enter your last name: ");
            lastName = scanner.nextLine();
            if (!lastName.isEmpty()) {
                break;
            }
            System.out.println("Last name can't be empty.");
        }
        while (true) {
            System.out.print("Enter your username: ");
            username = scanner.nextLine();
            if (username.isEmpty()) {
                System.out.println("Username can't be empty.");
                continue;
            }
            if (!isUsernameUnique(username)) {
                System.out.println("Username is already taken.");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            if (!isEmailValid(email)) {
                System.out.println("Email is invalid");
                continue;
            }
            if (!isEmailUnique(email)) {
                System.out.println("Email is already used.");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Enter your password: ");
            password = scanner.nextLine();
            if (password.length() >= 8) {
                break;
            }
            System.out.println("Password can't have less than 8 characters.");
        }
        byte[] salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);
        password = Base64.getEncoder().encodeToString(salt) + ":" + hashedPassword;
        User user;
        if (getUsers().isEmpty()) {
            user = new Admin(UUID.randomUUID().toString(), username, email, password, firstName, lastName);
        }
        else {
            user = new Customer(UUID.randomUUID().toString(), username, email, password, firstName, lastName, new ArrayList<>(), new ArrayList<>());
        }
        user.saveUser();
    }

    public static void login() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.println("[ 1 ]: Login with email");
        System.out.println("[ 2 ]: Login with username");
        while(true) {
            try {
                System.out.print("Enter Choice: ");
                choice = scanner.nextInt();
                if (choice == 1) {
                    loginWithEmailInterface();
                } else if (choice == 2) {
                    loginWithUsernameInterface();
                } else {
                    System.out.println("Invalid Choice.");
                    continue;
                }
                System.out.println("Login Successful");
                break;
            }
            catch (Exception e) {
                System.out.println("Invalid Choice.");
            }
        }
    }

    private static void loginWithEmailInterface() {
        Scanner scanner = new Scanner(System.in);
        String email;
        String password;
        while (true) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine();
            if (!isEmailValid(email)) {
                System.out.println("Invalid Email.");
                continue;
            }
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
            if (!loginWithEmail(email, password)) {
                System.out.println("Invalid Credentials");
                continue;
            }
            break;
        }
    }

    private static void loginWithUsernameInterface() {
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;
        while (true) {
            System.out.print("Enter Username: ");
            username = scanner.nextLine();
            if (username.isEmpty()) {
                System.out.println("Username can't be empty.");
                continue;
            }
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
            if (!loginWithUsername(username, password)) {
                System.out.println("Invalid Credentials.");
                continue;
            }
            break;
        }
    }

    public void saveUser() {
        int index = findIndex();
        if (index == -1) {
            getUsers().add(this);
        }
        else {
            getUsers().set(index, this);
        }
    }

    public void delete()
    {
        getUsers().remove(this);
    }

    public static void storeUsers() {
        JsonWriter.writeJsonToFile("./src/main/data/Users.json", getUsers());
    }

    private int findIndex() {
        for (int i = 0; i < getUsers().size(); i++) {
            if (getUsers().get(i).getId().equals(getId())) {
                return i;
            }
        }
        return -1;
    }

    private static User findUserByEmail(String email) {
        for (User user : getUsers()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    private static User findUserByUsername(String username) {
        for (User user : getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private static boolean isEmailUnique(String email) {
        User user = findUserByEmail(email);
        return user == null;
    }

    private static boolean isUsernameUnique(String username) {
        User user = findUserByUsername(username);
        return user == null;
    }

    private static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] generateSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstanceStrong();
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean verifyPassword(String inputPassword, String storedPassword) {
        // Hash the input password using the same salt
        String saltBase64 = storedPassword.split(":")[0];
        String storedHash = storedPassword.split(":")[1];

        // Decode the salt
        byte[] salt = Base64.getDecoder().decode(saltBase64);
        String inputHash = hashPassword(inputPassword, salt);

        // Compare the hashes
        return inputHash.equals(storedHash);
    }
}