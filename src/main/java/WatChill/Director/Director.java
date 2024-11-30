package WatChill.Director;

import java.time.LocalDate;
import java.util.ArrayList;

import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import WatChill.Movie.Movie;
import WatChill.Series.Series;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Director {
    private static ArrayList<Director> directors;
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private ArrayList<Movie> movies;
    private ArrayList<Series> serieses;
    private String nationality;
    private String instagramLink;
    private String twitterLink;

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
            @JsonProperty("movies") ArrayList<Movie> movies,
            @JsonProperty("serieses") ArrayList<Series> serieses
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.instagramLink = instagramLink;
        this.twitterLink = twitterLink;
        this.movies = movies;
        this.serieses = serieses;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<Series> getSerieses() {
        return serieses;
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    public String getNationality() {
        return nationality;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public String getFirstName() {
        return firstName;
    }

    public void saveDirector() {
        int directorIndex = findDirectorIndex();
        if (directorIndex == -1) {
            getDirectors().add(this);
        }
        else {
            getDirectors().set(directorIndex, this);
        }
    }

    public void printDirectorInfo() {
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Date of Birth: " + getDateOfBirth());
        System.out.println("Gender: " + getGender());
        System.out.println("Nationality: " + getNationality());
        System.out.println("Instagram: " + getInstagramLink());
        System.out.println("Twitter: " + getTwitterLink());
        System.out.println("Movies:");
        for (Movie movie : getMovies()) {
            System.out.println(movie.getTitle() + " (" + movie.getReleaseYear() + ") - " + movie.getGenre());
        }
    }

    public static ArrayList<Director> getDirectors() {
        if (directors == null) {
            directors = JsonReader.readJsonFile("./src/main/data/Directors.json", Director.class);
        }
        return directors;
    }

    // Search for directors by name
    public ArrayList<Director> searchDirector(String name) {
        ArrayList<Director> filteredDirectors = getDirectors();
        // Filter directors whose names do not contain the search query
        filteredDirectors.removeIf(director -> !director.getFirstName().concat(director.getLastName()).toLowerCase().contains(name.toLowerCase()));
        return filteredDirectors;
    }

    private int findDirectorIndex() {
        for (int i = 0; i < getDirectors().size(); i++) {
            if (getDirectors().get(i).getId().equals(getId())) {
                return i;
            }
        }
        return -1;
    }

    public static void storeDirectors() {
        JsonWriter.writeJsonToFile("./src/main/data/Directors.json", getDirectors());
    }

}