package WatChill.Subscription;

import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

// Specify the attributes for jackson and ignore getter methods
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Subscription {
    private static ArrayList<Subscription> subscriptions;
    private String id;
    private String userId;
    private LocalDate startDate;
    private int moviesLeftCount;
    Plan plan;

    // Specify the constructor and parameters for jackson to serialize rhe class
    @JsonCreator
    public Subscription(
            @JsonProperty("id") String id,
            @JsonProperty("userId") String userId,
            @JsonProperty("startDate") LocalDate startDate,
            @JsonProperty("plan") Plan plan,
            @JsonProperty("moviesLeftCount") int moviesLeftCount
    ) {
        this.id = id;
        this.userId = userId;
        this.startDate = startDate;
        this.moviesLeftCount = moviesLeftCount;
        this.plan = plan;
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

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public int getMoviesLeftCount() {
        return moviesLeftCount;
    }

    public void setMoviesLeftCount(int moviesLeftCount) {
        this.moviesLeftCount = moviesLeftCount;
    }

    // Get all subscriptions from JsonFile
    public static ArrayList<Subscription> getSubscriptions() {
        if (subscriptions == null) {
            subscriptions = JsonReader.readJsonFile("./src/main/data/subscriptions.json", Subscription.class);
        }
        return subscriptions;
    }

    // Check if subscription is active by comparing current (date + 30) and start date of subscription
    public boolean isSubscriptionActive() {
        LocalDate currentDate = LocalDate.now();
        return startDate.plusDays(30).compareTo(currentDate) > -1;
    }

    // Remove subscription from ArrayList
    public void cancelSubscription(String subscriptionId) {
        int index = findSubscriptionIndex();
        getSubscriptions().remove(index);
    }

    public void saveSubscription() {
        int index = findSubscriptionIndex(); // Get subscription index
        if (index == -1) {
            getSubscriptions().add(this); // Add subscription to ArrayList if not in ArrayList
        }
        else {
            getSubscriptions().set(index, this); // Update subscription if in ArrayList
        }
    }

    public static Map<String, Integer> getPlansSubscriptions() {
        // Counter variables for every plan type
        int basicPlanSubscriptionCount = 0;
        int standardPlanSubscriptionCount = 0;
        int premiumPlanSubscriptionCount = 0;
        Map<String, Integer> plansMap = new HashMap<>();
        for (Subscription subscription : getSubscriptions()) {
            // Check type of plan
            if (subscription.plan instanceof BasicPlan) {
                basicPlanSubscriptionCount++;
            }
            else if (subscription.plan instanceof StandardPlan) {
                standardPlanSubscriptionCount++;
            }
            else {
                premiumPlanSubscriptionCount++;
            }
        }
        // Add counters to plans map
        plansMap.put("basic", basicPlanSubscriptionCount);
        plansMap.put("standard", standardPlanSubscriptionCount);
        plansMap.put("premium", premiumPlanSubscriptionCount);
        return plansMap;
    }

    public static String getHighestMonthRevenue() {
        double[] monthsRevenues = new double[13]; // Array to store all 12 months revenues
        int maxRevenueIndex = 0;
        for (Subscription subscription : getSubscriptions()) {
            int monthIndex = subscription.getStartDate().getMonth().getValue(); // Get month index (not 0-based) from subscription start date
            double subscriptionPrice = subscription.plan.getPrice();
            monthsRevenues[monthIndex] += subscriptionPrice; // Increment month revenue by plan price
        }
        for (int i = 1; i < monthsRevenues.length; i++) {
            if (monthsRevenues[i] > monthsRevenues[maxRevenueIndex]) {
                maxRevenueIndex = i;
            }
        }
        // Return month name
        return Month.of(maxRevenueIndex).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    // Get current subscription index from ArrayList
    private int findSubscriptionIndex() {
        for (int i = 0; i < getSubscriptions().size(); i++) {
            if (getSubscriptions().get(i).getId().equals(getId())) {
                return i;
            }
        }
        return -1;
    }

    // Get active subscription by userId
    public static Subscription getUserSubscription(String userId) {
        for (Subscription subscription : getSubscriptions()) {
            if (subscription.getUserId().equals(userId) && subscription.isSubscriptionActive()) {
                return subscription;
            }
        }
        return null;
    }

    // Save subscriptions in Json file
    public static void storeSubscriptions() {
        JsonWriter.writeJsonToFile("./src/main/data/subscriptions.json", getSubscriptions());
    }

}
