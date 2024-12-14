package WatChill.FileHandling;

import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Series;
import WatChill.Crew.Crew;
import WatChill.Subscription.Subscription;
import WatChill.UserManagement.User;
import WatChill.UserWatchRecord.UserWatchRecord;

public class WriteAllFiles {
    public static void writeAllFiles(){
        Movie.storeAllMovies();
        Series.storeAllSeries();
        Crew.storeCrews();
        Subscription.storeSubscriptions();
        UserWatchRecord.storeRecords();
        User.storeUsers();
    }
}
