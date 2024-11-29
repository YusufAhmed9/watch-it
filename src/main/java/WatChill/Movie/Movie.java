package WatChill.Movie;

public class Movie {
    private String title;
    private String releaseYear;
    private String genre;

    public Movie(String title, String releaseYear, String genre) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getGenre() {
        return genre;
    }

}
