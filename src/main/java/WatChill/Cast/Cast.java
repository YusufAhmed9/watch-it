package WatChill.Cast;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import WatChill.Movie.Movie;

public class Cast {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private List<Movie> movies;
    private String nationality;
    private String instagramLink;
    private String twitterLink;

    public Cast(String firstName, String lastName, String dateOfBirth, String gender, String nationality, String instagramLink, String twitterLink) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.instagramLink = instagramLink;
        this.twitterLink = twitterLink;
        this.movies = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMovies(List<Movie> movies) {
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

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public List<Movie> getMovies() {
        return movies;
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

    public void printCastInfo() {

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
    public static void searchCast(List<Cast> casts, String name) {
        // Search for an actor by name
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an actor's name to search:");
        String searchName = scanner.nextLine();

        for (Cast cast : casts) {
            if (cast.getFirstName().toLowerCase().contains(searchName.toLowerCase()) ||
                    cast.getLastName().toLowerCase().contains(searchName.toLowerCase())) {
                System.out.println("Name: " + cast.getFirstName() + " " + cast.getLastName());
                System.out.println("Date of Birth: " + cast.getDateOfBirth());
                System.out.println("Gender: " + cast.getGender());
                System.out.println("Nationality: " + cast.getNationality());
                System.out.println("Instagram: " + cast.getInstagramLink());
                System.out.println("Twitter: " + cast.getTwitterLink());
                System.out.println("Movies:");
                for (Movie movie : cast.getMovies()) {
                    System.out.println(movie.getTitle() + " (" + movie.getReleaseYear() + ") - " + movie.getGenre());
                }
            }
        }
    }
}


