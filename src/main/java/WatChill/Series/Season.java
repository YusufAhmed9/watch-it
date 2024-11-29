package WatChill.Series;

import WatChill.Review.Review;

import java.util.ArrayList;
import java.util.Date;

public class Season {
    private String title;
    private Date releaseDate;
    private String description;
    private ArrayList<Episode> episodes;

    public Season(String title, String description, Date releaseDate, ArrayList<Episode> episodes) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.episodes = episodes;
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

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    public void addEpisode(Episode episode){
        episodes.add(episode);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViewsCount(){
        //Initialize total views count to zero
        int totalViewsCount = 0;
        for(Episode episode : getEpisodes()){
            //Sums all episodes of the season view count
            totalViewsCount += episode.getViewsCount();
        }
        return totalViewsCount;
    }
}