package WatChill.Subscription;

import java.util.HashMap;
import java.util.Map;

public class Plan {
    private static final Map<String, Map<String, Object>> plans = Map.of(
            "basic", Map.of(
                "name", "basic",
                "price", 10,
                "maxMoviesCount", 5
            ),
            "standard", Map.of(
                "name", "standard",
                "price", 20,
                "maxMoviesCount", 10
            ),
            "premium", Map.of(
                "name", "premium",
                "price", 30,
                "maxMoviesCount", 30
            )
    );

    public static Map<String, Map<String, Object>> getPlans() {
        return plans;
    }
}
