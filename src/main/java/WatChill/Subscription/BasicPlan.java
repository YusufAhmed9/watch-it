package WatChill.Subscription;

public class BasicPlan extends Plan {
    private final int maxMoviesCount = 5;
    private final double price = 10;

    @Override
    public int getMaxMoviesCount() {
        return maxMoviesCount;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
