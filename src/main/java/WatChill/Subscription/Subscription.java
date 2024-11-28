package WatChill.Subscription;

import WatChill.FileHandling.JsonReader;

import java.time.LocalDate;
import java.util.*;

public class Subscription {
    private static ArrayList<Subscription> subscriptions = null;
    private String id;
    private String userId;
//    private Plans;
    private LocalDate startDate;

    public Subscription (String id, String userId, LocalDate startDate) {
        this.id = id;
        this.userId = userId;
        this.startDate = startDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public boolean isActive() {
        LocalDate currentDate = LocalDate.now();
        return startDate.plusDays(30).compareTo(currentDate) > -1;
    }

    public static ArrayList<Subscription> getSubscriptions() {
        if (subscriptions == null) {
            subscriptions = JsonReader.readJsonFile("./src/main/subscriptions.json");
        }
        return subscriptions;
    }

    public void saveSubscription() {
        getSubscriptions().add(this);
    }



}
