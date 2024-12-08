package WatChill.Cast;

import java.time.LocalDate;
import java.util.ArrayList;

import WatChill.Content.Series.Series;
import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import WatChill.Content.Movie.Movie;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Cast {
    private static ArrayList<Cast> casts;
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
    private String picture;

    @JsonCreator
    public Cast(
            @JsonProperty("id") String id,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("dateOfBirth") LocalDate dateOfBirth,
            @JsonProperty("gender") String gender,
            @JsonProperty("nationality") String nationality,
            @JsonProperty("instagramLink") String instagramLink,
            @JsonProperty("twitterLink") String twitterLink,
            @JsonProperty("movies") ArrayList<Movie> movies,
            @JsonProperty("serieses") ArrayList<Series> serieses,
            @JsonProperty("picture") String picture
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
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public void setSerieses(ArrayList<Series> serieses) {
        this.serieses = serieses;
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

    public String getFirstName() {
        return firstName;
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

    public void addSeries(Series series) {
        this.serieses.add(series);
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

    public static ArrayList<Cast> getCasts() {
        if (casts == null) {
            casts = JsonReader.readJsonFile("./src/main/data/Casts.json", Cast.class);
        }
        return casts;
    }

    public void saveCast() {
        int castIndex = findCastIndex();
        if (castIndex == -1) {
            getCasts().add(this);
        } else {
            getCasts().set(castIndex, this);
        }
    }

    public void printCastInfo() {
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Date of Birth: " + getDateOfBirth());
        System.out.println("Gender: " + getGender());
        System.out.println("Nationality: " + getNationality());
        System.out.println("Instagram: " + getInstagramLink());
        System.out.println("Twitter: " + getTwitterLink());
        System.out.println("Movies:");
        for (Movie movie : getMovies()) {
            System.out.println(movie.getTitle() + " (" + movie.getReleaseDate() + ") - " + movie.getGenres());
        }
    }

    // Search for actor by name
    public static ArrayList<Cast> searchCastsByName(String name) {
        ArrayList<Cast> filteredCasts = getCasts();
        // Filter actors whose names do not contain the search query
        filteredCasts.removeIf(cast -> !cast.getFirstName().concat(" " + cast.getLastName()).toLowerCase().contains(name.strip().toLowerCase()));
        return filteredCasts;
    }

    private int findCastIndex() {
        for (int i = 0; i < getCasts().size(); i++) {
            if (getCasts().get(i).getId().equals(getId())) {
                return i;
            }
        }
        return -1;
    }

    public static void storeCasts() {
        JsonWriter.writeJsonToFile("./src/main/data/Casts.json", getCasts());
    }

}


