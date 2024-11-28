package WatChill;

import WatChill.FileHandling.JsonWriter;
import WatChill.Subscription.Subscription;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
//        Subscription s = new Subscription("1", "1", LocalDate.now());
//        s.saveSubscription();
        JsonWriter.writeJsonToFile("./src/main/subscriptions.json", Subscription.getSubscriptions());
    }
}
