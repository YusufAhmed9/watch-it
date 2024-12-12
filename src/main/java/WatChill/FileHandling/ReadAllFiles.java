package WatChill.FileHandling;

import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Series;
import WatChill.Crew.Crew;
import WatChill.Subscription.Subscription;
import WatChill.UserWatchRecord.UserWatchRecord;

public class ReadAllFiles {
    public static void readAllFiles(){
        Movie.retrieveMovies();
        Series.retrieveSeries();
        Crew.retrieveCrews();
        Subscription.retrieveSubscriptions();
        UserWatchRecord.retrieveRecords();
    }
}