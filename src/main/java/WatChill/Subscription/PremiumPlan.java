package WatChill.Subscription;

public class PremiumPlan extends Plan {
    private final int maxMoviesCount = 30;
    private final double price = 30;

    @Override
    public int getMaxMoviesCount() {
        return maxMoviesCount;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
