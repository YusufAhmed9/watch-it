package WatChill.Crew;

import WatChill.Content.Movie.Movie;
import WatChill.Content.Series.Series;
import WatChill.Crew.Cast.Cast;
import WatChill.Crew.Director.Director;
import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import WatChill.Subscription.BasicPlan;
import WatChill.Subscription.PremiumPlan;
import WatChill.Subscription.StandardPlan;
import com.fasterxml.jackson.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type" // A "type" field in JSON will determine the subclass
)
// Specify subclasses of Plan abstract class for Jackson to serialize
@JsonSubTypes({
        @JsonSubTypes.Type(value = Director.class, name = "Director"),
        @JsonSubTypes.Type(value = Cast.class, name = "Cast"),
})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class Crew {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private ArrayList<String> contentCreated;
    private String nationality;
    private String instagramLink;
    private String twitterLink;
    private String picture;
    private static ArrayList<Crew> crews;

    public Crew(String firstName, String lastName, LocalDate dateOfBirth, String gender, String nationality, String instagramLink, String twitterLink, String picture) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.instagramLink = instagramLink;
        this.twitterLink = twitterLink;
        this.picture = picture;
        this.contentCreated = new ArrayList<>();
    }

    public Crew(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String nationality, String instagramLink, String twitterLink, ArrayList<String> contentCreated, String picture) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contentCreated = contentCreated;
        this.nationality = nationality;
        this.instagramLink = instagramLink;
        this.twitterLink = twitterLink;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getContentCreated() {
        return contentCreated;
    }

    public void setContentCreated(ArrayList<String> contentCreated) {
        this.contentCreated = contentCreated;
    }

    public void addContent(String contentId) {
        contentCreated.add(contentId);
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void printCrewInfo() {
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Date of Birth: " + getDateOfBirth());
        System.out.println("Gender: " + getGender());
        System.out.println("Nationality: " + getNationality());
        System.out.println("Instagram: " + getInstagramLink());
        System.out.println("Twitter: " + getTwitterLink());
        System.out.println("Movies:");
        for (String contentId : getContentCreated()) {
            if (Movie.findById(contentId) != null) {
                Movie movie = Movie.findById(contentId);
                System.out.println(movie.getTitle() + " (" + movie.getReleaseDate() + ") - " + movie.getGenres());
            } else {
                Series series = Series.findById(contentId);
                System.out.println(series.getTitle() + " (" + series.getReleaseDate() + ") - " + series.getGenres());
            }
        }
    }

    public static ArrayList<Crew> getCrews() {
        if (crews == null) {
            crews = JsonReader.readJsonFile("./src/main/data/Crews.json", Crew.class);
        }
        return crews;
    }

    public static ArrayList<Crew> searchByName(String name) {
        ArrayList<Crew> filteredCrews = new ArrayList<>(getCrews());
        // Filter actors whose names do not contain the search query
        filteredCrews.removeIf(crew -> !crew.getFirstName().concat(" " + crew.getLastName()).toLowerCase().contains(name.strip().toLowerCase()));
        return filteredCrews;
    }

    public void saveCrew() {
        int directorIndex = findCrewIndex();
        if (directorIndex == -1) {
            getCrews().add(this);
        } else {
            getCrews().set(directorIndex, this);
        }
    }

    private int findCrewIndex() {
        for (int i = 0; i < getCrews().size(); i++) {
            if (getCrews().get(i).getId().equals(getId())) {
                return i;
            }
        }
        return -1;
    }

    public static void storeCrews() {
        JsonWriter.writeJsonToFile("./src/main/data/Crews.json", getCrews());
    }
}
