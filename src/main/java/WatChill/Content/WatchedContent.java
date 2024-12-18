package WatChill.Content;

import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Episode;
import WatChill.Content.Series.Series;
import WatChill.UserWatchRecord.UserWatchRecord;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS, // class name as type information
        property = "@class" // "@class" as the property name
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Episode.class, name = "WatChill.Content.Series.Episode"),
        @JsonSubTypes.Type(value = Movie.class, name = "WatChill.Content.Movie.Movie")
})
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
