package WatChill.Admin;

import java.util.concurrent.Flow.Subscription;

import WatChill.Movie.Movie;
import WatChill.User.User;
import WatChill.Series.*;
import java.util.*;
import WatChill.Subscription.*;



public class Admin extends User 
{
    public Admin(String name, String email, String password, String firstName, String LastName, String subscription)
    {
        super(name, email, password, firstName, LastName, subscription);   
    }

    public void DisplayRecentMovieReleases()
    {
        Movie.RecentMovieReleases();
    }

    public void DisplayRecentSeriesReleases()
    {
        Series.RecentSeriesReleases();
    }

    public void GetMoviesByGenres(String Genre)
    {
        Movie.findByGenre(Genre);
    }

    public void GetSeriesByGenres(String Genre)
    {
        Series.findByGenre(Genre);
    }

    // use getHighestMonthRevenue from Subscribtion
    public void UseGetHighestMonthRevenue() {
        String month = WatChill.Subscription.Subscription.getHighestMonthRevenue();
        System.out.println("Highest revenue month: "+month);
    }
    // use getPlansSubscribtions from Subscribtion
    public static void UseGetPlansSubscribtions() {
        Map<String, Integer> plans = WatChill.Subscription.Subscription.getPlansSubscriptions();
        System.out.println("Plans Subscribtions: "+plans);
    }
}
