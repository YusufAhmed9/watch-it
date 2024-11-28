package WatChill.Subscription;

import WatChill.FileHandling.JsonReader;

import java.time.LocalDate;
import java.util.*;

public class Subscription {
    private static ArrayList<Subscription> subscriptions = null;
    private String id;
    private String userId;
    private LocalDate startDate;
    private int moviesLeftCount;
    private Map<String, Object> plan;

    public Subscription (String id, String userId, LocalDate startDate, Map<String, Object> plan, int moviesLeftCount) {
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

    public Map<String, Object> getPlan() {
        return plan;
    }

    public void setPlan(Map<String, Object> plan) {
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
            subscriptions = JsonReader.readJsonFile("./src/main/subscriptions.json");
        }
        return subscriptions;
    }

    public boolean isActive() {
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
        ArrayList<Subscription> subscriptions = getSubscriptions();
        for (Subscription subscription : subscriptions) {
            String name = (String) subscription.plan.get("name");
            if (name.equals("basic")) {
                basicPlanSubscriptionCount++;
            }
            else if (name.equals("standard")) {
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

    private int findSubscriptionIndex() {
        ArrayList<Subscription> subscriptions = getSubscriptions();
        for (int i = 0; i < subscriptions.size(); i++) {
            if (subscriptions.get(i).getId().equals(getId())) {
                return i;
            }
        }
        return -1;
    }

}
