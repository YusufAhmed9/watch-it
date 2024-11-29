package WatChill.Subscription;

abstract public class Plan {
    private int maxMoviesCount;
    private double price;

    abstract public double getPrice();
    abstract public int getMaxMoviesCount();
}
