package WatChill.Subscription;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    property = "@class" // A "@class" field in JSON will determine the subclass
)
// Specify sub-classes of Plan abstract class for Jackson to serialize
@JsonSubTypes({
    @JsonSubTypes.Type(value = BasicPlan.class, name = "WatChill.Subscription.BasicPlan"),
    @JsonSubTypes.Type(value = StandardPlan.class, name = "WatChill.Subscription.StandardPlan"),
    @JsonSubTypes.Type(value = PremiumPlan.class, name = "WatChill.Subscription.PremiumPlan")
})
abstract public class Plan {
    private int maxMoviesCount;
    private double price;

    abstract public double getPrice();
    abstract public int getMaxMoviesCount();
}
