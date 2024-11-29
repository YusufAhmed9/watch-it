package WatChill;

import WatChill.Review.Review;
import WatChill.Series.Season;
import WatChill.Series.Series;

import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Series s = new Series("1", "AOT", new Date(), new ArrayList<Season>(), new ArrayList<Review>(), "Great", new ArrayList<String>(), "Japan", new ArrayList<String>());
        s.addCurrentSeriesToList();
        s.storeSeries();
    }
}
