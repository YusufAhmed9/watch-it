package WatChill.Subscription;

import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

public class Subscription {
    private static ArrayList<Subscription> subscriptions = null;
    private String id;
    private String userId;
    private LocalDate startDate;
    private int moviesLeftCount;
    Plan plan;

    public Subscription (String id, String userId, LocalDate startDate, Plan plan, int moviesLeftCount) {
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

    public static ArrayList<Subscription> getSubscriptions() {
        if (subscriptions == null) {
            subscriptions = JsonReader.readJsonFile("./src/main/data/subscriptions.json");
        }
        return subscriptions;
    }

    public boolean isSubscriptionActive() {
        LocalDate currentDate = LocalDate.now();
        return startDate.plusDays(30).compareTo(currentDate) > -1;
    }

    public void cancelSubscription(String subscriptionId) {
        int index = findSubscriptionIndex();
        getSubscriptions().remove(index);
    }

    public void saveSubscription() {
        int index = findSubscriptionIndex();
        if (index == -1) {
            getSubscriptions().add(this);
        }
        else {
            getSubscriptions().set(index, this);
        }
    }

    public Map<String, Integer> getPlansSubscriptions() {
        int basicPlanSubscriptionCount = 0;
        int standardPlanSubscriptionCount = 0;
        int premiumPlanSubscriptionCount = 0;
        Map<String, Integer> plansMap = new HashMap<>();
        for (Subscription subscription : getSubscriptions()) {
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
        plansMap.put("basic", basicPlanSubscriptionCount);
        plansMap.put("standard", standardPlanSubscriptionCount);
        plansMap.put("premium", premiumPlanSubscriptionCount);
        return plansMap;
    }

    public static String getHighestMonthRevenue() {
        double[] monthsRevenues = new double[12];
        double maxRevenue = monthsRevenues[0];
        int maxRevenueIndex = 0;
        for (Subscription subscription : getSubscriptions()) {
            monthsRevenues[subscription.getStartDate().getMonth().getValue() - 1] += subscription.plan.getPrice();
        }
        for (int i = 0; i < monthsRevenues.length; i++) {
            if (monthsRevenues[i] > maxRevenue) {
                maxRevenue = monthsRevenues[i];
                maxRevenueIndex = i;
            }
        }
        return Month.of(maxRevenueIndex + 1).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    private int findSubscriptionIndex() {
        for (int i = 0; i < getSubscriptions().size(); i++) {
            if (getSubscriptions().get(i).getId().equals(getId())) {
                return i;
            }
        }
        return -1;
    }

    public static void storeSubscriptions() {
        JsonWriter.writeJsonToFile("./src/main/data/subscriptions.json", getSubscriptions());
    }

}
