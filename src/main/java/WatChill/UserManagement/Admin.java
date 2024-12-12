package WatChill.UserManagement;

import WatChill.Content.Series.Episode;
import WatChill.Crew.Cast.Cast;
import WatChill.Crew.Director.Director;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;

public class Admin extends User {
    //Constructor for Admin class
    @JsonCreator
    public Admin(
            @JsonProperty("id") String id,
            @JsonProperty("username") String username,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName
    ) {
        super(id, username, email, password, firstName, lastName);
    }


    //Displays Recent Movie Releases
//    public void DisplayRecentMovieReleases()
//    {
//        ArrayList<Movie> shows = Movie.getMostRecent();
//
//        for (Movie movie : shows)
//        {
//            System.out.println("Movie called: " + movie.getTitle() + " (" + movie.getReleaseDate() + ") - " + movie.getGenres());
//        }
//    }

    //Displays Recent Series Releases
//    public void DisplayRecentSeriesReleases()
//    {
//        ArrayList<Series> shows = Series.getMostRecent();
//        for (Series series : shows)
//        {
//            System.out.println("Series called: " + series.getTitle() + " (" + series.getReleaseDate() + ") - " + series.getGenres());
//        }
//    }
//
//    //Display Movies By Genres
//    public void GetMoviesByGenres(String Genre)
//    {
//        Movie.findByGenre(Genre);
//    }
//
//    public void GetSeriesByGenres(String Genre)
//    {
//        Series.findByGenre(Genre);
//    }

    // use getHighestMonthRevenue from Subscribtion
    public void UseGetHighestMonthRevenue() {
        String month = WatChill.Subscription.Subscription.getHighestMonthRevenue();
        System.out.println("Highest revenue month: " + month);
    }

    // use getPlansSubscribtions from Subscribtion
    public static void UseGetPlansSubscribtions() {
        Map<String, Integer> plans = WatChill.Subscription.Subscription.getPlansSubscriptions();

        for (Map.Entry<String, Integer> entry : plans.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void addEpisode() {
        Scanner scanner = new Scanner(System.in);

        String title = null;
        while (title == null) {
            System.out.print("Enter episode's title: ");
            title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Error: provide a valid title");
            }
        }

        int duration = 0;
        while (duration <= 0) {
            System.out.print("Enter episode's duration: ");
            try {
                duration = scanner.nextInt();
                if (duration <= 0) {
                    System.out.println("Error: provide a valid duration");
                }
            } catch (InputMismatchException exception) {
                System.out.println("Error: provide a valid duration");
                scanner.next();
            }
        }

        LocalDate releaseDate = getValidDate(scanner, "Enter date of birth (yyyy-MM-dd): ");
        String poster = getValidInput(scanner, "Enter picture URL: ", "^\\/WatChill\\/Content\\/Series\\/media\\/[A-Za-z\\s]+\\/[A-Za-z0-9]+\\.(jpg|jpeg|png)$\n");

        String description = null;
        while (description == null) {
            System.out.print("Enter episode's description: ");
            description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println("Error: provide a valid description");
            }
        }

        String seasonId = null;
        while (seasonId == null) {
            System.out.print("Enter series id: ");
            title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Error: provide a valid series id");
            }
        }

        Episode episode = new Episode(title, duration, releaseDate, poster, description, seasonId);
    }

    private void addDirector() {
        Scanner scanner = new Scanner(System.in);

        String firstName = getValidInput(scanner, "Enter first name: ", "^[a-zA-Z]+$");
        String lastName = getValidInput(scanner, "Enter last name: ", "^[a-zA-Z]+$");
        LocalDate dateOfBirth = getValidDate(scanner, "Enter date of birth (yyyy-MM-dd): ");
        String gender = getValidInput(scanner, "Enter gender (Male/Female): ", "^(Male|Female)$");
        String nationality = getValidInput(scanner, "Enter nationality: ", "^[a-zA-Z]+$");
        String instagramLink = getValidInput(scanner, "Enter Instagram link: ", "^https?://www\\.instagram\\.com/.+$");
        String twitterLink = getValidInput(scanner, "Enter Twitter link: ", "^https?://twitter\\.com/.+$");
        String picture = getValidInput(scanner, "Enter picture URL: ", "^https?://.+$");

        // Create a new Director object with the validated inputs
        Director director = new Director(firstName, lastName, dateOfBirth, gender, nationality, instagramLink, twitterLink, picture);

        System.out.println("Director created successfully!");
    }

    private void addCast() {
        Scanner scanner = new Scanner(System.in);

        String firstName = getValidInput(scanner, "Enter first name: ", "^[a-zA-Z]+$");
        String lastName = getValidInput(scanner, "Enter last name: ", "^[a-zA-Z]+$");
        LocalDate dateOfBirth = getValidDate(scanner, "Enter date of birth (yyyy-MM-dd): ");
        String gender = getValidInput(scanner, "Enter gender (Male/Female/Other): ", "^(Male|Female|Other)$");
        String nationality = getValidInput(scanner, "Enter nationality: ", "^[a-zA-Z]+$");
        String instagramLink = getValidInput(scanner, "Enter Instagram link: ", "^https?://www\\.instagram\\.com/.+$");
        String twitterLink = getValidInput(scanner, "Enter Twitter link: ", "^https?://twitter\\.com/.+$");
        String picture = getValidInput(scanner, "Enter picture URL: ", "^https?://.+$");

        // Create a new Cast object with the validated inputs
        Cast cast = new Cast(firstName, lastName, dateOfBirth, gender, nationality, instagramLink, twitterLink, picture);

        System.out.println("Cast created successfully!");
    }

    private static String getValidInput(Scanner scanner, String prompt, String regex) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (Pattern.matches(regex, input)) {
                return input;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private static LocalDate getValidDate(Scanner scanner, String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
    }


}
