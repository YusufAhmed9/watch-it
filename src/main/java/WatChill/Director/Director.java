package WatChill.Director;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import WatChill.Movie.Movie;

public class Director {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private List<Movie> movies;
    private String nationality;
    private String instagramLink;
    private String twitterLink;

    public Director(String firstName, String lastName, String dateOfBirth, String gender, String nationality, String instagramLink, String twitterLink) {
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

    public static void searchDirector(List<Director> directors) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a director's name to search:");
        String searchName = scanner.nextLine();

        for (Director director : directors) {
            if (director.getFirstName().toLowerCase().contains(searchName.toLowerCase()) ||
                    director.getLastName().toLowerCase().contains(searchName.toLowerCase())) {
                System.out.println("Name: " + director.getFirstName() + " " + director.getLastName());
                System.out.println("Date of Birth: " + director.getDateOfBirth());
                System.out.println("Gender: " + director.getGender());
                System.out.println("Nationality: " + director.getNationality());
                System.out.println("Instagram: " + director.getInstagramLink());
                System.out.println("Twitter: " + director.getTwitterLink());
                System.out.println("Movies:");
                for (Movie movie : director.getMovies()) {
                    System.out.println(movie.getTitle() + " (" + movie.getReleaseYear() + ") - " + movie.getGenre());
                }
            }
        }
    }
}