package WatChill;

import WatChill.Review.Review;
import WatChill.Series.Episode;
import WatChill.Series.Season;
import WatChill.Series.Series;
import WatChill.Subscription.BasicPlan;
import WatChill.Subscription.Subscription;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        for(Series series : Series.getTopTen())
            System.out.println(series.getTitle());
    }
}
