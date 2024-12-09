package WatChill.User;

import WatChill.Cast.Cast;
import WatChill.Movie.*;
import WatChill.Director.*;
import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import WatChill.Subscription.Subscription;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/*  Functionalities:
1- Add /update /delete in all of the entities.
2- The user can display all the movies he already watched.
3- The movie rating should be updated by the new ratings given to a
movie based on the users watch record.
4- Displaying the top-rated movies to the user.
5- The admin can see the most subscribed plan among (Basic, Standard,
Premium).
6- The admin can see which month had the most revenue to the
application by adding all the plan prices subscribed in that month.
7- The user can see the casts and directors’ information and their
movies.
8- The user can search for a movie/director/actor by his name.
9- The user can also search and display all movies by the genre of
movie.
10- Display the top watched movies for the user.
11- Display the recent movies for the users.
12- Display movies that matches specific filters (The filters may be
based on rating, movie duration and language)
13- Recommendation feature (That recommends movies to user
based on the genres and cast that the user usually watch movies for)
*/


import java.util.*;
public class User {
    String name;
    String email;
    String password;
    String fname;
    String lname;
    String subscription;
    static ArrayList<User> userInfoList = new ArrayList<>();

    @JsonCreator
    public User(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("fname") String fname,
        @JsonProperty("lname") String lname,
        @JsonProperty("subscription") String subscription
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.subscription = subscription;
    }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }

    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }

    public void setFname(String fname) { this.fname = fname; }
    public String getFname() { return fname; }

    public void setLname(String lname) { this.lname = lname; }
    public String getLname() { return lname; }

    public void setSubscription(String subscription) { this.subscription = subscription; }
    public String getSubscription() { return subscription; }


    public static ArrayList<User> getUserInfo()
    {
        if (userInfoList == null)
        {
            userInfoList = JsonReader.readJsonFile("./src/main/data/UserInfo.json", User.class);
        }

        return userInfoList;
    }

    public boolean login()
    {
        for (User user : getUserInfo())
        {
            if (user.getEmail().equals(email) && user.getPassword().equals(password))
            {
                return true;
            }
        }
        return false;
    }

    public void signUp()
    {
        String[] data = {};

        do
        {
            System.out.println("Enter your email");
            data[1] = new Scanner(System.in).nextLine();
            if (data[1].trim().isEmpty())
                continue;
            
        } while (!data[1].matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"));

        do 
        {
            System.out.println("Enter your password");
            data[2] = new Scanner(System.in).nextLine();
            if (data[2].trim().isEmpty())
                continue;
        } while (data[2].length() < 8);

        do
        {
            System.out.println("Enter your first name");
            data[3] = new Scanner(System.in).nextLine();
            if (data[3].trim().isEmpty())
                continue;
        } while (data[3].length() < 2);

        do
        {
            System.out.println("Enter your last name");
            data[4] = new Scanner(System.in).nextLine();
            if (data[4].trim().isEmpty())
                continue;
        } while (data[4].length() < 2);

        do
        {
            System.out.println("Enter your name: ");
            data[0] = new Scanner(System.in).nextLine();
            if (data[0].trim().isEmpty())
                continue;
        } while (data[0].length() < 2);
        
        User tmp = new User(data[4], data[0], data[1], data[2], data[3], "Basic");
        userInfoList.add(tmp);
        JsonWriter.writeJsonToFile("./src/main/data/UserInfo.json", userInfoList);

        System.out.println("Sign Up Successful");
        return;
    }

    public void delInfo()
    {
        for (User user : getUserInfo())
        {
            if (user.getEmail().equals(email) && user.getPassword().equals(password))
            {
                getUserInfo().remove(user);
                JsonWriter.writeJsonToFile("./src/main/data/UserInfo.json", getUserInfo());
                return;
            }
        }
    }

    public void updateInfo()
    {
        System.out.println("Enter what you want to change: 1-name, 2-email, 3-password, 4-fname, 5-lname, 6-subscription");
        int choice = new Scanner(System.in).nextInt();
        Scanner scanner = new Scanner(System.in);
        String[] data = {};
        switch (choice) {
            case 1:
                System.out.println("Enter your name: ");
                String nameInput;
                do {
                    nameInput = scanner.nextLine().trim();
                    if (nameInput.isEmpty()) {
                        System.out.println("Name cannot be empty. Try again: ");
                    }
                } while (nameInput.length() < 2);
                this.name = nameInput;
                break;
        
            case 2:
                System.out.println("Enter your email: ");
                String emailInput;
                do {
                    emailInput = scanner.nextLine().trim();
                    if (emailInput.isEmpty() || !emailInput.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                        System.out.println("Invalid email format. Try again: ");
                    }
                } while (!emailInput.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"));
                this.email = emailInput;
                break;
        
            case 3:
                System.out.println("Enter your password (minimum 8 characters): ");
                String passwordInput;
                do {
                    passwordInput = scanner.nextLine().trim();
                    if (passwordInput.length() < 8) {
                        System.out.println("Password too short. Try again: ");
                    }
                } while (passwordInput.length() < 8);
                this.password = passwordInput;
                break;
        
            case 4:
                System.out.println("Enter your first name: ");
                String fnameInput;
                do {
                    fnameInput = scanner.nextLine().trim();
                    if (fnameInput.isEmpty() || fnameInput.length() < 2) {
                        System.out.println("First name must be at least 2 characters long. Try again: ");
                    }
                } while (fnameInput.length() < 2);
                this.fname = fnameInput;
                break;
        
            case 5:
                System.out.println("Enter your last name: ");
                String lnameInput;
                do {
                    lnameInput = scanner.nextLine().trim();
                    if (lnameInput.isEmpty() || lnameInput.length() < 2) {
                        System.out.println("Last name must be at least 2 characters long. Try again: ");
                    }
                } while (lnameInput.length() < 2);
                this.lname = lnameInput;
                break;
        
            case 6:
                System.out.println("Enter your subscription type: ");
                this.subscription = scanner.nextLine().trim();
                break;
        
            default:
                System.out.println("Invalid choice. Please select a valid option.");
        }

        User InputUser = new User(data[0], data[1], data[2],data[3],data[4],"Basic");
        userInfoList.add(InputUser);
        JsonWriter.writeJsonToFile("./src/main/data/UserInfo.json", userInfoList);
    }
}


