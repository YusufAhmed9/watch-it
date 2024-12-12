package WatChill.UserManagement;

import WatChill.Content.Content;
import WatChill.Content.WatchableContent;
import WatChill.Review.Review;
import WatChill.Subscription.*;
import WatChill.UserWatchRecord.UserWatchRecord;
import com.fasterxml.jackson.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

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
        System.out.printf("%40s Welcome Back, %s\n", getUsername());
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
        while (true) {
            System.out.println("Choice: ");
            choice = scanner.nextInt();
            if (choice >= 1 && choice <= 3) {
                break;
            }
            System.out.println("Invalid Choice.");
        }
        switch (choice) {
            case 1:
                subscribeInterface();
                break;
            case 2:
                break;
            case 3:
                displayWatchHistory();
                break;
        }
    }

    private void subscribedCustomerInterface() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.println("[ 1 ]: Movies");
        System.out.println("[ 2 ]: Series");
        System.out.println("[ 3 ]: Subscription Details");
        System.out.println("[ 4 ]: Edit Your Info");
        System.out.println("[ 5 ]: Watch History");
        System.out.println("[ 6 ]: Watch Later");
        while (true) {
            System.out.println("Choice: ");
            choice = scanner.nextInt();
            if (choice >= 1 && choice <= 6) {
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
        else if (choice == 4) {

        }
        else if (choice == 5) {
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
        while (true) {
            try {
                System.out.print("Choice: ");
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 4) {
                    break;
                }
            } catch (Exception e) {

            }
            System.out.println("Invalid Choice.");
        }
        if (choice == 1) {
            displayTrendingMovies();
        }
        else if (choice == 2) {
            displayTopRatedMovies();
        }
        else if (choice == 3) {

        }
        else {

        }
    }

    public void displaySeries() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.println("[ 1 ]: Trending Series");
        System.out.println("[ 2 ]: Top Rated Series");
        System.out.println("[ 3 ]: Search Series By Name");
        System.out.println("[ 4 ]: Search Series By Genre");
        while (true) {
            System.out.print("Choice: ");
            choice = scanner.nextInt();
            if (choice >= 1 && choice <= 4) {
                break;
            }
            System.out.println("Invalid Choice.");
        }
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
        Plan plan = null;
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
        System.out.println("Watch History: ");
    }

    private void watchEpisode(WatchableContent watchableContent, Review review) {
        Subscription subscription = Subscription.getUserSubscription(getId());
        subscription.setMoviesLeftCount(subscription.getMoviesLeftCount() - 1);
        subscription.saveSubscription();
        System.out.println("You have: " + subscription.getMoviesLeftCount() + " movies left");
//        UserWatchRecord.addRecord(watchableContent, getId(), review);
    }

    private void displayWatchLater() {
        if (watchLater.isEmpty()) {
            System.out.println("You have no movies in your watch later list.");
            return;
        }
        printContent(watchLater);
    }

    private void addToWatchLater(Content content) {
        int watchLaterIndex = findMovieWatchLaterIndex(content.getId());
        if (watchLaterIndex == -1) {
            watchLater.add(content);
        }
    }

    private int findMovieWatchLaterIndex(String contentId) {
        for (int i = 0; i < watchLater.size(); i++) {
            if (watchLater.get(i).getId().equals(contentId)) {
                return i;
            }
        }
        return -1;
    }

    private void displayTrendingMovies() {
//        printMovies();
    }

    private void displayTopRatedMovies() {
//        printMovies();
    }

    private void searchMoviesByName() {

    }

    private void searchMoviesByGenre() {

    }

    private void displayTrendingSeries() {
//        printMovies();
    }

    private void displayTopRatedSeries() {
//        printMovies();
    }

    private void searchSeriesByName() {

    }

    private void searchSeriesByGenre() {

    }

    private void printContent(ArrayList<Content> contents) {
        System.out.printf("%20s", "Title |");
        System.out.printf("%20s", "Release Year |");
        System.out.printf("%20s", "Description|\n");
        for (Content content : contents) {
            System.out.printf("%20s", content.getTitle() + " |");
            System.out.printf("%20s", content.getReleaseDate() + " |");
            System.out.printf("%20s", content.getDescription() + " |\n");
        }
    }
}
