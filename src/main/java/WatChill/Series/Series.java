package WatChill.Series;

import WatChill.Cast.Cast;
import WatChill.Director.Director;
import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.*;

// Specify the attributes for jackson and ignore getter methods
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)

public class Series {
    private String id;
    private String title;
    private LocalDate releaseDate;
    private ArrayList<Season> seasons;
    private String description;
    private ArrayList<String> languages;
    private String country;
    private ArrayList<String> genres;
    private static ArrayList<Series> fileSeries;
    private ArrayList<Director> directors;
    private ArrayList<Cast> casts;

    // Specify the constructor and parameters for jackson to serialize rhe class
    @JsonCreator
    public Series(
            @JsonProperty("id") String id,
            @JsonProperty("title") String title,
            @JsonProperty("releaseDate") LocalDate releaseDate,
            @JsonProperty("seasons") ArrayList<Season> seasons,
            @JsonProperty("description") String description,
            @JsonProperty("languages") ArrayList<String> languages,
            @JsonProperty("country") String country,
            @JsonProperty("genres") ArrayList<String> genres,
            @JsonProperty("director") ArrayList<Director> directors,
            @JsonProperty("casts") ArrayList<Cast> casts
    ) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.seasons = seasons;
        this.description = description;
        this.languages = languages;
        this.country = country;
        this.genres = genres;
        this.directors = directors;
        this.casts = casts;
    }

    public ArrayList<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<Director> directors) {
        this.directors = directors;
    }

    public void addDirector(Director director){
        directors.add(director);
    }

    public ArrayList<Cast> getCasts() {
        return casts;
    }

    public void setCasts(ArrayList<Cast> casts) {
        this.casts = casts;
    }

    public void addCast(Cast cast){
        casts.add(cast);
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void addGenre(String genre) {
        genres.add(genre);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public void addLanguage(String language) {
        languages.add(language);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public void addSeason(Season season) {
        seasons.add(season);
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getViewsCount() {
        //Initialize total views count to zero
        int totalViewsCount = 0;
        for (Season season : getSeasons()) {
            //Sums all seasons of the series view count
            totalViewsCount += season.getViewsCount();
        }
        return totalViewsCount;
    }

    public static ArrayList<Series> retrieveSeries() {
        if (fileSeries == null)//If series is not read yet
            return fileSeries = JsonReader.readJsonFile("./src/main/data/Series.json", Series.class);
        return fileSeries;
    }

    private int findSeriesIndex() {
        retrieveSeries();
        for (int seriesIndex = 0; seriesIndex < fileSeries.size(); seriesIndex++) {
            //Comparing every series id in the database to current id
            if (fileSeries.get(seriesIndex).getId().equals(getId()))//If true then it is already stored
                return seriesIndex;
        }
        //if -1 then it was not found in database
        return -1;
    }

    public void addSeries() {
        if (findSeriesIndex() == -1)//If it wasn't found in database
            fileSeries.add(this);//add to database
        else //Was found "There's a chance it's not updated"
            fileSeries.set(findSeriesIndex(), this);//Update it's value in database
    }

    public static void storeAllSeries() {//Function that write all series to a json file
        JsonWriter.writeJsonToFile("./src/main/data/Series.json", fileSeries);
    }

    public static ArrayList<Series> findByGenre(String genre) {
        //ArrayList to store series with wanted genre
        ArrayList<Series> desiredSeries = new ArrayList<>();
        for (Series series : retrieveSeries()) {
            if (!series.getGenres().contains(genre))//If the genre is not found inside this series then it won't make it to list
                continue;
            //Adds series with the desired genre
            desiredSeries.add(series);
        }
        return desiredSeries;
    }

    private double getSeriesAverageViews() {
        //Total number of views of series
        int totalViews = getViewsCount();
        //A variable to count number of episodes in the series
        double totalEpisodes = 0;
        for (Season season : getSeasons()) {
            //Adds number of episodes in each season
            totalEpisodes += season.getEpisodes().size();
        }
        return totalViews / totalEpisodes;
    }

    static class SeriesComparator implements Comparator<Series> {
        @Override
        public int compare(Series s1, Series s2) {//Comparator that sorts series by views in descending order
            return Double.compare(s2.getSeriesAverageViews(), s1.getSeriesAverageViews());
        }
    }

    public static ArrayList<Series> getTopTen() {
        ArrayList<Series> sortedSeriesByViews = retrieveSeries();
        //Using a comparator to sort ArrayList by views in descending order
        sortedSeriesByViews.sort(new SeriesComparator());
        //Takes top ten series after sorting
        List<Series> topTenSeries = sortedSeriesByViews.subList(0, Math.min(10, sortedSeriesByViews.size()));
        // Convert the list to an ArrayList of Series
        return new ArrayList<>(topTenSeries);

    }

    // Search for series by title
    public static ArrayList<Series> searchCast(String name) {
        ArrayList<Series> filteredSeries = retrieveSeries();
        // Filter series whose titles do not contain the search query
        filteredSeries.removeIf(series -> !series.getTitle().toLowerCase().contains(name.strip().toLowerCase()));
        return filteredSeries;
    }
}
