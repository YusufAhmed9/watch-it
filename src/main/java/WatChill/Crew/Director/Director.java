package WatChill.Crew.Director;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import WatChill.Crew.Crew;
import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import WatChill.Content.Movie.Movie;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Director extends Crew {

    @JsonCreator
    public Director(
            @JsonProperty("id") String id,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("dateOfBirth") LocalDate dateOfBirth,
            @JsonProperty("gender") String gender,
            @JsonProperty("nationality") String nationality,
            @JsonProperty("instagramLink") String instagramLink,
            @JsonProperty("twitterLink") String twitterLink,
            @JsonProperty("movies") ArrayList<String> contentCreated,
            @JsonProperty("picture") String picture
    ) {
        super(id, firstName, lastName, dateOfBirth, gender, nationality, instagramLink, twitterLink, contentCreated, picture);

    }

    public Director(String firstName, String lastName, LocalDate dateOfBirth, String gender, String nationality, String instagramLink, String twitterLink, String picture) {
        super(firstName, lastName, dateOfBirth, gender, nationality, instagramLink, twitterLink, picture);
    }


}