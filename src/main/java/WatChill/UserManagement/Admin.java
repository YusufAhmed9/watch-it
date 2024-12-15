package WatChill.UserManagement;

import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Episode;
import WatChill.Content.Series.Season;
import WatChill.Content.Series.Series;
import WatChill.Crew.Cast.Cast;
import WatChill.Crew.Crew;
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

    // use getHighestMonthRevenue from Subscription
    public void UseGetHighestMonthRevenue() {
        String month = WatChill.Subscription.Subscription.getHighestMonthRevenue();
        System.out.println("Highest revenue month: " + month);
    }

    // use getPlansSubscriptions from Subscription
    public static void UseGetPlansSubscriptions() {
        Map<String, Integer> plans = WatChill.Subscription.Subscription.getPlansSubscriptions();

        for (Map.Entry<String, Integer> entry : plans.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void addMovie() {
        Scanner scanner = new Scanner(System.in);
        String title = null;
        String poster = null;
        String description = null;
        String country;
        int duration = 0;
        double budget;
        double revenue;

        LocalDate releaseDate;
        ArrayList<String> languages;
        ArrayList<String> genres;
        ArrayList<Crew> crews;

        while (title == null || title.isEmpty()) {
            System.out.print("Enter movie's title: ");
            title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Error: provide a valid title");
            }
        }

        while (duration <= 0) {
            System.out.print("Enter movie's duration: ");
            try {
                duration = scanner.nextInt();
                if (duration <= 0) {
                    System.out.println("Error: provide a valid duration");
                }
            } catch (InputMismatchException exception) {
                System.out.println("Error: provide a valid duration");
                scanner.next();
            }
            scanner.nextLine();
        }

        releaseDate = getValidDate(scanner, "Enter movie's release date (yyyy-MM-dd): ");
        while (poster == null || poster.isEmpty()) {
            System.out.print("Enter movie's poster: ");
            poster = scanner.nextLine().trim();
            if (poster.isEmpty()) {
                System.out.println("Error: provide a valid poster");
                scanner.next();
            }
            scanner.nextLine();
        }
        languages = getValidLanguages();
        genres = getValidGenres();
        country = getValidInput(scanner, "Enter country name: ", "^[A-Z][a-z]*(?: [A-Z][a-z]*)*$");
        budget = getValidDouble(scanner, "Enter movie's budget: ");
        revenue = getValidDouble(scanner, "Enter movie's revenue: ");
        crews = getValidCrews();

        Movie movie = new Movie(UUID.randomUUID().toString(), title, releaseDate, duration, languages, genres, crews, country, budget, revenue, poster, 0, description);
        movie.save();
        System.out.println("Movie added successfully!");
    }

    private void addSeries() {
        Scanner scanner = new Scanner(System.in);
        String title = null;
        String poster;
        String description = null;
        String country;
        int duration = 0;
        double budget;
        double revenue;

        LocalDate releaseDate;
        ArrayList<String> languages;
        ArrayList<String> genres;
        ArrayList<Crew> crews;

        while (title == null || title.isEmpty()) {
            System.out.print("Enter series's title: ");
            title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Error: provide a valid title");
            }
        }

        while (description == null || description.isEmpty()) {
            System.out.print("Enter series's description: ");
            description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println("Error: provide a valid description");
            }
        }

        releaseDate = getValidDate(scanner, "Enter movie's release date (yyyy-MM-dd): ");
        poster = getValidInput(scanner, "Enter picture URL: ", "^\\/WatChill\\/Content\\/Series\\/media\\/[A-Za-z\\s]+\\/[A-Za-z0-9]+\\.(jpg|jpeg|png)$\n");
        languages = getValidLanguages();
        genres = getValidGenres();
        country = getValidInput(scanner, "Enter country name: ", "^[A-Z][a-z]*(?: [A-Z][a-z]*)*$");
        budget = getValidDouble(scanner, "Enter series's budget");
        revenue = getValidDouble(scanner, "Enter series's revenue");
        crews = getValidCrews();

        Series series = new Series(UUID.randomUUID().toString(), title, releaseDate, new ArrayList<>(), description, languages, country, genres, crews, poster, budget, revenue);
        series.save();
        System.out.println("Series added successfully!");
    }


    private void addEpisode() {
        Scanner scanner = new Scanner(System.in);

        String title = null;
        while (title == null || title.isEmpty()) {
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
            scanner.nextLine();
        }

        LocalDate releaseDate = getValidDate(scanner, "Enter episode's release date (yyyy-MM-dd): ");
        String poster = getValidInput(scanner, "Enter picture URL: ", "^\\/WatChill\\/Content\\/Series\\/media\\/[A-Za-z\\s]+\\/[A-Za-z0-9]+\\.(jpg|jpeg|png)$\n");

        String description = null;
        while (description == null || description.isEmpty()) {
            System.out.print("Enter episode's description: ");
            description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println("Error: provide a valid description");
            }
        }

        String seasonId = null;
        while (seasonId == null) {
            System.out.print("Enter season id: ");
            seasonId = scanner.nextLine().trim();
            if (seasonId.isEmpty() || Season.findById(seasonId) == null) {
                System.out.println("Error: provide a valid season id");
            }
        }

        Episode episode = new Episode(title, duration, releaseDate, poster, description, seasonId);
        Season season = Season.findById(seasonId);
        season.addEpisode(episode);
        Series series = Series.findById(season.getSeriesId());
        series.updateSeason(seasonId);
        series.save();
    }

    private void addSeason() {
        //ArrayList<Episode> episodes
        Scanner scanner = new Scanner(System.in);

        String title = null;
        while (title == null || title.isEmpty()) {
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
            scanner.nextLine();
        }

        LocalDate releaseDate = getValidDate(scanner, "Enter season's release date (yyyy-MM-dd): ");

        String description = null;
        while (description == null || description.isEmpty()) {
            System.out.print("Enter season's description: ");
            description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println("Error: provide a valid description");
                scanner.next();
            }
            scanner.nextLine();
        }


        String seriesId = null;
        while (seriesId == null) {
            System.out.print("Enter series id: ");
            seriesId = scanner.nextLine().trim();
            if (seriesId.isEmpty() || Series.findById(seriesId) == null) {
                System.out.println("Error: provide a valid series id");
            }
        }

        Season season = new Season(title, description, releaseDate, new ArrayList<>() , seriesId);
        Series series = Series.findById(seriesId);
        series.addSeason(season);
        series.save();
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
        director.saveCrew();
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
        cast.saveCrew();
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

    public static ArrayList<String> getValidLanguages() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> languages = new ArrayList<>();
        while (true) {
            String language;
            String choice;
            language = getValidInput(scanner, "Enter language: ", "^[a-zA-Z]+$");
            languages.add(language);
            System.out.print("Add another language ? (y / n): ");
            choice = scanner.nextLine().toLowerCase();
            if (choice.equals("n")) {
                break;
            }
        }
        return languages;
    }

    public static ArrayList<String> getValidGenres() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> genres = new ArrayList<>();
        while (true) {
            String genre;
            String choice;
            genre = getValidInput(scanner, "Enter genre: ", "^[a-zA-Z]+$");
            genres.add(genre);
            System.out.print("Add another genre ? (y / n): ");
            choice = scanner.nextLine().toLowerCase();
            if (choice.equals("n")) {
                break;
            }
        }
        return genres;
    }

    private double getValidDouble(Scanner scanner, String prompt) {
        double value = 0;
        while (value <= 0) {
            System.out.print(prompt);
            try {
                value = scanner.nextDouble();
                if (value > 0) {
                    break;
                }
                System.out.println("Error: provide a valid input");
            } catch (InputMismatchException exception) {
                System.out.println("Error: provide a valid input");
            }
            scanner.nextLine();
        }
        return value;
    }

    private ArrayList<Crew> getValidCrews() {
        ArrayList<Crew> crews = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String crewId = null;
        Crew crew = null;
        String choice;
        printCrews(Crew.retrieveCrews());
        while (crewId == null || crewId.isEmpty() || crew == null) {
            System.out.print("Enter crew id: ");
            crewId = scanner.nextLine();
            if (crewId.isEmpty()) {
                System.out.println("Crew ID can't be empty.");
                continue;
            }
            crew = Crew.findById(crewId);
            if (crew == null) {
                System.out.println("Invalid crew id.");
                continue;
            }
            if (crews.contains(crew)) {
                System.out.println("You have already chosen this crew member.");
                continue;
            }
            crews.add(crew);
            System.out.print("Add another crew member ? ( y / n ): ");
            choice = scanner.nextLine().toLowerCase();
            if (choice.equals("n")) {
                break;
            }
        }
        return crews;
    }

    private void printCrews(ArrayList<Crew> crews) {
        System.out.printf("%20s", "Name");
        System.out.printf("%20s", "Date Of Birth |");
        System.out.printf("%20s", "Type|\n");
        for (Crew crew : crews) {
            System.out.printf("%20s", crew.getFirstName() + " " + crew.getLastName() + " |");
            System.out.printf("%20s", crew.getDateOfBirth().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")) + " |");
            System.out.printf("%20s", crew instanceof Director ? "Director" : "Actor" + " |\n");
        }
    }
}