package WatChill.Content.Series;

import WatChill.Content.Content;
import WatChill.Content.Movie.Movie;
import WatChill.Content.WatchedContent;
import WatChill.Crew.Crew;
import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.*;

// Specify the attributes for jackson and ignore getter methods
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)

public class Series extends Content {

    private ArrayList<Season> seasons;
    private static ArrayList<Series> seriesFile;

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
            @JsonProperty("director") ArrayList<Crew> crews,
            @JsonProperty("poster") String poster,
            @JsonProperty("budget") double budget,
            @JsonProperty("revenue") double revenue
    ) {
        super(id, title, releaseDate, description, languages, country, genres, crews, poster, budget, revenue);
        this.seasons = seasons;
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
    public void updateSeason(String seasonId){
        for (int i = 0; i < getSeasons().size(); i++) {
            Season season = getSeasons().get(i);
            if (season.getId().equals(seasonId)) {
                getSeasons().set(i, Season.findById(seasonId));  // Replace the old season with the updated one
                break;  // Stop once the season is found and updated
            }
        }
    }

    @Override
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
        if (seriesFile == null) {//If series is not read yet
            return seriesFile = JsonReader.readJsonFile("./src/main/data/Series.json", Series.class);
        }
        return seriesFile;
    }

    @Override
    protected int findIndex() {
        retrieveSeries();
        for (int seriesIndex = 0; seriesIndex < seriesFile.size(); seriesIndex++) {
            //Comparing every series id in the database to current id
            if (seriesFile.get(seriesIndex).getId().equals(getId())) {//If true then it is already stored
                return seriesIndex;
            }
        }
        //if -1 then it was not found in database
        return -1;
    }

    @Override
    public void save() {
        int index = findIndex();
        if (index == -1) {//If it wasn't found in database
            seriesFile.add(this);
        }//add to database
        else { //Was found "There's a chance it's not updated"
            update();//Update it's value in database
        }
    }

    @Override
    public void update() {
        seriesFile.set(findIndex(), this);//Update it's value in database
    }

    @Override
    public void delete() {
        seriesFile.remove(this);
    }

    public static void storeAllSeries() {//Function that write all series to a json file
        JsonWriter.writeJsonToFile("./src/main/data/Series.json", seriesFile);
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


    public static ArrayList<Series> getTopWatchedSeries() {
        ArrayList<Series> sortedSeriesByViews = retrieveSeries();
        //Using a comparator to sort ArrayList by views in descending order
        sortedSeriesByViews.sort(Comparator.comparing(Series::getSeriesAverageViews));
        //Takes top ten series after sorting
        List<Series> topTenSeries = sortedSeriesByViews.subList(0, Math.min(10, sortedSeriesByViews.size()));
        // Convert the list to an ArrayList of Series
        return new ArrayList<>(topTenSeries);

    }

    // Search for series by title
    public static ArrayList<Series> searchByTitle(String title) {
        ArrayList<Series> filteredSeries = new ArrayList<>(retrieveSeries());
        // Filter series whose titles do not contain the search query
        filteredSeries.removeIf(series -> !series.getTitle().toLowerCase().contains(title.strip().toLowerCase()));
        return filteredSeries;
    }

    public static Series findById(String id) {
        for (Series series : retrieveSeries()) {
            if (series.getId().equals(id)) {//A series with the given id is found
                return series;
            }
        }
        //No series with given id is found
        return null;
    }

    public double getRating() {
        double totalSeasonRating = 0.0;

        for (Season season : getSeasons()) {
            totalSeasonRating += season.getRating();
        }

        return !getSeasons().isEmpty() ? totalSeasonRating / getSeasons().size() : 0.0;
    }

    public static ArrayList<Series> getTopRatedSeries() {
        ArrayList<Series> topRatedMovies = new ArrayList<>(retrieveSeries());
        topRatedMovies.sort(Comparator.comparing(Series::getRating));
        return topRatedMovies;
    }

    public static ArrayList<Series> findByLanguage(String language) {
        ArrayList<Series> filteredSeries = new ArrayList<>();
        for (Series series : retrieveSeries()) {
            if (series.getLanguages().contains(language)) {
                filteredSeries.add(series);
            }
        }
        return filteredSeries;
    }
}
