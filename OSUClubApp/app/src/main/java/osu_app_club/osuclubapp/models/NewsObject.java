package osu_app_club.osuclubapp.models;

/**
 * Created by Bfriedman on 3/2/15.
 */
//todo CREATE THIS
public class NewsObject {
    private String title;
    private int imageRes;

    public NewsObject(String title, int imageRes) {
        this.title = title;
        this.imageRes = imageRes;
    }

    public String getTitle() {
        return title;
    }

    public int getImageRes() {
        return imageRes;
    }
}
