package WatChill.Content;

import WatChill.UserWatchRecord.UserWatchRecord;

public interface WatchedContent {
    String getId();
    void setId(String id);
    default double getRating(){
        double rating = 0.0;
        int numberOfRaters = 0;
        for (UserWatchRecord userWatchRecord : UserWatchRecord.retrieveRecords()) {
            if (userWatchRecord.getWatchedContent().getId().equals(getId())) {
                rating +=userWatchRecord.getReview().getRating();
                numberOfRaters++;
            }
        }
        return numberOfRaters != 0 ? rating/numberOfRaters : 0.0;
    }
}
