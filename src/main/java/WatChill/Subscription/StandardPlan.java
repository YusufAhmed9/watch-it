package WatChill.Subscription;

public class StandardPlan extends Plan {
    private final int maxMoviesCount = 10;
    private final double price = 20;

    @Override
    public int getMaxMoviesCount() {
        return maxMoviesCount;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
