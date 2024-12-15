package WatChill.UserManagement;

import WatChill.Content.Content;
import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Episode;
import WatChill.Content.Series.Series;
import WatChill.Content.WatchableContent;
import WatChill.Content.WatchedContent;
import WatChill.Crew.Crew;
import WatChill.Crew.Director.Director;
import WatChill.Review.Review;
import WatChill.Subscription.*;
import WatChill.UserWatchRecord.UserWatchRecord;
import com.fasterxml.jackson.annotation.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Customer extends User {
    private ArrayList<Content> watchLater;

    // Specify the constructor and parameters for jackson to serialize rhe class
    @JsonCreator
    public Customer(
        @JsonProperty("id") String id,
        @JsonProperty("username") String username,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("firstName") String firstName,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("watchLater") ArrayList<Content> watchLater
    ) {
        super(id, username, email, password, firstName, lastName);
        this.watchLater = watchLater;
    }

    public void setWatchLater(ArrayList<Content> watchLater) {
        this.watchLater = watchLater;
    }

    public ArrayList<Content> getWatchLater() {
        return watchLater;
    }

    public void customerInterface() {
        System.out.printf("Welcome Back, %s\n", getUsername());
        if (Subscription.getUserSubscription(getId()) == null) {
            unsubscribedCustomerInterface();
        }
        else {
            subscribedCustomerInterface();
        }
    }

    private void unsubscribedCustomerInterface() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.println("You have no active subscription");
        System.out.println("[ 1 ]: Subscribe");
        System.out.println("[ 2 ]: Edit Your Info");
        System.out.println("[ 3 ]: Watch History");
        System.out.println("[ 4 ]: Watch Later");
        while (true) {
            System.out.println("Choice: ");
            choice = scanner.nextInt();
            if (choice >= 1 && choice <= 4) {
                break;
            }
            System.out.println("Invalid Choice.");
        }
        if (choice == 1) {
            displayMovies();
        }
        else if (choice == 2) {
            displaySeries();
        }
        else if (choice == 3) {
            displaySubscriptionDetails();
        }
        else {
            displayWatchLater();
        }
    }

    private void subscribedCustomerInterface() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.println("[ 1 ]: Movies");
        System.out.println("[ 2 ]: Series");
        System.out.println("[ 3 ]: Crews");
        System.out.println("[ 4 ]: Recommendations");
        System.out.println("[ 5 ]: Subscription Details");
        System.out.println("[ 6 ]: Edit Your Info");
        System.out.println("[ 7 ]: Watch History");
        System.out.println("[ 8 ]: Watch Later");
        while (true) {
            System.out.println("Choice: ");
            choice = scanner.nextInt();
            if (choice >= 1 && choice <= 8) {
                break;
            }
            System.out.println("Invalid Choice.");
        }
        if (choice == 1) {
            displayMovies();
        }
        else if (choice == 2) {
            displaySeries();
        }
        else if (choice == 3) {
            displayCrews();
        }
        else if (choice == 4) {
            displayRecommendations();
        }
        else if (choice == 5) {
            displaySubscriptionDetails();
        }
        else if (choice == 6) {

        }
        else if (choice == 7) {
            displayWatchHistory();
        }
        else {
            displayWatchLater();
        }
    }

    public void displayMovies() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.println("[ 1 ]: Trending Movies");
        System.out.println("[ 2 ]: Top Rated Movies");
        System.out.println("[ 3 ]: Search Movies By Name");
        System.out.println("[ 4 ]: Search Movies By Genre");
        System.out.println("[ 5 ]: Search Movies By Language");
        while (true) {
            try {
                System.out.print("Choice: ");
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 5) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid Choice.");
            }
        }
        if (choice == 1) {
            displayTrendingMovies();
        }
        else if (choice == 2) {
            displayTopRatedMovies();
        }
        else if (choice == 3) {
            searchMoviesByName();
        }
        else if (choice == 4) {
            searchMoviesByGenre();
        }
        else {
            searchMoviesByLanguage();
        }
    }

    public void displaySeries() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.println("[ 1 ]: Trending Series");
        System.out.println("[ 2 ]: Top Rated Series");
        System.out.println("[ 3 ]: Search Series By Name");
        System.out.println("[ 4 ]: Search Series By Genre");
        System.out.println("[ 5 ]: Search Series By Language");
        while (true) {
            try {
                System.out.print("Choice: ");
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 5) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid Choice.");
            }
        }
        if (choice == 1) {
            displayTrendingSeries();
        }
        else if (choice == 2) {
            displayTopRatedSeries();
        }
        else if (choice == 3) {
            searchSeriesByName();
        }
        else if (choice == 4) {
            searchSeriesByGenre();
        }
        else {
            searchSeriesByLanguage();
        }
    }

    private void displayCrews() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.println("[ 1 ]: Search Crews By Name");
        while (true) {
            try {
                System.out.print("Choice: ");
                choice = scanner.nextInt();
                if (choice != 1) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid Choice.");
            }
        }
        searchCrewsByName();
    }

    private void displaySubscriptionDetails() {
        Subscription subscription = Subscription.getUserSubscription(getId());
        System.out.println("Current Plan: " + subscription.getPlan());
        System.out.println("Subscription End Date: " + subscription.getStartDate());
        System.out.println("Movies Left In Subscription: " + subscription.getMoviesLeftCount());
    }

    private void subscribeInterface() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        Plan plan;
        System.out.println("[ 1 ]: Basic | 5 Movies / Mo | 10 L.E / Mo");
        System.out.println("[ 2 ]: Standard | 10 Movies / Mo | 20 L.E / Mo");
        System.out.println("[ 3 ]: Premium | 30 Movies / Mo | 30 L.E / Mo");
        while (true) {
            System.out.println("Choice: ");
            choice = scanner.nextInt();
            if (choice >= 1 && choice <= 3) {
                break;
            }
            System.out.println("Invalid Choice.");
        }
        if (choice == 1) {
            plan = new BasicPlan();
        }
        else if (choice == 2) {
            plan = new StandardPlan();
        }
        else {
            plan = new PremiumPlan();
        }
        Subscription subscription = new Subscription(UUID.randomUUID().toString(), getId(), LocalDate.now(), plan, plan.getMaxMoviesCount());
        subscription.saveSubscription();
        System.out.println("You should pay: " + plan.getPrice());
        System.out.println("You have: " + plan.getMaxMoviesCount() + " movies left");
        System.out.println("Your subscription expires on: " + subscription.getStartDate().plusDays(30));
    }

    private void displayWatchHistory() {
        ArrayList<UserWatchRecord> userWatchRecords = UserWatchRecord.getUserWatchRecord(getId());
        if (userWatchRecords.isEmpty()) {
            System.out.println("You have no watch records.");
            return;
        }
        System.out.println("Watch History: ");
        printWatchRecords(userWatchRecords);
    }

    public void watchContent(WatchedContent watchedContent, Review review) {
        Subscription subscription = Subscription.getUserSubscription(getId());
        subscription.setMoviesLeftCount(subscription.getMoviesLeftCount() - 1);
        subscription.saveSubscription();
        System.out.println("You have: " + subscription.getMoviesLeftCount() + " movies left");
        UserWatchRecord.addRecord(watchedContent, getId(), review);
    }

    private void displayWatchLater() {
        if (watchLater.isEmpty()) {
            System.out.println("You have no movies in your watch later list.");
            return;
        }
        printContent(watchLater);
    }

    public void addToWatchLater(Content content) {
        int watchLaterIndex = findMovieWatchLaterIndex(content.getId());
        if (watchLaterIndex == -1) {
            getWatchLater().add(content);
        }
    }

    public void removeFromWatchLater(String contentId) {
        int index = findMovieWatchLaterIndex(contentId);
        getWatchLater().remove(index);
    }

    public int findMovieWatchLaterIndex(String contentId) {
        for (int i = 0; i < watchLater.size(); i++) {
            if (watchLater.get(i).getId().equals(contentId)) {
                return i;
            }
        }
        return -1;
    }

    private void displayTrendingMovies() {
        printContent(new ArrayList<>(Movie.getTopTen()));
    }

    private void displayTopRatedMovies() {
        printContent(new ArrayList<>(Movie.getTopRatedMovies()));
    }

    private void searchMoviesByName() {
        Scanner scanner = new Scanner(System.in);
        String title;
        ArrayList<Content> contents;
        while (true) {
            System.out.print("Enter search query: ");
            title = scanner.nextLine();
            if (!title.isEmpty()) {
                contents = new ArrayList<>(Movie.searchByTitle(title));
                if (contents.isEmpty()) {
                    System.out.println("No Results Found.");
                    return;
                }
                break;
            }
            System.out.println("Search Query can't be empty");
        }
        printContent(contents);
    }

    private void searchMoviesByGenre() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        ArrayList<Content> contents;
        Map<Integer, String> genresMap = displayGenres();
        while (true) {
            try {
                System.out.print("Enter Choice: ");
                choice = scanner.nextInt();
                contents = new ArrayList<>(Movie.findByGenre(genresMap.get(choice)));
                if (contents.isEmpty()) {
                    System.out.println("No Results Found.");
                    return;
                }
                break;
            }
            catch (Exception e) {
                System.out.println("Invalid Choice.");
            }
        }
        printContent(contents);
    }

    private void displayTrendingSeries() {
        printContent(new ArrayList<>(Series.getTopWatchedSeries()));
    }

    private void displayTopRatedSeries() {
        printContent(new ArrayList<>(Series.getTopRatedSeries()));
    }

    private void searchSeriesByName() {
        Scanner scanner = new Scanner(System.in);
        String title;
        ArrayList<Content> contents;
        while (true) {
            System.out.print("Enter search query: ");
            title = scanner.nextLine();
            if (!title.isEmpty()) {
                contents = new ArrayList<>(Series.searchByTitle(title));
                if (contents.isEmpty()) {
                    System.out.println("No Results Found.");
                    return;
                }
                break;
            }
            System.out.println("Search Query can't be empty");
        }
        printContent(contents);
    }

    private void searchSeriesByGenre() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        ArrayList<Content> contents;
        Map<Integer, String> genresMap = displayGenres();
        while (true) {
            try {
                System.out.print("Enter Choice: ");
                choice = scanner.nextInt();
                contents = new ArrayList<>(Series.findByGenre(genresMap.get(choice)));
                if (contents.isEmpty()) {
                    System.out.println("No Results Found.");
                    return;
                }
                break;
            }
            catch (Exception e) {
                System.out.println("Invalid Choice.");
            }
        }
        printContent(contents);
    }

    private void printContent(ArrayList<Content> contents) {
        System.out.printf("%20s", "Title |");
        System.out.printf("%20s", "Release Year |");
        System.out.printf("%20s", "Description |\n");
        for (Content content : contents) {
            System.out.printf("%20s", content.getTitle() + " |");
            System.out.printf("%20s", content.getReleaseDate() + " |");
            System.out.printf("%20s", content.getDescription() + " |\n");
        }
    }

    private void printWatchRecords(ArrayList<UserWatchRecord> userWatchRecords) {
        System.out.printf("%20s", "Watch Date |");
        System.out.printf("%20s", "Title |");
        System.out.printf("%20s", "Description |\n");
        for (UserWatchRecord userWatchRecord : userWatchRecords) {
            WatchedContent watchedContent = userWatchRecord.getWatchedContent();
            if (watchedContent instanceof Episode) {
                Episode episode = (Episode) watchedContent;
                System.out.printf("%20s", userWatchRecord.getWatchDate().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")) + " |");
                System.out.printf("%20s", episode.getTitle() + " |");
                System.out.printf("%20s", episode.getDescription() + " |\n");
            }
        }
    }

    private Map<Integer, String> displayGenres() {
        ArrayList<String> genres = new ArrayList<>(Content.allContentGenres);
        Map<Integer, String> genresMap = new HashMap<>();
        for (int i = 0; i < genres.size(); i++) {
            System.out.println("[ " + i + 1 + " ]: " + genres.get(i));
            genresMap.put(i + 1, genres.get(i));
        }
        return genresMap;
    }

    private Map<Integer, String> displayLanguages() {
        ArrayList<String> languages = new ArrayList<>(Content.allContentLanguages);
        Map<Integer, String> languagesMap = new HashMap<>();
        for (int i = 0; i < languages.size(); i++) {
            System.out.println("[ " + i + 1 + " ]: " + languages.get(i));
            languagesMap.put(i + 1, languages.get(i));
        }
        return languagesMap;
    }

    private void displayRecommendations() {
        ArrayList<Content> contents = UserWatchRecord.recommendWatchableContent(User.getCurrentUser().getId());
        printContent(contents);
    }

    private void searchCrewsByName() {
        Scanner scanner = new Scanner(System.in);
        String title;
        ArrayList<Crew> crews;
        while (true) {
            System.out.print("Enter search query: ");
            title = scanner.nextLine();
            if (!title.isEmpty()) {
                crews = new ArrayList<>(Crew.searchByName(title));
                if (crews.isEmpty()) {
                    System.out.println("No Results Found.");
                    return;
                }
                break;
            }
            System.out.println("Search Query can't be empty");
        }
        printCrews(crews);
    }

    private void printCrews(ArrayList<Crew> crews) {
        System.out.printf("%20s", "Name |");
        System.out.printf("%20s", "Date Of Birth |");
        System.out.printf("%20s", "Type |\n");
        for (Crew crew : crews) {
            System.out.printf("%20s", crew.getFirstName() + " " + crew.getLastName() + " |");
            System.out.printf("%20s", crew.getDateOfBirth().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")) + " |");
            System.out.printf("%20s", crew instanceof Director ? "Director" : "Actor" + " |\n");
        }
    }

    private void searchSeriesByLanguage() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        ArrayList<Content> contents;
        Map<Integer, String> languagesMap = displayLanguages();
        while (true) {
            try {
                System.out.print("Enter Choice: ");
                choice = scanner.nextInt();
                contents = new ArrayList<>(Series.findByLanguage(languagesMap.get(choice)));
                if (contents.isEmpty()) {
                    System.out.println("No Results Found.");
                    return;
                }
                break;
            }
            catch (Exception e) {
                System.out.println("Invalid Choice.");
            }
        }
        printContent(contents);
    }

    private void searchMoviesByLanguage() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        ArrayList<Content> contents;
        Map<Integer, String> languagesMap = displayLanguages();
        while (true) {
            try {
                System.out.print("Enter Choice: ");
                choice = scanner.nextInt();
                contents = new ArrayList<>(Movie.findByLanguage(languagesMap.get(choice)));
                if (contents.isEmpty()) {
                    System.out.println("No Results Found.");
                    return;
                }
                break;
            }
            catch (Exception e) {
                System.out.println("Invalid Choice.");
            }
        }
        printContent(contents);
    }
}
