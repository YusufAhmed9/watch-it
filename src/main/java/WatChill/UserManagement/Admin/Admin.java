package WatChill.Admin;

import java.util.concurrent.Flow.Subscription;

import WatChill.Director.Director;
import WatChill.Movie.Movie;
import WatChill.User.User;
import WatChill.Series.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import WatChill.Subscription.*;



public class Admin extends User 
{
    //Constructor for Admin class
    public Admin(String name, String email, String password, String firstName, String LastName, String subscription)
    {
        super(name, email, password, firstName, LastName, subscription);   
    }

    //Displays Recent Movie Releases
    public void DisplayRecentMovieReleases()
    {
        ArrayList<Movie> shows = Movie.getMostRecent();

        for (Movie movie : shows)
        {
            System.out.println("Movie called: " + movie.getTitle() + " (" + movie.getReleaseDate() + ") - " + movie.getGenres());
        }
    }

    //Displays Recent Series Releases
    public void DisplayRecentSeriesReleases()
    {
        ArrayList<Series> shows = Series.getMostRecent();
        for (Series series : shows)
        {
            System.out.println("Series called: " + series.getTitle() + " (" + series.getReleaseDate() + ") - " + series.getGenres());
        }
    }

    //Display Movies By Genres
    public void GetMoviesByGenres(String Genre)
    {
        Movie.findByGenre(Genre);
    }

    public void GetSeriesByGenres(String Genre)
    {
        Series.findByGenre(Genre);
    }

    // use getHighestMonthRevenue from Subscribtion
    public void UseGetHighestMonthRevenue() 
    {
        String month = WatChill.Subscription.Subscription.getHighestMonthRevenue();
        System.out.println("Highest revenue month: "+month);
    }
    // use getPlansSubscribtions from Subscribtion
    public static void UseGetPlansSubscribtions() {
        Map<String, Integer> plans = WatChill.Subscription.Subscription.getPlansSubscriptions();
        
        for (Map.Entry<String, Integer> entry : plans.entrySet()) 
        {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
