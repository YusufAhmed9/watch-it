package WatChill.Subscription;

abstract public class Plan {
    private int maxMoviesCount;
    private double price;

    public int getMaxMoviesCount() {
        return maxMoviesCount;
    }

    public double getPrice() {
        return price;
    }
}
