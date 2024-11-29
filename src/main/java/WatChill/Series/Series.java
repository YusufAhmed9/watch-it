package WatChill.Series;

import WatChill.FileHandling.JsonReader;
import WatChill.FileHandling.JsonWriter;
import WatChill.Review.Review;

import java.util.ArrayList;
import java.util.Date;

public class Series {
    private String id;
    private String title;
    private Date releaseDate;
    private ArrayList<Season> seasons;
    private String description;
    private ArrayList<String> languages;
    private String country;
    private ArrayList<String> genres;
    private static ArrayList<Series> fileSeries;

    public Series(String id, String title, Date releaseDate, ArrayList<Season> seasons, String description, ArrayList<String> languages, String country, ArrayList<String> genres) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.seasons = seasons;
        this.description = description;
        this.languages = languages;
        this.country = country;
        this.genres = genres;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void addGenre(String genre){
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

    public void addLanguage(String language){
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

    public void addSeason(Season season){
        seasons.add(season);
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getViewsCount(){
        //Initialize total views count to zero
        int totalViewsCount = 0;
        for(Season season : getSeasons()){
            //Sums all seasons of the series view count
            totalViewsCount += season.getViewsCount();
        }
        return totalViewsCount;
    }

    public static ArrayList<Series> retrieveSeries(){
        if(fileSeries == null)//If series is not read yet
            return fileSeries = JsonReader.readJsonFile("./src/main/data/Series.json", Series.class);
        return fileSeries;
    }

    private int findSeriesIndex(){
        retrieveSeries();
        for(int i = 0; i < fileSeries.size(); i++){
            //Comparing every series id in the database to current id
            if(fileSeries.get(i).getId().equals(getId()))//If true then it is already stored
                return i;
        }
        //if -1 then it was not found in database
        return -1;
    }

    public void addCurrentSeriesToList(){
        if(findSeriesIndex() == -1)//If it wasn't found in database
            fileSeries.add(this);//add to database
        else //Was found "There's a chance it's not updated"
            fileSeries.set(findSeriesIndex(), this);//Update it's value in database
    }

    public static void storeSeries() {//Function that write all series to a json file
        JsonWriter.writeJsonToFile("./src/main/data/Series.json", fileSeries);
    }

    public static ArrayList<Series> findSeriesByGenre(String genre){
        retrieveSeries();
        //ArrayList to store series with wanted genre
        ArrayList<Series>desiredSeries = new ArrayList<>();
        for(Series series : retrieveSeries()){
            if(series.getGenres().indexOf(genre) == -1)//If the genre is not found inside this series then it won't make it to list
                continue;
            //Adds series with the desired genre
            desiredSeries.add(series);
        }
        return desiredSeries;
    }
}